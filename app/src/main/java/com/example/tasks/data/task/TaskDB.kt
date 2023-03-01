package com.example.tasks.data.task

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskDB : RoomDatabase() {
    abstract fun taskDao() : TaskDao
    companion object {
        @Volatile
        private var instance : TaskDB? = null
        @Synchronized
        fun getTasks(ctx: Context) : TaskDB {
            if (instance == null)
                instance = Room.databaseBuilder(ctx.applicationContext, TaskDB::class.java,
                    "task_db")
                    .fallbackToDestructiveMigration()
                    .build()
            return instance!!
        }
    }
}