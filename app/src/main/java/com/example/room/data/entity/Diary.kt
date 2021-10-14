package com.example.room.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.room.util.getToday

@Entity(tableName = "diary_database")
data class Diary(
    @PrimaryKey(autoGenerate = true) val idx: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "date") val date: String = getToday()
)
