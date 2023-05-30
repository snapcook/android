package com.bangkit.snapcook.utils.helper

import androidx.sqlite.db.SimpleSQLiteQuery

object RawQueryHelper {
    fun searchRecipeQuery(title: String): SimpleSQLiteQuery{
        return SimpleSQLiteQuery("SELECT * FROM recipe WHERE title LIKE '%$title%'")
    }
}