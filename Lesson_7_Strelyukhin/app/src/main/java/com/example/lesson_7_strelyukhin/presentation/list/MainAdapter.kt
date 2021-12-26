package com.example.lesson_7_strelyukhin.presentation.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson_7_strelyukhin.data.model.Bridge

class MainAdapter : RecyclerView.Adapter<ViewHolder>() {

    lateinit var onItemClick: (Bridge) -> Unit
    lateinit var onBellClick: (Int) -> Unit
    private val items = mutableListOf<Bridge>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent, onItemClick, onBellClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(items: List<Bridge>) {
        this.items.apply {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

}