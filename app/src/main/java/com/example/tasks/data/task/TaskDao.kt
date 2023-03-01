package com.example.tasks.data.task

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {
    @Insert
    fun insert(t: Task)

    @Update
    fun update(t: Task)

    @Delete
    fun delete(t: Task)

    @Query("select * from task_db where alarm < :endTime order by -alarm DESC")
    fun upcomingTasks(endTime: Long) : LiveData<List<Task>>

    @Query("select * from task_db where thd == :thread order by -alarm DESC")
    fun tasks(thread: Long) : LiveData<List<Task>>

    @Query("select * from task_db where area == :area order by -alarm DESC")
    fun areaTasks(area: Long) : LiveData<List<Task>>

    @Query("select * from task_db order by -alarm DESC")
    fun all() : LiveData<List<Task>>

    @Query("select * from task_db where area == :area order by -alarm DESC")
    suspend fun tasksToDelete(area: Long) : List<Task>

    @Query("delete from task_db where area == :area")
    fun deleteAreaTasks(area: Long)
}