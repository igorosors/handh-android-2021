package com.example.lesson_7_strelyukhin.data

import java.lang.Exception

sealed class LoadingState<T> {
    class Loading<T> : LoadingState<T>()
    class Data<T>(val data: T) : LoadingState<T>()
    class Error<T>(val error: Exception) : LoadingState<T>()
}