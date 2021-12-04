package com.example.lesson_7_strelyukhin.presentation

import androidx.fragment.app.Fragment
import com.example.lesson_7_strelyukhin.data.model.AdapterElement

interface FragmentListener {
    fun back(key: String)
    fun switchToFragment(fragment: Fragment)
}