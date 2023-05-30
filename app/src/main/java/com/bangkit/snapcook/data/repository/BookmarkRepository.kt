package com.bangkit.snapcook.data.repository

import com.bangkit.snapcook.data.model.Recipe
import com.bangkit.snapcook.data.network.ApiResponse
import com.bangkit.snapcook.data.network.response.BookmarkResponse
import com.bangkit.snapcook.data.source.BookmarkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

class BookmarkRepository(
    private val dataSource: BookmarkDataSource
) {

    suspend fun getBookmarkedRecipe(): Flow<ApiResponse<List<Recipe>>> {
        return dataSource.fetchBookmarkedRecipe().flowOn(Dispatchers.IO)
    }

    suspend fun addBookmark(
        id: String
    ): Flow<ApiResponse<String>> {
        Timber.d("Remove Bookmark, id: $id")

        return dataSource.addBookmark(
            id
        ).flowOn(Dispatchers.IO)
    }

    suspend fun removeBookmark(
        id: String
    ): Flow<ApiResponse<String>> {
        Timber.d("Remove Bookmark, id: $id")
        return dataSource.removeBookmark(
            id
        ).flowOn(Dispatchers.IO)
    }
}