package com.example.lesson_9_strelyukhin.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Weather(
    @SerializedName("weather") val weather: List<WeatherWeather>?,
    @SerializedName("main") val main: WeatherMain?,
) : Parcelable
