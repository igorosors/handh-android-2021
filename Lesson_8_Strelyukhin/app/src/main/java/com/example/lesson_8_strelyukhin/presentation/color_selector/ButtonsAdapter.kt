package com.example.lesson_8_strelyukhin.presentation.color_selector

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ButtonsAdapter : RecyclerView.Adapter<ButtonsViewHolder>() {

    lateinit var onItemClick: (Int) -> Unit
    private val items = mutableListOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonsViewHolder {
        return ButtonsViewHolder(parent, onItemClick)
    }

    override fun onBindViewHolder(holder: ButtonsViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(items: List<Int>) {
        this.items.apply {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

}

