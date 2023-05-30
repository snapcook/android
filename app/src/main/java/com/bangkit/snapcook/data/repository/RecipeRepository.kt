package com.bangkit.snapcook.data.repository

import com.bangkit.snapcook.data.model.Recipe
import com.bangkit.snapcook.data.network.ApiResponse
import com.bangkit.snapcook.data.network.request.PredictIngredientRequest
import com.bangkit.snapcook.data.source.RecipeDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import java.io.File

class RecipeRepository(
    private val dataSource: RecipeDataSource,
) {
    suspend fun addRecipe(
        photo: File,
        title: String,
        description: String,
        mainCategory: String,
        secondCategoryId: String,
        totalServing: String,
        estimatedTime: String,
        mainIngredients: List<String>,
        spices: List<String>,
        steps: List<String>,
        utensils: List<String>,
    ): Flow<ApiResponse<String>> {
        return dataSource.addRecipe(
            photo,
            title,
            description,
            mainCategory,
            secondCategoryId,
            totalServing,
            estimatedTime,
            mainIngredients,
            spices,
            steps,
            utensils
        ).flowOn(Dispatchers.IO)
    }

    suspend fun getRecipes(
        authorId: String? = null,
        mainCategory: String? = null,
        secondCategoryId: String? = null,
        search: String? = null,
    ): Flow<ApiResponse<List<Recipe>>> {
        return dataSource.fetchRecipes(
            authorId,
            mainCategory,
            secondCategoryId,
            search
        ).flowOn(Dispatchers.IO)
    }

    suspend fun predictIngredients(
        ingredients: List<String>,
    ): Flow<ApiResponse<List<Recipe>>> {
        return dataSource.predictIngredient(PredictIngredientRequest(ingredients)).flowOn(Dispatchers.IO)
    }

    suspend fun getRecipeDetail(slug: String): Flow<ApiResponse<Recipe>> =
        dataSource.fetchRecipeDetail(slug).flowOn(Dispatchers.IO)
}