package com.example.lesson_6_strelyukhin.model

sealed class AdapterElement(
    open val title: String,
    open val id: Int,
    open val icon: Int,
    open val isAlert: Boolean,
) {
    data class Base(
        override val title: String,
        override val id: Int,
        override val icon: Int,
        override val isAlert: Boolean,
    ) : AdapterElement(title, id, icon, isAlert)

    data class Detail(
        override val title: String,
        override val id: Int,
        override val icon: Int,
        override val isAlert: Boolean,
    ) : AdapterElement(title, id, icon, isAlert)

}