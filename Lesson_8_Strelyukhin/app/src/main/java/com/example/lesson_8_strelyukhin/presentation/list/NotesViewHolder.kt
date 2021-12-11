package com.example.lesson_8_strelyukhin.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.lesson_8_strelyukhin.R
import com.example.lesson_8_strelyukhin.data.db.entity.NoteEntity
import com.example.lesson_8_strelyukhin.databinding.ItemNoteBinding

class NotesViewHolder(
    parent: ViewGroup,
    private val onItemClick: (NoteEntity) -> Unit,
    private val onLongItemClick: (NoteEntity) -> Unit,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
) {
    private val binding by viewBinding(ItemNoteBinding::bind)

    fun bind(note: NoteEntity) {
        itemView.setOnClickListener {
            onItemClick(note)
        }

        itemView.setOnLongClickListener {
            onLongItemClick(note)
            true
        }

        binding.cardViewNote.setCardBackgroundColor(ContextCompat.getColor(itemView.context, note.color))
        binding.textViewNoteTitle.text = note.title
        binding.textViewNoteText.text = note.text
        binding.textViewNoteTitle.setTextColor(ContextCompat
            .getColor(itemView.context, if (note.color == R.color.white) R.color.black else R.color.white))
        binding.textViewNoteText.setTextColor(ContextCompat
            .getColor(itemView.context, if (note.color == R.color.white) R.color.gray else R.color.white))
    }

}