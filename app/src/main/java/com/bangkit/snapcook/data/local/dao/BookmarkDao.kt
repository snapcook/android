package com.bangkit.snapcook.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bangkit.snapcook.data.model.Recipe

@Dao
interface BookmarkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRecipe(recipe:List<Recipe>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe:Recipe)

    @Query("SELECT * FROM recipe WHERE id = :id")
    suspend fun getDetailRecipe(id: String): Recipe

    @Update
    fun updateBookmarkRecipe(recipe: Recipe)

    @Query("SELECT * FROM recipe WHERE is_bookmark = 1")
    suspend fun getAllBookmarkedRecipe(): List<Recipe>

    @Query("SELECT EXISTS(SELECT * FROM recipe WHERE id = :id)")
    suspend fun isRecipeExist(id : String) : Boolean

    @Query("UPDATE recipe SET is_bookmark = :isBookmark WHERE id = :id")
    suspend fun updateRecipeBookmarkStatus(isBookmark: Boolean, id: String)

    @Query("DELETE FROM recipe WHERE is_bookmark = 0")
    fun deleteAll()
}
