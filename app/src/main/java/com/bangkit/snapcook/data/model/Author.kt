package com.bangkit.snapcook.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Author(
    val name: String,
    val photo: String,
    @PrimaryKey
    val slug: String
)