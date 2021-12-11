package com.example.lesson_8_strelyukhin.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.lesson_8_strelyukhin.data.db.dao.NoteDao
import com.example.lesson_8_strelyukhin.data.db.entity.NoteEntity

@Database(
    entities = [
        NoteEntity::class,
    ],
    version = AppDatabase.DATABASE_VERSION
)

abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "app_database"
    }

    abstract fun noteDao(): NoteDao
}