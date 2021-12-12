package com.example.lesson_8_strelyukhin.presentation.list

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lesson_8_strelyukhin.data.LoadingState
import com.example.lesson_8_strelyukhin.data.db.DatabaseClient
import com.example.lesson_8_strelyukhin.data.db.entity.NoteEntity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NotesViewModel : ViewModel() {
    private val _notesLiveData = MutableLiveData<List<NoteEntity>>()
    val notesLiveData: LiveData<List<NoteEntity>> = _notesLiveData

    private val _loadingStateLiveData = MutableLiveData<LoadingState<List<NoteEntity>>>()
    val loadingStateLiveData: LiveData<LoadingState<List<NoteEntity>>> = _loadingStateLiveData

    fun loadNotes(context: Context) {
        viewModelScope.launch {
            try {
                _loadingStateLiveData.postValue(LoadingState.Loading())
                DatabaseClient.getInstance(context).getNotesFlow().collect {
                    _loadingStateLiveData.postValue(LoadingState.Data(it))
                }
            } catch (e: Exception) {
                _loadingStateLiveData.postValue(LoadingState.Error(e))
            }
        }
    }

    fun subscribeToNotes(context: Context) {
        viewModelScope.launch {
            DatabaseClient.getInstance(context).getNotesFlow().collect {
                _notesLiveData.postValue(it)
            }
        }
    }

    fun archiveNote(context: Context, note: NoteEntity) {
        viewModelScope.launch {
            DatabaseClient.getInstance(context).saveNotes(listOf(NoteEntity(note.id, note.title, note.text, isArchive = true)))
        }
    }

    fun deleteNote(context: Context, note: NoteEntity) {
        viewModelScope.launch {
            DatabaseClient.getInstance(context).deleteNote(note)
        }
    }

}