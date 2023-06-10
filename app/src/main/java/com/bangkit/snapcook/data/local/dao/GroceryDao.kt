package com.bangkit.snapcook.data.local.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import com.bangkit.snapcook.data.model.Grocery
import com.bangkit.snapcook.data.model.GroceryGroup

@Dao
interface GroceryDao {

    @Query("SELECT * FROM grocery WHERE type =:type AND group_id =:groupId")
    fun getGroceriesByType(type: String, groupId: String): LiveData<List<Grocery>>

    @Query("SELECT * FROM grocery WHERE group_id =:groupId")
    suspend fun getGroceriesByGroupId(groupId: String): List<Grocery>

    @Query("SELECT * FROM grocery_group")
    suspend fun getGroceriesGroup(): List<GroceryGroup>

    @Query("DELETE FROM grocery_group WHERE group_id =:groupId")
    suspend fun deleteGroceriesGroup(groupId: String)

    @Query("SELECT * FROM grocery_group WHERE id =:id")
    fun getGroceriesGroupDetail(id: Int): LiveData<GroceryGroup>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGroceryGroup(grocery: GroceryGroup)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGroceries(groceries: List<Grocery>)

    @Query("SELECT EXISTS(SELECT * FROM grocery_group WHERE group_id = :groupId)")
    suspend fun isGroceryGroupExist(groupId : String) : Boolean

    @Query("UPDATE grocery SET completed = :completed WHERE id =:groceryId AND group_id =:groupId")
    suspend fun updateCompleted(groceryId: Int, groupId: String, completed: Boolean)

    @Query("UPDATE grocery_group SET completed = :completed WHERE group_id =:groupId")
    suspend fun updateGroceryGroupCompleted(groupId: String, completed: Boolean)

}