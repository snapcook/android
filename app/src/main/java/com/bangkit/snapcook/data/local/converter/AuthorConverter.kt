package com.bangkit.snapcook.data.local.converter

import androidx.room.TypeConverter
import com.bangkit.snapcook.data.model.Author
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AuthorConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromAuthor(author: Author): String {
        return gson.toJson(author)
    }

    @TypeConverter
    fun toAuthor(json: String): Author {
        val listType = object : TypeToken<Author>() {}.type
        return gson.fromJson(json, listType)
    }
}




