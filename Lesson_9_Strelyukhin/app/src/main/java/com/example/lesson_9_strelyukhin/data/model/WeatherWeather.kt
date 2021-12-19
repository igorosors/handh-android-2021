package com.example.lesson_9_strelyukhin.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherWeather(
    @SerializedName("main") val main: String?,
) : Parcelable