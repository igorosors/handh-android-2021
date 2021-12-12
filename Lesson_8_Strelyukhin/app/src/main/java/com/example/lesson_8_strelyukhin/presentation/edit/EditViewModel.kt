package com.example.lesson_8_strelyukhin.presentation.edit

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lesson_8_strelyukhin.data.db.DatabaseClient
import com.example.lesson_8_strelyukhin.data.db.entity.NoteEntity
import com.example.lesson_8_strelyukhin.presentation.FragmentListener
import kotlinx.coroutines.launch

class EditViewModel : ViewModel() {
    fun saveNotes(context: Context, note: NoteEntity, back: () -> Unit) {
        viewModelScope.launch {
            DatabaseClient.getInstance(context).saveNotes(listOf(note))
            Toast.makeText(context, "Заметка сохранена", Toast.LENGTH_SHORT).show()
            back()
        }
    }
}