package com.example.lesson_8_strelyukhin.presentation

import androidx.fragment.app.Fragment

interface FragmentListener {
    fun back(key: String)
    fun switchToFragment(fragment: Fragment)
}