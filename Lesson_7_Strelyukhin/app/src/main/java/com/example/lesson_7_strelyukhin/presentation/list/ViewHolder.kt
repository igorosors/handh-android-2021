package com.example.lesson_7_strelyukhin.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.lesson_7_strelyukhin.R
import com.example.lesson_7_strelyukhin.data.model.Bridge
import com.example.lesson_7_strelyukhin.databinding.ItemBridgeBinding

class ViewHolder(
    parent: ViewGroup,
    private val onItemClick: (Bridge) -> Unit,
    private val onBellClick: (Int) -> Unit,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_bridge, parent, false)
) {
    private val binding by viewBinding(ItemBridgeBinding::bind)

    fun bind(bridge: Bridge) {
        itemView.setOnClickListener {
            onItemClick(bridge)
        }
        if (bridge.isBell) {
            binding.imageButtonBell.setImageResource(R.drawable.ic_bell_on)
        } else {
            binding.imageButtonBell.setImageResource(R.drawable.ic_bell_off)
        }

        binding.imageButtonBell.setOnClickListener {
            onBellClick(bridge.id!!)
        }

        binding.imageViewBridgeState.setImageResource(bridge.getImageId())
        binding.textViewBridgeName.text = bridge.name
        binding.textViewBridgeTime.text = buildString {
            bridge.divorces?.forEach { divorce ->
                append("${divorce.start} - ${divorce.end}\t\t\t")
            }
        }
    }

}