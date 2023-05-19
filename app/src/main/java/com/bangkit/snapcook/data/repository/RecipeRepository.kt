package com.bangkit.snapcook.data.repository

import com.bangkit.snapcook.data.network.ApiResponse
import com.bangkit.snapcook.data.source.RecipeDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import java.io.File

class RecipeRepository(
    private val dataSource: RecipeDataSource,
) {

    suspend fun addRecipe(
        photo: File,
        title: String,
        description: String,
        mainCategory: String,
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
            totalServing,
            estimatedTime,
            mainIngredients,
            spices,
            steps,
            utensils
        ).flowOn(Dispatchers.IO)
    }
}