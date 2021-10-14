package com.example.room.domain.usecase

import com.example.room.data.entity.Diary
import com.example.room.data.repository.DiaryRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WriteDiaryUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository
) {
    fun writeDiary(title: String, content: String) = flow {
        runCatching {
            diaryRepository.writeDiary(Diary(title = title, content = content))
        }.onSuccess {
            emit(true)
        }.onFailure {
            emit(false)
        }
    }
}
