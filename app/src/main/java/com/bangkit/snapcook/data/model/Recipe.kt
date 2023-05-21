package com.bangkit.snapcook.data.model

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
    val spices: List<String>,
    val utensils: List<String>,
    val estimatedTime: Int,
    val steps: List<String>,
    val slug: String
)