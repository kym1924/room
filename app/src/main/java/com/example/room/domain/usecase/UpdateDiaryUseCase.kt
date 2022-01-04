package com.example.room.domain.usecase

import com.example.room.data.repository.DiaryRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateDiaryUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository
) {
    fun updateDiary(idx: Int, title: String, content: String) = flow<Boolean> {
        runCatching {
            diaryRepository.updateDiary(idx, title, content)
        }.onSuccess {
            emit(true)
        }.onFailure {
            emit(false)
        }
    }
}
