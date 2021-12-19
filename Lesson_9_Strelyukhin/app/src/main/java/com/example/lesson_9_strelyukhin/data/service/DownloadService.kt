package com.example.lesson_9_strelyukhin.data.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.example.lesson_9_strelyukhin.presentation.main.MainActivity
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.net.URL
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

class DownloadService : Service() {
    companion object {
        const val EXTRA_URL = "extra_url"
        const val EXTRA_PROGRESS = "extra_progress"
        const val EXTRA_IMAGE = "extra_image"

        fun createStartIntent(context: Context): Intent {
            return Intent(context, DownloadService::class.java)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Thread {
            val archive = download(intent)
            val image = unzip(archive)
            val intent2 = Intent(MainActivity.EXTRA_IMAGE)
            intent2.putExtra(EXTRA_IMAGE, image)
            sendBroadcast(intent2)
        }.start()
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun download(intent: Intent?): String {
        val urlToDownload = intent?.getStringExtra(EXTRA_URL)
        val intentDownload = Intent(MainActivity.EXTRA_DOWNLOAD)
        var path = ""
        try {
            val url = URL(urlToDownload)
            val connection = url.openConnection()
            connection.connect()
            val fileLength = connection.contentLength
            val bufferedInputStream = BufferedInputStream(connection.getInputStream())
            val file = File(this.applicationContext.filesDir, "image")
            path = file.absolutePath.toString()
            if (!file.exists()) {
                file.createNewFile()
            }
            val fileOutputStream = FileOutputStream(file)
            val data = byteArrayOf(1024.toByte())
            var total = 0
            var count: Int
            while (bufferedInputStream.read(data).also { count = it } != -1) {
                total += count
                intentDownload.putExtra(EXTRA_PROGRESS, (total * 100 / fileLength))
                sendBroadcast(intentDownload)
                fileOutputStream.write(data, 0, count)
            }
            fileOutputStream.flush()
            fileOutputStream.close()
            bufferedInputStream.close()
            intentDownload.putExtra(EXTRA_PROGRESS, 100)
            sendBroadcast(intentDownload)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return path
    }

    private fun unzip(path: String): String {
        var pathImage = ""
        val zipInputStream = ZipInputStream(FileInputStream(path))
        var zipEntry: ZipEntry
        val data = byteArrayOf(1024.toByte())
        try {
            while (zipInputStream.nextEntry.also { zipEntry = it } != null) {
                pathImage = path + zipEntry.name
                val fileOutputStream = FileOutputStream(pathImage)
                var count: Int
                while (zipInputStream.read(data).also { count = it } != -1) {
                    fileOutputStream.write(data, 0, count)
                }
                fileOutputStream.flush()
                zipInputStream.closeEntry()
                fileOutputStream.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return pathImage
    }

}