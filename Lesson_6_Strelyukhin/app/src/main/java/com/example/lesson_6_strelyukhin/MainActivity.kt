package com.example.lesson_6_strelyukhin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.lesson_6_strelyukhin.fragments.FragmentItemOne
import com.example.lesson_6_strelyukhin.fragments.FragmentItemTwo
import com.example.lesson_6_strelyukhin.fragments.FragmentItemThree
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), FragmentListener {

    private val bottomNavigation by lazy { findViewById<BottomNavigationView>(R.id.bottomNavigation)}

    private fun switchToFragment(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment).commit()
        return true
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        switchToFragment(FragmentItemOne.newInstance())

        bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.itemOne -> switchToFragment(FragmentItemOne.newInstance())
                R.id.itemTwo -> switchToFragment(FragmentItemTwo.newInstance())
                R.id.itemThree -> switchToFragment(FragmentItemThree.newInstance())
                else -> false
            }
        }

    }
}