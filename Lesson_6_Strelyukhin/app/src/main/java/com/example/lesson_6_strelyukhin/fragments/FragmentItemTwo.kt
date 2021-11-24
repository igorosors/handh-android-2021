package com.example.lesson_6_strelyukhin.fragments

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson_6_strelyukhin.MainAdapter
import com.example.lesson_6_strelyukhin.MyDividerItemDecoration
import com.example.lesson_6_strelyukhin.R
import com.example.lesson_6_strelyukhin.model.AdapterElement
import com.google.android.material.appbar.MaterialToolbar

class FragmentItemTwo : Fragment(R.layout.fragment_item_2) {

    companion object {
        fun newInstance(): FragmentItemTwo {
            return FragmentItemTwo()
        }
    }

    private fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)

        toolbar.setOnMenuItemClickListener {
            Toast.makeText(context, "menu", Toast.LENGTH_SHORT).show()
            true
        }

        val dividerItemDecoration = MyDividerItemDecoration(8.toPx())
        val adapter = MainAdapter()
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(dividerItemDecoration)

        adapter.setItems(
            listOf(
                *listOf(
                    AdapterElement.Base(
                        title = "Холодная вода",
                        id = 54656553,
                        icon = R.drawable.ic_water_cold,
                        isAlert = true
                    ),
                    AdapterElement.Base(
                        title = "Горячая вода",
                        id = 54656553,
                        icon = R.drawable.ic_water_hot,
                        isAlert = true
                    ),
                ).toTypedArray(),
                *listOf(
                    AdapterElement.Detail(
                        title = "Электроэнергия",
                        id = 54656553,
                        icon = R.drawable.ic_electro,
                        isAlert = false
                    )
                ).toTypedArray(),
            )
        )
    }

}