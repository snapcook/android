package com.bangkit.snapcook.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bangkit.snapcook.data.local.converter.AuthorConverter
import com.bangkit.snapcook.data.local.converter.CategoryConverter
import com.bangkit.snapcook.data.local.converter.ListGroceryConverter
import com.bangkit.snapcook.data.local.converter.ListStringConverter
import com.bangkit.snapcook.data.local.converter.ListUtensilConverter
import com.bangkit.snapcook.data.local.dao.BookmarkDao
import com.bangkit.snapcook.data.local.dao.GroceryDao
import com.bangkit.snapcook.data.model.Author
import com.bangkit.snapcook.data.model.Grocery
import com.bangkit.snapcook.data.model.GroceryGroup
import com.bangkit.snapcook.data.model.Recipe

@Database(
    entities = [Grocery::class, GroceryGroup::class, Recipe::class, Author::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(
    value = [
        ListGroceryConverter::class,
        AuthorConverter::class,
        ListStringConverter::class,
        CategoryConverter::class,
        ListUtensilConverter::class
    ]
)
abstract class SnapcookDatabase : RoomDatabase() {
    abstract fun getGroceryDao(): GroceryDao
    abstract fun getBookmarkDao(): BookmarkDao

}