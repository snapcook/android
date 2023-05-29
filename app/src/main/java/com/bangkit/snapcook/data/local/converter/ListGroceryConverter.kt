package com.bangkit.snapcook.data.local.converter

import androidx.room.TypeConverter
import com.bangkit.snapcook.data.model.Grocery
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListGroceryConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromGroceryList(groceryList: List<Grocery>): String {
        return gson.toJson(groceryList)
    }

    @TypeConverter
    fun toGroceryList(json: String): List<Grocery> {
        val listType = object : TypeToken<List<Grocery>>() {}.type
        return gson.fromJson(json, listType)
    }
}


