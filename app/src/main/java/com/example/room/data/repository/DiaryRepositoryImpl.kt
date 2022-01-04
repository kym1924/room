package com.example.room.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.room.data.entity.Diary
import com.example.room.data.paging.DiaryPagingSource
import com.example.room.data.room.DiaryDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DiaryRepositoryImpl @Inject constructor(
    private val diaryDao: DiaryDao
) : DiaryRepository {
    override fun getAllDiaries(): Flow<PagingData<Diary>> {
        return Pager(
            config = PagingConfig(pageSize = 30, enablePlaceholders = false),
            pagingSourceFactory = { DiaryPagingSource(diaryDao) }
        ).flow
    }

    override suspend fun getDetailDiary(idx: Int) =
        diaryDao.getDetailDiary(idx)

    override suspend fun writeDiary(diary: Diary) {
        diaryDao.writeDiary(diary)
    }

    override suspend fun updateDiary(idx: Int, title: String, content: String) {
        diaryDao.updateDiary(idx, title, content)
    }

    override suspend fun deleteDiary(idx: Int) {
        diaryDao.deleteDiary(idx)
    }
}
