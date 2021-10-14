package com.example.room.domain.usecase

import com.example.room.data.repository.DiaryRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetDetailDiaryUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository
) {
    fun getDetailDiary(idx: Int) = flow {
        emit(diaryRepository.getDetailDiary(idx))
    }
}
