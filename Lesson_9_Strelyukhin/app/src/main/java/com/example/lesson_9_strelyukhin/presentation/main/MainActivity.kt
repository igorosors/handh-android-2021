package com.example.lesson_9_strelyukhin.presentation.main

import android.content.*
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.lesson_9_strelyukhin.R
import com.example.lesson_9_strelyukhin.data.model.Weather
import com.example.lesson_9_strelyukhin.data.service.DownloadService
import com.example.lesson_9_strelyukhin.data.service.ServiceListener
import com.example.lesson_9_strelyukhin.data.service.WeatherService
import com.example.lesson_9_strelyukhin.data.service.WeatherService.WeatherBinder
import com.example.lesson_9_strelyukhin.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DOWNLOAD = "extra_download"
        const val EXTRA_IMAGE = "extra_image"
        const val URL = "https://vk.com/doc246219706_622196009"
    }

    private val binding by viewBinding(ActivityMainBinding::bind)
    private var bound = false

    private val weatherConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            bound = true
            weatherBinder = binder as WeatherBinder
            weatherService = weatherBinder?.service
            weatherService?.setListener(object : ServiceListener {
                override fun setWeather(weather: Weather) {
                    runOnUiThread {
                        binding.textView.text = buildString {
                            clear()
                            val weatherNow = weather.weather?.get(0)?.main
                            val main = weather.main
                            append("${weatherNow}, ")
                            append("${main?.temp?.toInt()}°С\n")
                            append("Ощущается: ${main?.feelsLike?.toInt()}°С\n")
                            append("Минимальная температура: ${main?.tempMin?.toInt()}°С\n")
                            append("Максимальная температура: ${main?.tempMax?.toInt()}°С\n")
                            append("Давление: ${main?.pressure}00 Па\n")
                            append("Влажность: ${main?.humidity} %")
                        }
                    }
                }
            })
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            bound = false
            weatherBinder = null
        }
    }
    private val weatherIntent by lazy { WeatherService.createStartIntent(this) }
    private var weatherBinder: WeatherBinder? = null
    private var weatherService: WeatherService? = null

    private val downloadIntent by lazy { DownloadService.createStartIntent(this) }

    private val downloadReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val progress = intent?.getIntExtra(DownloadService.EXTRA_PROGRESS, 0)
            binding.textViewProgress.text = ("${progress}%")
            binding.buttonDownload.visibility = View.GONE
        }
    }

    private val imageReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val path = intent?.getStringExtra(DownloadService.EXTRA_IMAGE)
            path?.let {
                binding.imageView.setImageBitmap(BitmapFactory.decodeFile(File(it).absolutePath))
            }
            binding.textViewProgress.visibility = View.GONE
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindService(weatherIntent, weatherConnection, BIND_AUTO_CREATE)
        binding.buttonDownload.setOnClickListener {
            registerReceiver(downloadReceiver, IntentFilter(EXTRA_DOWNLOAD))
            registerReceiver(imageReceiver, IntentFilter(EXTRA_IMAGE))
            downloadIntent.apply {
                putExtra(DownloadService.EXTRA_URL, URL)
            }
            startService(downloadIntent)
        }

    }


    override fun onDestroy() {
        if (bound) {
            weatherService?.setListener(null)
            unbindService(weatherConnection)
            bound = false
        }
        unregisterReceiver(downloadReceiver)
        unregisterReceiver(imageReceiver)
        super.onDestroy()
    }

}