package com.example.lesson_7_strelyukhin.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.lesson_7_strelyukhin.R
import com.example.lesson_7_strelyukhin.presentation.FragmentInformation.Companion.FRAGMENT_STATE
import com.example.lesson_7_strelyukhin.presentation.FragmentListener
import com.example.lesson_7_strelyukhin.presentation.list.FragmentList

class MainActivity : AppCompatActivity(), FragmentListener {

    override fun back(key: String) {
        onBackPressed()
    }

    override fun onBackPressed() {
        if (!FRAGMENT_STATE) {
            AlertDialog.Builder(this).apply {
                setTitle("Подтверждение")
                setMessage("Вы действительно хотите выйти?")
                setPositiveButton("ДА") { _, _ ->
                    super.onBackPressed()
                }
                show()
            }
        }
        else {
            switchToFragment(FragmentList.newInstance())
        }

    }

    override fun switchToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, FragmentList.newInstance()).commit()
    }
}