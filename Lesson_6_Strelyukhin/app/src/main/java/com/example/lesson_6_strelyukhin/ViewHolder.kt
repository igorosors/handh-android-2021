package com.example.lesson_6_strelyukhin

import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson_6_strelyukhin.model.AdapterElement

class ViewHolder(
    parent: ViewGroup,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
) {

    private val imageViewInfo by lazy { itemView.findViewById<ImageView>(R.id.imageViewInfo) }
    private val imageViewMore by lazy { itemView.findViewById<ImageView>(R.id.imageViewMore) }
    private val imageViewSend by lazy { itemView.findViewById<ImageView>(R.id.imageViewSend) }
    private val textViewTitle by lazy { itemView.findViewById<TextView>(R.id.textViewTitle) }
    private val textViewId by lazy { itemView.findViewById<TextView>(R.id.textViewId) }
    private val imageViewIcon by lazy { itemView.findViewById<ImageView>(R.id.imageViewIcon) }
    private val textViewLabel1 by lazy { itemView.findViewById<TextView>(R.id.textViewLabel1) }
    private val layoutCell2 by lazy { itemView.findViewById<LinearLayout>(R.id.layoutCell2) }
    private val layoutCell3 by lazy { itemView.findViewById<LinearLayout>(R.id.layoutCell3) }
    private val textViewAlert by lazy { itemView.findViewById<TextView>(R.id.textViewAlert) }
    private val sb by lazy {
        SpannableStringBuilder(parent.context.getString(R.string.text_no_alert)).apply {
            setSpan(
                android.text.style.StyleSpan(android.graphics.Typeface.BOLD),
                16,
                24,
                android.text.Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )
            setSpan(
                android.text.style.StyleSpan(android.graphics.Typeface.BOLD),
                62,
                70,
                android.text.Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )
        }
    }
    private val textViewNoAlert by lazy {
        itemView.findViewById<TextView>(R.id.textViewNoAlert).apply {
            text = sb
        }
    }


    fun bind(detail: AdapterElement.Detail) {

        imageViewInfo.setOnClickListener {
            Toast.makeText(itemView.context, "Info", Toast.LENGTH_SHORT).show()
        }
        imageViewMore.setOnClickListener {
            Toast.makeText(itemView.context, "More", Toast.LENGTH_SHORT).show()
        }
        imageViewSend.setOnClickListener {
            Toast.makeText(itemView.context, "Send", Toast.LENGTH_SHORT).show()
        }
        textViewTitle.text = detail.title
        textViewId.text = detail.id.toString()
        imageViewIcon.setImageResource(detail.icon)
        textViewLabel1.text = "День"
        layoutCell2.visibility = View.VISIBLE
        layoutCell3.visibility = View.VISIBLE
        isAlert(detail.isAlert)
    }

    fun bind(base: AdapterElement.Base) {

        imageViewInfo.setOnClickListener {
            Toast.makeText(itemView.context, "Info", Toast.LENGTH_SHORT).show()
        }
        imageViewMore.setOnClickListener {
            Toast.makeText(itemView.context, "More", Toast.LENGTH_SHORT).show()
        }
        imageViewSend.setOnClickListener {
            Toast.makeText(itemView.context, "Send", Toast.LENGTH_SHORT).show()
        }

        textViewTitle.text = base.title
        textViewId.text = base.id.toString()
        imageViewIcon.setImageResource(base.icon)
        textViewLabel1.text = "Новые показания"
        layoutCell2.visibility = View.GONE
        layoutCell3.visibility = View.GONE
        isAlert(base.isAlert)
    }

    private fun isAlert(isAlert: Boolean) {
        if (isAlert) {
            textViewAlert.visibility = View.VISIBLE
            textViewNoAlert.visibility = View.GONE
        } else {
            textViewAlert.visibility = View.GONE
            textViewNoAlert.visibility = View.VISIBLE
        }
    }

}