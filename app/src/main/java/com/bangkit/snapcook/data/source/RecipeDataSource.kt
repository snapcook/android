package com.bangkit.snapcook.data.source

import com.bangkit.snapcook.data.model.Recipe
import com.bangkit.snapcook.data.network.ApiResponse
import com.bangkit.snapcook.data.network.services.RecipeService
import com.bangkit.snapcook.utils.PreferenceManager
import com.bangkit.snapcook.utils.helper.createResponse
import com.bangkit.snapcook.utils.helper.toMultipart
import com.bangkit.snapcook.utils.helper.toRequestBody
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.io.File

class RecipeDataSource(
    private val service: RecipeService, private val pref: PreferenceManager
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
        return flow {
            try {
                emit(ApiResponse.Loading)
                val userId = pref.getUserId
                val response = service.addRecipe(
                    photo = photo.toMultipart(),
                    mainCategory = mainCategory.toRequestBody(),
                    secondCategoryId = "clhr4vpnx0002s60bx81gqf86".toRequestBody(),
                    totalServing = totalServing.toRequestBody(),
                    title = title.toRequestBody(),
                    estimatedTime = estimatedTime.toRequestBody(),
                    mainIngredients = mainIngredients.toRequestBody(),
                    spices = spices.toRequestBody(),
                    steps = steps.toRequestBody(),
                    utensils = utensils.toRequestBody(),
                    description = description.toRequestBody(),
                    authorId = userId.toRequestBody()
                )

                emit(ApiResponse.Success("Success"))
            } catch (e: Exception) {
                Timber.e(e.message)
                emit(ApiResponse.Error(e.createResponse()?.message ?: ""))
            }
        }
    }

    suspend fun fetchRecipes(
        authorId: String? = null,
        mainCategory: String? = null,
        secondCategoryId: String? = null,
        search: String? = null
    ): Flow<ApiResponse<List<Recipe>>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = service.fetchRecipe(
                    authorId,
                    mainCategory,
                    secondCategoryId,
                    search
                )

                if (response.isEmpty()) {
                    emit(ApiResponse.Empty)
                    return@flow
                }

                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                Timber.e(e.message)
                emit(ApiResponse.Error(e.createResponse()?.message ?: ""))
            }

        }
    }

    suspend fun fetchRecipeDetail(slug: String): Flow<ApiResponse<Recipe>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = service.fetchRecipeDetail(slug)

                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                Timber.e(e.message)
                emit(ApiResponse.Error(e.createResponse()?.message ?: ""))
            }

        }
    }


}