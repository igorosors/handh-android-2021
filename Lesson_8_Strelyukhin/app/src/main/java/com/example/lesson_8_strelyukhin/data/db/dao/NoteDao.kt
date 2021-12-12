package com.example.lesson_8_strelyukhin.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lesson_8_strelyukhin.data.db.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    companion object {
        const val TABLE_NAME = "notes"
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNotes(vararg noteEntity: NoteEntity)

    @Query("select * from $TABLE_NAME WHERE is_archive = 0")
    fun getNotesFlow(): Flow<List<NoteEntity>>

    @Delete
    suspend fun deleteNote(noteEntity: NoteEntity)
}