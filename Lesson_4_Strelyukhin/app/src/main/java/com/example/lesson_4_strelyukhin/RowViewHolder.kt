package com.example.lesson_4_strelyukhin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson_4_strelyukhin.model.Base
import com.example.lesson_4_strelyukhin.model.Detail

class RowViewHolder(
    //родитель ячейки
    parent: ViewGroup,
    private val onItemClick: (String) -> Unit,
) : RecyclerView.ViewHolder(
    //создание вью, соотв макету
    LayoutInflater.from(parent.context).inflate(R.layout.base_item, parent, false)
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
        textViewContent.visibility = View.VISIBLE
        imageViewIcon.setImageResource(detail.icon)
        //Context(Resource)Compat класс, позволяющий достать например ресурс !! если есть иф, обязательно надо else
        textViewContent.setTextColor(ContextCompat.getColor(itemView.context, if (detail.isRed) R.color.red else R.color.gray))
    }

    fun bind(base: Base) {
        itemView.setOnClickListener {
            onItemClick(base.title)
        }
        textViewTitle.text = base.title
        textViewContent.visibility = View.INVISIBLE
        imageViewIcon.setImageResource(base.icon)
    }


}