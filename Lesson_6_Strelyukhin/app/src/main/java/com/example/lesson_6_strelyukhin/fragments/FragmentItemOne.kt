package com.example.lesson_6_strelyukhin.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.lesson_6_strelyukhin.R
import com.google.android.material.appbar.MaterialToolbar

class FragmentItemOne : Fragment(R.layout.fragment_item_1) {
    companion object {
        fun newInstance(): FragmentItemOne {
            return FragmentItemOne()
        }
    }

    private val toolbar by lazy { view?.findViewById<MaterialToolbar>(R.id.toolbar) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar?.setOnMenuItemClickListener {
            Toast.makeText(context, it.title, Toast.LENGTH_SHORT).show()
            true
        }
    }

}