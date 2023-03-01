package com.example.tasks.data.thread

class ThdRep(private val thdDao: ThdDao) {
    fun threads(a: Long) = thdDao.threads(a)
    fun all() = thdDao.all()
    fun insert(th: Thd) {
        thdDao.insert(th)
    }
    fun update(th: Thd) {
        thdDao.update(th)
    }
    fun delete(th: Thd) {
        thdDao.delete(th)
    }
    fun deleteAreaThreads(ar: Long) {
        thdDao.deleteAreaThreads(ar)
    }
}