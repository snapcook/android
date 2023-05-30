package com.bangkit.snapcook.data.local.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.bangkit.snapcook.data.model.Recipe
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class RecipeConverter {
    @TypeConverter
    fun fromString(value: String?): List<String>? {
        val listType = object : TypeToken<List<String>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<String>?): String? {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromAuthor(author: Recipe.Author): String {
        val authorMap = mapOf(
            "name" to author.name,
            "photo" to author.photo,
            "slug" to author.slug
        )
        return Gson().toJson(authorMap)
    }

    @TypeConverter
    fun toAuthor(authorString: String): Recipe.Author {
        val gson = Gson()
        val authorMap = gson.fromJson(authorString, Map::class.java) as Map<String, String>

        return Recipe.Author(
            name = authorMap["name"] ?: "",
            photo = authorMap["photo"] ?: "",
            slug = authorMap["slug"] ?: ""
        )
    }
}




