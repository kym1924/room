package com.example.room.presentation.list

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.room.data.entity.Diary
import com.example.room.domain.usecase.GetAllDiariesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getAllDiariesUseCase: GetAllDiariesUseCase
) : ViewModel() {
    fun getAllDiaries(): Flow<PagingData<Diary>> {
        return getAllDiariesUseCase.getAllDiaries()
    }
}
