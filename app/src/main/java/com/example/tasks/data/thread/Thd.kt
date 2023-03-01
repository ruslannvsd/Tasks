package com.example.tasks.data.thread

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "thread_db")
data class Thd(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "thdStr") val thdStr: String,
    @ColumnInfo(name = "thdL") val thdL: Long,
    @ColumnInfo(name = "area") val area: Long,
    @ColumnInfo(name = "sts") val status: Boolean
)