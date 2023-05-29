package com.bangkit.snapcook.data.source

import androidx.lifecycle.LiveData
import com.bangkit.snapcook.data.local.dao.GroceryDao
import com.bangkit.snapcook.data.model.Grocery
import com.bangkit.snapcook.data.model.GroceryGroup
import com.bangkit.snapcook.data.network.ApiResponse
import com.bangkit.snapcook.utils.helper.createResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class GroceryDataSource(
    private val dao: GroceryDao,
) {

    suspend fun getGroceriesGroup(
    ): Flow<ApiResponse<List<GroceryGroup>>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val groceries = dao.getGroceriesGroup()

                if (groceries.isEmpty()) {
                    emit(ApiResponse.Empty)
                    return@flow
                }
                emit(ApiResponse.Success(groceries))
            } catch (e: Exception) {
                Timber.e(e.message)
                emit(ApiResponse.Error(e.createResponse()?.message ?: ""))
            }
        }
    }

    fun getGroceriesGroupDetail(id: Int): LiveData<GroceryGroup> = dao.getGroceriesGroupDetail(id)

    fun getGroceries(type: String, groupId: String): LiveData<List<Grocery>> =
        dao.getGroceriesByType(type, groupId)

    suspend fun checkGroceryStatus(groupId: String): Boolean {
        val data = dao.getGroceriesByGroupId(groupId)
        return data.any { !it.isCompleted }
    }

    suspend fun insertGroceries(groceries: List<Grocery>) {
        dao.insertGroceries(groceries)
    }

    suspend fun insertGroceryGroup(newGroceryGroup: GroceryGroup) {
        dao.insertGroceryGroup(newGroceryGroup)
    }

    suspend fun completeGroceryGroup(id: Int, groupId: String, isCompleted: Boolean) {
        dao.updateCompleted(id, groupId, isCompleted)
    }

    suspend fun updateGroceryGroupCompleted(groupId: String, isCompleted: Boolean) {
        dao.updateGroceryGroupCompleted(groupId, isCompleted)
    }

}