package com.example.room.di

import android.content.Context
import com.example.room.data.repository.DiaryRepositoryImpl
import com.example.room.data.room.DiaryDao
import com.example.room.data.room.DiaryDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DiaryModule {
    @Provides
    @Singleton
    fun provideDiaryDao(@ApplicationContext context: Context) =
        DiaryDataBase.getDatabase(context).getDiaryDao()

    @Provides
    @Singleton
    fun provideDiaryRepository(diaryDao: DiaryDao) =
        DiaryRepositoryImpl(diaryDao)
}
