package com.bangkit.snapcook.data.network.request

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)
