package com.example.tasks.data.area

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "area_db")
data class Area(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "areaStr") val areaStr: String,
    @ColumnInfo(name = "areaL") val areaL: Long,
    @ColumnInfo(name = "sts") val status: Boolean
)