package com.example.lesson_7_strelyukhin.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BridgesApi {
    private const val BASE_URL = "http://gdemost.handh.ru:1235/"
    val apiService: BridgesApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(BridgesApiService::class.java)
}