package com.example.tasks.data.task

class TaskRep(private val taskDao: TaskDao) {
    fun tasks(th: Long) = taskDao.tasks(th)
    fun areaTasks(area: Long) = taskDao.areaTasks(area)
    fun all() = taskDao.all()
    fun insert(t: Task) {
        taskDao.insert(t)
    }
    fun update(t: Task) {
        taskDao.update(t)
    }
    fun delete(t: Task) {
        taskDao.delete(t)
    }
    fun deleteAreaTasks(area: Long) {
        taskDao.deleteAreaTasks(area)
    }
    fun upcoming(endTime: Long) = taskDao.upcomingTasks(endTime)
    suspend fun tasksToDelete(area: Long) : List<Task> {
        return taskDao.tasksToDelete(area)
    }
}