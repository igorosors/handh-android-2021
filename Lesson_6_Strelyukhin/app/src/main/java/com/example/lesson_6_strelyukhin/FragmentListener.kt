package com.example.lesson_6_strelyukhin

import android.content.Context
import android.widget.Toast

interface FragmentListener {
    fun toast (context: Context?, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}