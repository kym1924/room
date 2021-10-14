package com.example.room.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.room.data.entity.Diary
import com.example.room.domain.usecase.DeleteDiaryUseCase
import com.example.room.domain.usecase.GetDetailDiaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getDetailDiaryUseCase: GetDetailDiaryUseCase,
    private val deleteDiaryUseCase: DeleteDiaryUseCase
) : ViewModel() {
    private val _diary = MutableStateFlow<Diary?>(null)
    val diary = _diary.asStateFlow()

    private val _isDeleted = MutableSharedFlow<Boolean>()
    val isDeleted = _isDeleted.asSharedFlow()

    fun getDetailDiary(idx: Int) {
        viewModelScope.launch {
            getDetailDiaryUseCase.getDetailDiary(idx).collect { diary ->
                _diary.emit(diary)
            }
        }
    }

    fun deleteDiary(idx: Int) {
        viewModelScope.launch {
            deleteDiaryUseCase.deleteDiary(idx).collect { isDeleted ->
                _isDeleted.emit(isDeleted)
            }
        }
    }
}
