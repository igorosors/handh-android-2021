package com.example.lesson_6_strelyukhin

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.lesson_6_strelyukhin.fragments.PagerFragment

class PagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    companion object {
        val IMAGES = listOf(
            R.drawable.pic_1,
            R.drawable.pic_2,
            R.drawable.pic_3,
        )
    }

    override fun getItemCount() = IMAGES.size

    override fun createFragment(position: Int): Fragment {
        return PagerFragment.newInstance(IMAGES[position], position)
    }
}