package com.example.lesson_9_strelyukhin.data.remote

import com.example.lesson_9_strelyukhin.data.model.Weather
import retrofit2.http.GET

interface WeatherApiService {
    @GET("data/2.5/weather?q=saratov&units=metric&appid=bee838ae92e37f5c80f85893bcd2ac78")
    suspend fun getWeather(): Weather
}