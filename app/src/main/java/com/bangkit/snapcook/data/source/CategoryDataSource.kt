package com.bangkit.snapcook.data.source

import com.bangkit.snapcook.data.model.Category
import com.bangkit.snapcook.data.network.ApiResponse
import com.bangkit.snapcook.data.network.services.CategoryService
import com.bangkit.snapcook.utils.helper.createResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class CategoryDataSource(
    private val service: CategoryService,
) {
    suspend fun getCategories(): Flow<ApiResponse<List<Category>>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = service.fetchCategories()
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                Timber.e(e.message)
                emit(ApiResponse.Error(e.createResponse()?.message ?: ""))
            }
        }
    }

}