package com.example.lesson_9_strelyukhin.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherApi {
    private const val BASE_URL = "http://api.openweathermap.org/"
    val apiService: WeatherApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(WeatherApiService::class.java)
}