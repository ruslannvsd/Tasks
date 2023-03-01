package com.example.tasks.data.task

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_db")
data class Task(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = " id") val id: Int,
    @ColumnInfo(name = "task") val task: String,
    @ColumnInfo(name = "note") val note: String?,
    @ColumnInfo(name = "alarm") val alarm: Long?,
    @ColumnInfo(name = "alarms") val alarms: String?,
    @ColumnInfo(name = "sts") val status: Boolean,
    @ColumnInfo(name = "thd") val thread: Long,
    @ColumnInfo(name = "area") val area: Long
)