package com.example.lesson_6_strelyukhin

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson_6_strelyukhin.model.AdapterElement

class MainAdapter : RecyclerView.Adapter<ViewHolder>() {
    companion object {
        private const val DETAIL_ITEM = 0
        private const val BASE_ITEM = 1
    }

    private val items = mutableListOf<AdapterElement>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if ((viewType == DETAIL_ITEM) || (viewType == BASE_ITEM)) {
            ViewHolder(parent)
        } else {
            throw Exception("Unsupported view type")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (getItemViewType(position) == DETAIL_ITEM) {
            holder.bind(items[position] as AdapterElement.Detail)
        } else {
            holder.bind(items[position] as AdapterElement.Base)
        }
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

    override fun getItemViewType(position: Int): Int {
        return if (items[position] is AdapterElement.Detail) {
            DETAIL_ITEM
        } else {
            BASE_ITEM
        }
    }
}