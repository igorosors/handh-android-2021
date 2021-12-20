package com.example.lesson_10_strelyukhin.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Divorce(
    @SerializedName("start") val start: String?,
    @SerializedName("end") val end: String?,
) : Parcelable