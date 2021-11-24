package com.example.lesson_6_strelyukhin.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.ToggleButton
import androidx.fragment.app.Fragment
import com.example.lesson_6_strelyukhin.R

class FragmentItemThree : Fragment(R.layout.fragment_item_3) {
    companion object {
        fun newInstance(): FragmentItemThree {
            return FragmentItemThree()
        }
    }

    private val toggleButton by lazy { view?.findViewById<ToggleButton>(R.id.toggleButton) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragment = ToggleableFragment.newInstance()

        toggleButton?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                childFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, fragment).addToBackStack(null).commit()
            } else {
                childFragmentManager.beginTransaction()
                    .remove(fragment).commit()
            }
        }
    }



}