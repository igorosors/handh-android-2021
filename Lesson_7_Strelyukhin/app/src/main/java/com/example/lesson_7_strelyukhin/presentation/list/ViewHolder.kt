package com.example.lesson_7_strelyukhin.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson_7_strelyukhin.R
import com.example.lesson_7_strelyukhin.data.model.AdapterElement

class ViewHolder(
    parent: ViewGroup,
    private val onItemClick: (AdapterElement) -> Unit,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_bridge, parent, false)
) {

    private val imageButtonBell by lazy { itemView.findViewById<ImageButton>(R.id.imageButtonBell) }
    private val textViewBridgeName by lazy { itemView.findViewById<TextView>(R.id.textViewBridgeName) }
    private val textViewBridgeTime by lazy { itemView.findViewById<TextView>(R.id.textViewBridgeTime) }
    private val imageViewBridgeState by lazy { itemView.findViewById<ImageView>(R.id.imageViewBridgeState) }

    fun bind(bridge: AdapterElement) {
        itemView.setOnClickListener {
            onItemClick(bridge)
        }
        imageButtonBell.setImageResource(R.drawable.ic_bell_off)
        imageButtonBell.setOnClickListener {
            if (bridge.isBell) {
                imageButtonBell.setImageResource(R.drawable.ic_bell_off)
            } else {
                imageButtonBell.setImageResource(R.drawable.ic_bell_on)
            }
            bridge.isBell = !bridge.isBell
        }
        imageViewBridgeState.setImageResource(bridge.getImageId())
        textViewBridgeName.text = bridge.name
        textViewBridgeTime.text = buildString {
            bridge.divorces?.forEach { divorce ->
                append("${divorce.start} - ${divorce.end}\t\t\t")
            }
        }
    }

}