package com.example.lesson_8_strelyukhin.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.lesson_8_strelyukhin.R
import com.example.lesson_8_strelyukhin.databinding.ActivityMainBinding
import com.example.lesson_8_strelyukhin.presentation.list.NotesFragment

class MainActivity : AppCompatActivity(), FragmentListener {
    private val binding by viewBinding(ActivityMainBinding::bind)

    override fun back(key: String) {
        supportFragmentManager.popBackStack()
    }

    override fun switchToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .addToBackStack(null)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager
            .beginTransaction()
            .replace(binding.fragmentContainer.id, NotesFragment.newInstance())
            .commit()
    }
}
