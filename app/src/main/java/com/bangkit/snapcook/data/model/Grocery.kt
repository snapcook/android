package com.bangkit.snapcook.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Grocery(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "group_id")
    val groupId: String = "",
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "unit")
    val unit: String = "",
    val type: String,
    @ColumnInfo(name = "completed")
    val isCompleted: Boolean = false
)