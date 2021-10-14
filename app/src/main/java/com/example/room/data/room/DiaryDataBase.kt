package com.example.room.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.room.data.entity.Diary

@Database(entities = [Diary::class], version = 1, exportSchema = false)
abstract class DiaryDataBase : RoomDatabase() {
    abstract fun getDiaryDao(): DiaryDao

    companion object {
        @Volatile
        private var INSTANCE: DiaryDataBase? = null

        fun getDatabase(context: Context): DiaryDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    DiaryDataBase::class.java, "diary_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
