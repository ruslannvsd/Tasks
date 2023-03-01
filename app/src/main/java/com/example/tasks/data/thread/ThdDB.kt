package com.example.tasks.data.thread

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Thd::class], version = 1, exportSchema = false)
abstract class ThdDB : RoomDatabase() {
    abstract fun thdDao() : ThdDao
    companion object {
        @Volatile
        private var instance : ThdDB? = null
        @Synchronized
        fun getThreads(ctx: Context) : ThdDB {
            if (instance == null)
                instance = Room.databaseBuilder(ctx.applicationContext, ThdDB::class.java,
                    "thread_db")
                    .fallbackToDestructiveMigration()
                    .build()
            return instance!!
        }
    }
}