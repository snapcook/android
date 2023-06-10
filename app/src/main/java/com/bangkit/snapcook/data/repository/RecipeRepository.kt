package com.bangkit.snapcook.data.repository

import com.bangkit.snapcook.data.model.Recipe
import com.bangkit.snapcook.data.model.Utensil
import com.bangkit.snapcook.data.network.ApiResponse
import com.bangkit.snapcook.data.network.request.PredictIngredientRequest
import com.bangkit.snapcook.data.network.response.BasicResponse
import com.bangkit.snapcook.data.source.RecipeDataSource
import com.bangkit.snapcook.utils.helper.extractData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
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
            mainIngredients = mainIngredients.map { ingredients -> ingredients.extractData().second },
            fullIngredients =  mainIngredients,
            spices,
            steps,
            utensils
        ).flowOn(Dispatchers.IO)
    }
    suspend fun editRecipe(
        id: String,
        photo: File?,
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
        return dataSource.editRecipe(
            id,
            photo,
            title,
            description,
            mainCategory,
            secondCategoryId,
            totalServing,
            estimatedTime,
            mainIngredients = mainIngredients.map { ingredients -> ingredients.extractData().second },
            fullIngredients =  mainIngredients,
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

    suspend fun getRecipesOrdered(
        authorId: String? = null,
        mainCategory: String? = null,
        secondCategoryId: String? = null,
        search: String? = null,
    ): Flow<ApiResponse<List<Recipe>>> {
        return dataSource.fetchRecipesOrdered(
            authorId,
            mainCategory,
            secondCategoryId,
            search
        ).flowOn(Dispatchers.IO)
    }

    suspend fun getSearchRecipes(
        search: String?,
    ): Flow<ApiResponse<List<Recipe>>> {
        return dataSource.fetchSearchedRecipe(
            search
        ).flowOn(Dispatchers.IO)
    }

    suspend fun getMyRecipe(): Flow<ApiResponse<List<Recipe>>> {
        return dataSource.fetchMyRecipe().flowOn(Dispatchers.IO)
    }

    suspend fun getCategoryRecipes(
        categoryId: String,
    ): Flow<ApiResponse<List<Recipe>>> {
        return dataSource.fetchCategoryRecipe(
            categoryId
        ).flowOn(Dispatchers.IO)
    }

    suspend fun predictIngredients(
        ingredients: List<String>,
    ): Flow<ApiResponse<List<Recipe>>> {
        return dataSource.predictIngredient(PredictIngredientRequest(ingredients)).flowOn(Dispatchers.IO)
    }

    suspend fun getUtensils(): Flow<ApiResponse<List<Utensil>>> {
        return dataSource.fetchUtensil().flowOn(Dispatchers.IO)
    }

    suspend fun getRecipeDetail(slug: String): Flow<ApiResponse<Recipe>> =
        dataSource.fetchRecipeDetail(slug).flowOn(Dispatchers.IO)

    suspend fun deleteRecipe(id: String): Flow<ApiResponse<BasicResponse>> =
        dataSource.deleteRecipe(id).flowOn(Dispatchers.IO)
}