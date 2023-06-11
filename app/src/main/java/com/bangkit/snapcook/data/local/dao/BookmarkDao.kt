package com.bangkit.snapcook.data.local.dao

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.bangkit.snapcook.data.model.Recipe
import com.bangkit.snapcook.data.model.Utensil

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

    @Query("SELECT * FROM recipe WHERE secondCategoryId = :secondCategoryId")
    suspend fun getRecipesByCategoryId(secondCategoryId: String): List<Recipe>

    @Query("UPDATE recipe SET isBookmarked = :isBookmark WHERE id =:id")
    suspend fun updateRecipeBookmarked(id: String, isBookmark: Boolean)
    @Query("UPDATE recipe SET title = :title, photo = :photo, description = :description, totalServing = :totalServing, mainIngredients = :mainIngredients, fullIngredients = :fullIngredients, spices = :spices, utensils = :utensils, estimatedTime = :estimatedTime, steps = :steps, totalBookmark = :totalBookmark, slug = :slug WHERE id = :id")
    suspend fun updateRecipe(
        id: String,
        title: String,
        photo: String,
        description: String,
        totalServing: Int,
        mainIngredients: List<String>,
        fullIngredients: List<String>,
        spices: List<String>,
        utensils: List<Utensil>,
        estimatedTime: Int,
        steps: List<String>,
        totalBookmark: Int,
        slug: String
    )


    @Query("SELECT EXISTS(SELECT * FROM recipe WHERE id = :id)")
    suspend fun isRecipeIsExist(id : String) : Boolean
}
