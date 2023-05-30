package com.bangkit.snapcook.data.model

import androidx.room.ColumnInfo

data class Recipe (
    val id: String,
    val title: String,
    val mainCategory: String,
    val secondCategoryId: String,
    val authorId: String,
    val photo: String,
    val description: String,
    val totalServing: Int,
    val mainIngredients: List<String>,
    val fullIngredients: List<String>,
    val spices: List<String>,
    val utensils: List<Utensil>,
    val estimatedTime: Int,
    val steps: List<String>,
    val slug: String,
    val author: Author,
    val secondCategory: Category,
    val totalBookmark: Int,
    var bookmarkId: String? = null
) {
    data class Author(
        val name: String,
        val photo: String,
        val slug: String
    )

}