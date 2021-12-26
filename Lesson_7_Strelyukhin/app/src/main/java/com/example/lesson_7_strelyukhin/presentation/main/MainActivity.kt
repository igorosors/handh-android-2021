package com.example.lesson_7_strelyukhin.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.lesson_7_strelyukhin.R
import com.example.lesson_7_strelyukhin.presentation.FragmentListener
import com.example.lesson_7_strelyukhin.presentation.list.FragmentList

class MainActivity : AppCompatActivity(), FragmentListener {

    override fun back(key: String) {
        supportFragmentManager.popBackStack()
    }

    override fun switchToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, FragmentList.newInstance()).commit()
        }
    }
}