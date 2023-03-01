package com.example.tasks.data.area

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Area::class], version = 1, exportSchema = false)
abstract class AreaDB : RoomDatabase() {
    abstract fun areaDao() : AreaDao
    companion object {
        @Volatile
        private var instance : AreaDB? = null
        @Synchronized
        fun getAreas(ctx: Context) : AreaDB {
            if (instance == null)
                instance = Room.databaseBuilder(ctx.applicationContext, AreaDB::class.java,
                    "area_db")
                    .fallbackToDestructiveMigration()
                    .build()
            return instance!!
        }
    }
}