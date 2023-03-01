package com.example.tasks.data.area

import androidx.lifecycle.LiveData

class AreaRep(private val areaDao: AreaDao) {
    private val allAreas: LiveData<List<Area>> = areaDao.areas()

    fun insert(a: Area) {
        areaDao.insert(a)
    }
    fun update(a: Area) {
        areaDao.update(a)
    }
    fun delete(a: Area) {
        areaDao.delete(a)
    }
    fun areas() = allAreas
}