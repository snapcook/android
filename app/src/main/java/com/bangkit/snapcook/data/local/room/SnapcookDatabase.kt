package com.bangkit.snapcook.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bangkit.snapcook.data.local.converter.ListGroceryConverter
import com.bangkit.snapcook.data.local.dao.GroceryDao
import com.bangkit.snapcook.data.model.Grocery
import com.bangkit.snapcook.data.model.GroceryGroup

@Database(
    entities = [Grocery::class, GroceryGroup::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(ListGroceryConverter::class)
abstract class SnapcookDatabase : RoomDatabase() {
    abstract fun getGroceryDao(): GroceryDao
}