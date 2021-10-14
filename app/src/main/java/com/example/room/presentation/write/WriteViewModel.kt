package com.example.room.presentation.write

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.room.data.entity.Diary
import com.example.room.domain.usecase.GetDetailDiaryUseCase
import com.example.room.domain.usecase.UpdateDiaryUseCase
import com.example.room.domain.usecase.WriteDiaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WriteViewModel @Inject constructor(
    private val getDetailDiaryUseCase: GetDetailDiaryUseCase,
    private val writeDiaryUseCase: WriteDiaryUseCase,
    private val updateDiaryUseCase: UpdateDiaryUseCase
) : ViewModel() {
    private val _diary = MutableSharedFlow<Diary>()
    val diary = _diary.asSharedFlow()

    private val _isWritten = MutableSharedFlow<Boolean>()
    val isWritten = _isWritten.asSharedFlow()

    fun getDetailDiary(idx: Int) {
        if (idx != -1) {
            viewModelScope.launch {
                getDetailDiaryUseCase.getDetailDiary(idx).collect { diary ->
                    _diary.emit(diary)
                }
            }
        }
    }

    fun writeOrUpdate(idx: Int, title: String, content: String) {
        if (title.isNotBlank() && content.isNotBlank()) {
            when (idx) {
                -1 -> writeDiary(title, content)
                else -> updateDiary(idx, title, content)
            }
        }
    }

    private fun writeDiary(title: String, content: String) {
        viewModelScope.launch {
            writeDiaryUseCase.writeDiary(title, content).collect { result ->
                _isWritten.emit(result)
            }
        }
    }

    private fun updateDiary(idx: Int, title: String, content: String) {
        viewModelScope.launch {
            updateDiaryUseCase.updateDiary(idx, title, content).collect { result ->
                _isWritten.emit(result)
            }
        }
    }
}
