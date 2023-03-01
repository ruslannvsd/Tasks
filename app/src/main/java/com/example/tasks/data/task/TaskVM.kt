package com.example.tasks.data.task

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskVM(app: Application) : AndroidViewModel(app) {
    private val rep: TaskRep

    init {
        val taskDao = TaskDB.getTasks(app).taskDao()
        rep = TaskRep(taskDao)
    }

    fun tasks(th: Long) = rep.tasks(th)
    fun areaTasks(area: Long) = rep.areaTasks(area)
    fun all() = rep.all()
    fun upcoming(endTime: Long) = rep.upcoming(endTime)
    fun insert(t: Task) {
        CoroutineScope(Dispatchers.IO).launch {
            rep.insert(t)
        }
    }

    fun update(t: Task) {
        CoroutineScope(Dispatchers.IO).launch {
            rep.update(t)
        }
    }

    fun delete(t: Task) {
        CoroutineScope(Dispatchers.IO).launch {
            rep.delete(t)
        }
    }

    fun deleteAreaTasks(area: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            rep.deleteAreaTasks(area)
        }
    }

    suspend fun tasksToDelete(area: Long) : List<Task> {
        return rep.tasksToDelete(area)
    }
}