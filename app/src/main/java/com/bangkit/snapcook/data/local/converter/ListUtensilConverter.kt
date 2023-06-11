package com.bangkit.snapcook.data.local.converter

import androidx.room.TypeConverter
import com.bangkit.snapcook.data.model.Utensil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListUtensilConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromUtensilList(groceryList: List<Utensil>): String {
        return gson.toJson(groceryList)
    }

    @TypeConverter
    fun toUtensilList(json: String): List<Utensil> {
        val listType = object : TypeToken<List<Utensil>>() {}.type
        return gson.fromJson(json, listType)
    }
}


