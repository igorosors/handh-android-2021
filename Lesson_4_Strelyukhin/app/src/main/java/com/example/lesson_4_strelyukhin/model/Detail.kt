package com.example.lesson_4_strelyukhin.model

import com.example.lesson_4_strelyukhin.AdapterElement

data class Detail(
    val title: String,
    val content: String,
    val icon: Int,
    val isRed: Boolean = false,
) : AdapterElement