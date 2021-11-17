package com.example.lesson_4_strelyukhin

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson_4_strelyukhin.model.Detail

class CellViewHolder(
    //родитель ячейки
    parent: ViewGroup,
    private val onItemClick: (String) -> Unit,
) : RecyclerView.ViewHolder(
    //создание вью, соотв макету
    LayoutInflater.from(parent.context).inflate(R.layout.detail_item, parent, false)
) {
    private val textViewTitle by lazy { itemView.findViewById<TextView>(R.id.textViewTitle) }
    private val textViewContent by lazy { itemView.findViewById<TextView>(R.id.textViewContent) }
    private val imageViewIcon by lazy { itemView.findViewById<ImageView>(R.id.imageViewIcon) }

    fun bind(detail: Detail) {
        itemView.setOnClickListener {
            onItemClick(detail.title)
        }
        textViewTitle.text = detail.title
        textViewContent.text = detail.content
        imageViewIcon.setImageResource(detail.icon)
        //Context(Resource)Compat класс, позволяющий достать например ресурс !! если есть иф, обязательно надо else
        textViewContent.setTextColor(ContextCompat.getColor(itemView.context, if (detail.isRed) R.color.red else R.color.gray))
    }
}