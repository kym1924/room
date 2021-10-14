package com.example.room.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.room.data.entity.Diary
import kotlinx.coroutines.flow.Flow

@Dao
interface DiaryDao {
    @Query("SELECT * FROM diary_database ORDER BY idx DESC")
    fun getAllDiaries(): Flow<List<Diary>>

    @Query("SELECT * FROM diary_database WHERE idx = :idx")
    suspend fun getDetailDiary(idx: Int): Diary

    @Insert
    suspend fun writeDiary(diary: Diary)

    @Query("UPDATE diary_database SET title = :title, content = :content WHERE idx = :idx")
    suspend fun updateDiary(idx: Int, title: String, content: String)

    @Query("DELETE FROM diary_database WHERE idx = :idx")
    suspend fun deleteDiary(idx: Int)
}
