package com.example.lesson_8_strelyukhin.presentation.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson_8_strelyukhin.data.db.entity.NoteEntity

class NotesAdapter : RecyclerView.Adapter<NotesViewHolder>() {

    lateinit var onItemClick: (NoteEntity) -> Unit
    lateinit var onLongItemClick: (NoteEntity) -> Unit
    private val items = mutableListOf<NoteEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(parent, onItemClick, onLongItemClick)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(items: List<NoteEntity>) {
        this.items.apply {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

}

