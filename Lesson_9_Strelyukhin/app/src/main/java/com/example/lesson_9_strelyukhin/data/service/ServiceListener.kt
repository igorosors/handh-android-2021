package com.example.lesson_9_strelyukhin.data.service

import com.example.lesson_9_strelyukhin.data.model.Weather

interface ServiceListener {
    fun setWeather(weather: Weather)
}