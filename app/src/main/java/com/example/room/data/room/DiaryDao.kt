package com.example.room.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.room.data.entity.Diary

@Dao
interface DiaryDao {
    @Query("SELECT * FROM diary_database ORDER BY idx DESC LIMIT 30 OFFSET :index * 10;")
    suspend fun getAllDiaries(index: Int): List<Diary>

    @Query("SELECT * FROM diary_database WHERE idx = :idx")
    suspend fun getDetailDiary(idx: Int): Diary

    @Insert
    suspend fun writeDiary(diary: Diary)

    @Query("UPDATE diary_database SET title = :title, content = :content WHERE idx = :idx")
    suspend fun updateDiary(idx: Int, title: String, content: String)

    @Query("DELETE FROM diary_database WHERE idx = :idx")
    suspend fun deleteDiary(idx: Int)
}
