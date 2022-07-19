package com.example.task1.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "segment_table")
data class Segment(
    @PrimaryKey @ColumnInfo(name = "start") val startValue: Int,
    @ColumnInfo(name = "end") var endValue: Int,
    @ColumnInfo(name = "color") var color: Int
)