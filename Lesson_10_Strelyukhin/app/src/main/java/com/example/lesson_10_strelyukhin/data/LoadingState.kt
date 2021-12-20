package com.example.lesson_10_strelyukhin.data

import com.example.lesson_10_strelyukhin.data.model.Bridge

sealed class LoadingState {
    class Loading() : LoadingState()
    class Data(val data: List<Bridge>) : LoadingState()
    class Error(val error: Exception) : LoadingState()
}