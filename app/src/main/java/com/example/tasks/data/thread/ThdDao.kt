package com.example.tasks.data.thread

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ThdDao {
    @Insert
    fun insert(th: Thd)
    @Update
    fun update(th: Thd)
    @Delete
    fun delete(th: Thd)
    @Query("select * from thread_db where area == :area")
    fun threads(area: Long) : LiveData<List<Thd>>
    @Query("select * from thread_db")
    fun all() : LiveData<List<Thd>>
    @Query("delete from thread_db where area == :area")
    fun deleteAreaThreads(area: Long)
}