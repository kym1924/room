package com.example.room.di

import com.example.room.data.repository.DiaryRepositoryImpl
import com.example.room.domain.usecase.DeleteDiaryUseCase
import com.example.room.domain.usecase.GetAllDiariesUseCase
import com.example.room.domain.usecase.GetDetailDiaryUseCase
import com.example.room.domain.usecase.UpdateDiaryUseCase
import com.example.room.domain.usecase.WriteDiaryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideGetAllDiariesUseCase(diaryRepository: DiaryRepositoryImpl) =
        GetAllDiariesUseCase(diaryRepository)

    @Provides
    @Singleton
    fun provideGetDetailDiaryUseCase(diaryRepository: DiaryRepositoryImpl) =
        GetDetailDiaryUseCase(diaryRepository)

    @Provides
    @Singleton
    fun provideAddDiaryUseCase(diaryRepository: DiaryRepositoryImpl) =
        WriteDiaryUseCase(diaryRepository)

    @Provides
    @Singleton
    fun provideUpdateDiaryUseCase(diaryRepository: DiaryRepositoryImpl) =
        UpdateDiaryUseCase(diaryRepository)

    @Provides
    @Singleton
    fun provideDeleteDiaryUseCase(diaryRepository: DiaryRepositoryImpl) =
        DeleteDiaryUseCase(diaryRepository)
}
