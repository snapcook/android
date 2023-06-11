package com.bangkit.snapcook.data.source

import com.bangkit.snapcook.data.local.dao.BookmarkDao
import com.bangkit.snapcook.data.model.Recipe
import com.bangkit.snapcook.data.network.ApiResponse
import com.bangkit.snapcook.data.network.request.AddBookmarkRequest
import com.bangkit.snapcook.data.network.services.BookmarkService
import com.bangkit.snapcook.data.network.services.RecipeService
import com.bangkit.snapcook.utils.PreferenceManager
import com.bangkit.snapcook.utils.helper.createResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class BookmarkDataSource(
    private val bookmarkService: BookmarkService,
    private val pref: PreferenceManager,
    private val dao: BookmarkDao

) {

    suspend fun fetchBookmarkedRecipe(): Flow<ApiResponse<List<Recipe>>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val bookmarkedRecipe = dao.getBookmarkedRecipe()
                if (bookmarkedRecipe.isNotEmpty()){
                    emit(ApiResponse.Success(bookmarkedRecipe))
                    return@flow
                }

                val authorId = pref.getUserId

                // Map dari response ke recipe aja
                val recipes = bookmarkService.fetchBookmarkedRecipe(authorId).map { response ->
                    val recipe = response.recipe
                    dao.insertRecipe(recipe)
                    dao.updateRecipeBookmarked(recipe.id, true)
                    return@map recipe
                }

                if (recipes.isNotEmpty()) {
                    emit(ApiResponse.Success(recipes))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }
    }

    suspend fun addBookmark(
        id: String
    ): Flow<ApiResponse<String>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val authorId = pref.getUserId
                dao.updateRecipeBookmarked(id, true)

                val response = bookmarkService.addToBookmark(
                    AddBookmarkRequest(
                        authorId,
                        id)
                )

                emit(ApiResponse.Success("Success"))
            } catch (e: Exception) {
                Timber.d(e.message)
                emit(ApiResponse.Error(e.createResponse()?.message ?: ""))
            }
        }
    }

    suspend fun removeBookmark(
        id: String
    ): Flow<ApiResponse<String>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                dao.updateRecipeBookmarked(id, false)
                val response = bookmarkService.removeFromBookmark(id)
                emit(ApiResponse.Success(response.message))
            } catch (e: Exception) {
                Timber.e(e.message)
                emit(ApiResponse.Error(e.createResponse()?.message ?: ""))
            }
        }
    }
}
