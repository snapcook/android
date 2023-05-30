package com.bangkit.snapcook.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe")
data class Recipe (
    @PrimaryKey(autoGenerate = false)
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
    var bookmarkId: String? = null,
    var isBookmarked: Boolean = false
) {
}