package com.example.room.data.repository

import androidx.paging.PagingData
import com.example.room.data.entity.Diary
import kotlinx.coroutines.flow.Flow

interface DiaryRepository {
    fun getAllDiaries(): Flow<PagingData<Diary>>

    suspend fun getDetailDiary(idx: Int): Diary

    suspend fun writeDiary(diary: Diary)

    suspend fun updateDiary(idx: Int, title: String, content: String)

    suspend fun deleteDiary(idx: Int)
}
