package com.example.room.domain.usecase

import com.example.room.data.repository.DiaryRepository
import javax.inject.Inject

class GetAllDiariesUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository
) {
    fun getAllDiaries() = diaryRepository.getAllDiaries()
}
