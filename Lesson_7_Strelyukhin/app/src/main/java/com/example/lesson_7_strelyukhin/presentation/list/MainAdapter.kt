package com.example.lesson_7_strelyukhin.presentation.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson_7_strelyukhin.data.model.AdapterElement

class MainAdapter : RecyclerView.Adapter<ViewHolder>() {

    lateinit var onItemClick: (AdapterElement) -> Unit

    private val items = mutableListOf<AdapterElement>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent, onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(items: List<AdapterElement>) {
        this.items.apply {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

}