package com.example.tasks.data.area

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AreaDao {
    @Insert
    fun insert(a: Area)
    @Update
    fun update(a: Area)
    @Delete
    fun delete(a: Area)
    @Query("select * from area_db") //order by -alDB DESC")
    fun areas() : LiveData<List<Area>>
}