package com.example.room.data.repository

import com.example.room.data.entity.Diary
import com.example.room.data.room.DiaryDao
import javax.inject.Inject

class DiaryRepositoryImpl @Inject constructor(
    private val diaryDao: DiaryDao
) : DiaryRepository {
    override fun getAllDiaries() =
        diaryDao.getAllDiaries()

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
