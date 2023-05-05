package com.bangkit.snapcook.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bangkit.snapcook.data.local.dao.GroceryDao
import com.bangkit.snapcook.data.model.Grocery

@Database(
    entities = [Grocery::class],
    version = 1,
    exportSchema = false
)

abstract class SnapcookDatabase : RoomDatabase() {
    abstract fun getGroceryDao(): GroceryDao
}