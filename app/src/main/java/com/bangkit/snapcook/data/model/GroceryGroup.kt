package com.bangkit.snapcook.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("grocery_group")
data class GroceryGroup(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "group_id")
    val groupId: String,
    @ColumnInfo(name = "slug")
    val slug: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "photo")
    val photo: String,
    @ColumnInfo(name = "ingredients")
    val ingredients: Int,
    @ColumnInfo(name = "spices")
    val spices: Int,
    @ColumnInfo(name = "utensils")
    val utensils: Int,
    @ColumnInfo(name = "completed")
    val isCompleted: Boolean = false,
)