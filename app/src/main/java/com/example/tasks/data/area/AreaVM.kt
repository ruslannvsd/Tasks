package com.example.tasks.data.area

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AreaVM(app: Application) : AndroidViewModel(app) {
    private val rep: AreaRep
    val areas : LiveData<List<Area>>
    init {
        val areaDao = AreaDB.getAreas(app).areaDao()
        rep = AreaRep(areaDao)
        areas = rep.areas()
    }
    fun insert(a: Area) {
        CoroutineScope(Dispatchers.IO).launch {
            rep.insert(a)
        }
    }
    fun update(a: Area) {
        CoroutineScope(Dispatchers.IO).launch {
            rep.update(a)
        }
    }
    fun delete(a: Area) {
        CoroutineScope(Dispatchers.IO).launch {
            rep.delete(a)
        }
    }
}