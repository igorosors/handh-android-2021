package com.example.lesson_6_strelyukhin.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.lesson_6_strelyukhin.PagerAdapter
import com.example.lesson_6_strelyukhin.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ToggleableFragment : Fragment(R.layout.toggleable_fragment) {
    companion object {
        fun newInstance(): ToggleableFragment {
            return ToggleableFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager)
        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)

        viewPager.adapter = PagerAdapter(this)
        TabLayoutMediator(tabLayout, viewPager) { _, _ -> }.attach()
    }
}