package com.example.lesson_5_strelyukhin

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Data(
    var value: String = "default"
) : Parcelable