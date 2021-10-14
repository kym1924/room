package com.example.room.domain.usecase

import com.example.room.data.repository.DiaryRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteDiaryUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository
) {
    fun deleteDiary(idx: Int) = flow {
        runCatching {
            diaryRepository.deleteDiary(idx)
        }.onSuccess {
            emit(true)
        }.onFailure {
            emit(false)
        }
    }
}
