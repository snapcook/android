package com.bangkit.snapcook.data.network.response

import com.bangkit.snapcook.data.model.Recipe
import com.google.gson.annotations.SerializedName

class BookmarkResponse(
    val id: String,
    val createdAt: String,
    val updatedAt: String,
    val authorId: String,
    val recipeId: String,
    @SerializedName("recipe")
    val recipe: Recipe
)