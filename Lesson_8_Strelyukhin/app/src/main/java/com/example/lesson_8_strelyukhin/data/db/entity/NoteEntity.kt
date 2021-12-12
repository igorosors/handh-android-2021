package com.example.lesson_8_strelyukhin.data.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.lesson_8_strelyukhin.R
import com.example.lesson_8_strelyukhin.data.db.dao.NoteDao
import kotlinx.parcelize.Parcelize

@Entity(tableName = NoteDao.TABLE_NAME)
@Parcelize
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "text") var text: String,
    @ColumnInfo(name = "color") var color: Int = R.color.white,
    @ColumnInfo(name = "is_archive") var isArchive: Boolean = false,
) : Parcelable