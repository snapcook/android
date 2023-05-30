package com.bangkit.snapcook.data.local.dao

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.bangkit.snapcook.data.model.Recipe

@Dao
interface BookmarkDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllRecipe(recipe:List<Recipe>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe:Recipe)

    @Query("SELECT * FROM recipe WHERE id = :id")
    suspend fun getDetailRecipe(id: String): Recipe


    @Query("SELECT * FROM recipe WHERE isBookmarked = 1")
    suspend fun getBookmarkedRecipe(): List<Recipe>

    @RawQuery(observedEntities = [Recipe::class])
    suspend fun getSearchRecipe(query: SupportSQLiteQuery): List<Recipe>

    @Query("UPDATE recipe SET isBookmarked = :isBookmark WHERE id =:id")
    suspend fun updateGroceryGroupCompleted(id: String, isBookmark: Boolean)
}
