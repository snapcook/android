package com.bangkit.snapcook.data.repository

import com.bangkit.snapcook.data.model.Category
import com.bangkit.snapcook.data.network.ApiResponse
import com.bangkit.snapcook.data.source.CategoryDataSource
import com.bangkit.snapcook.data.source.RecipeDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import java.io.File

class CategoryRepository(
    private val dataSource: CategoryDataSource,
) {
    suspend fun getCategories(): Flow<ApiResponse<List<Category>>> {
        return dataSource.getCategories().flowOn(Dispatchers.IO)
    }
}