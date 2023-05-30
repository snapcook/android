package com.bangkit.snapcook.data.local.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.bangkit.snapcook.data.model.Category
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CategoryConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromCategory(author: Category): String {
        return gson.toJson(author)
    }

    @TypeConverter
    fun toCategory(json: String): Category {
        val listType = object : TypeToken<Category>() {}.type
        return gson.fromJson(json, listType)
    }
}




