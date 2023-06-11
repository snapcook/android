package com.bangkit.snapcook.data.model

data class User (
    val id: String,
    val createdAt: String,
    val updatedAt: String,
    val email: String,
    val name: String,
    val photo: String,
    val slug: String,
    val role: String
)
