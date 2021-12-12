package com.example.lesson_8_strelyukhin.presentation.color_selector

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.lesson_8_strelyukhin.R
import com.example.lesson_8_strelyukhin.databinding.ItemButtonBinding

class ButtonsViewHolder(
    parent: ViewGroup,
    private val onItemClick: (Int) -> Unit,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_button, parent, false)
) {
    private val binding by viewBinding(ItemButtonBinding::bind)

    fun bind(color: Int) {
        itemView.setOnClickListener {
            onItemClick(color)
        }
        binding.buttonSelector.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(itemView.context, color))
    }

}