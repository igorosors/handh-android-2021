package com.example.lesson_9_strelyukhin.data.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.example.lesson_9_strelyukhin.data.remote.WeatherApi
import kotlinx.coroutines.*

class WeatherService : Service() {

    companion object {
        fun createStartIntent(context: Context): Intent {
            return Intent(context, WeatherService::class.java)
        }
    }

    private var serviceListener: ServiceListener? = null
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private fun loadWeather() {
        coroutineScope.launch {
            while (true) {
                val weather = WeatherApi.apiService.getWeather()
                serviceListener?.setWeather(weather)
                delay(60000)
            }
        }


    }

    override fun onCreate() {
        super.onCreate()

        loadWeather()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return WeatherBinder()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
    }

    fun setListener(serviceListener: ServiceListener?) {
        this.serviceListener = serviceListener
    }

    internal inner class WeatherBinder : Binder() {
        val service: WeatherService
            get() = this@WeatherService
    }

}