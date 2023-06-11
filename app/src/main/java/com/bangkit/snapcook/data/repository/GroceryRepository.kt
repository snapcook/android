package com.bangkit.snapcook.data.repository

import androidx.lifecycle.LiveData
import com.bangkit.snapcook.data.model.Grocery
import com.bangkit.snapcook.data.model.GroceryGroup
import com.bangkit.snapcook.data.network.ApiResponse
import com.bangkit.snapcook.data.source.GroceryDataSource
import kotlinx.coroutines.flow.Flow

class GroceryRepository(
    private val dataSource: GroceryDataSource,
) {
    suspend fun getGroceriesGroup(): Flow<ApiResponse<List<GroceryGroup>>> =
        dataSource.getGroceriesGroup()

    fun getGroceries(type: String, groupId: String): LiveData<List<Grocery>> =
        dataSource.getGroceries(type, groupId)

    fun getGroceriesGroupDetail(id: Int): LiveData<GroceryGroup> =
        dataSource.getGroceriesGroupDetail(id)

    suspend fun checkGroceryStatus(groupId: String) = !dataSource.checkGroceryStatus(groupId)

    suspend fun insertGroceries(groceries: List<Grocery>) {
        dataSource.insertGroceries(groceries)
    }

    suspend fun isGroceryGroupExist(groupId: String): Boolean {
        return dataSource.isGroceryGroupExist(groupId)
    }

    suspend fun insertGroceryGroup(newGroceryGroup: GroceryGroup) {
        dataSource.insertGroceryGroup(newGroceryGroup)
    }

    suspend fun completeGroceryGroup(id: Int, groupId: String, isCompleted: Boolean) {
        dataSource.completeGroceryGroup(id, groupId, isCompleted)
    }

    suspend fun updateGroceryGroupCompleted(groupId: String, isCompleted: Boolean) {
        dataSource.updateGroceryGroupCompleted(groupId, isCompleted)
    }

    suspend fun deleteGroceryGroup(groupId: String) {
        dataSource.deleteGroceryGroup(groupId)
    }


}