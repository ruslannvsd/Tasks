package com.example.tasks.data.thread

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ThdVM(app: Application) : AndroidViewModel(app) {
    private val rep: ThdRep
    init {
        val thdDao = ThdDB.getThreads(app).thdDao()
        rep = ThdRep(thdDao)
    }
    fun threads(a: Long) = rep.threads(a)
    fun all() = rep.all()
    fun insert(th: Thd) {
        CoroutineScope(Dispatchers.IO).launch {
            rep.insert(th)
        }
    }
    fun update(th: Thd) {
        CoroutineScope(Dispatchers.IO).launch {
            rep.update(th)
        }
    }
    fun delete(th: Thd) {
        CoroutineScope(Dispatchers.IO).launch {
            rep.delete(th)
        }
    }
    fun deleteAreaThreads(ar: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            rep.deleteAreaThreads(ar)
        }
    }
}