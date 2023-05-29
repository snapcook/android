package com.bangkit.snapcook.data.model

data class Utensil(
    val id: String,
    val name: String,
    val photo: String,
    val allowDelete: Boolean = false
)
