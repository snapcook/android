package com.bangkit.snapcook.data.source

import com.bangkit.snapcook.data.local.dao.BookmarkDao
import com.bangkit.snapcook.data.model.Recipe
import com.bangkit.snapcook.data.network.ApiResponse
import com.bangkit.snapcook.data.network.request.AddBookmarkRequest
import com.bangkit.snapcook.data.network.response.BookmarkResponse
import com.bangkit.snapcook.data.network.services.BookmarkService
import com.bangkit.snapcook.data.network.services.RecipeService
import com.bangkit.snapcook.utils.PreferenceManager
import com.bangkit.snapcook.utils.helper.createResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.util.ArrayList

class BookmarkDataSource(
    private val service: BookmarkService,
    private val pref: PreferenceManager,
) {

    suspend fun fetchBookmarkedRecipe(): Flow<ApiResponse<List<BookmarkResponse>>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val authorId = pref.getUserId
                val recipes = service.fetchBookmarkedRecipe(authorId)

                if (recipes.isEmpty()) {
                    emit(ApiResponse.Empty)
                }

                emit(ApiResponse.Success(recipes))
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

                service.addToBookmark(
                    AddBookmarkRequest(
                        authorId,
                        id)
                )

                emit(ApiResponse.Success("Success"))
            } catch (e: Exception) {
                Timber.e(e.message)
                emit(ApiResponse.Error(e.createResponse()?.message ?: ""))
            }
        }
    }

    suspend fun removeBookmark(
        id: String
    ): Flow<ApiResponse<String>> {
        return flow {
            Timber.d("Remove Bookmark, id: $id")
            try {
                emit(ApiResponse.Loading)


                val response = service.removeFromBookmark(id)

                emit(ApiResponse.Success(response.message))
            } catch (e: Exception) {
                Timber.e(e.message)
                emit(ApiResponse.Error(e.createResponse()?.message ?: ""))
            }
        }
    }
}
