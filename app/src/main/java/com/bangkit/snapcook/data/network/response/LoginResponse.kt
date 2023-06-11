package com.bangkit.snapcook.data.network.response

import com.bangkit.snapcook.data.model.User

data class LoginResponse(
    val user: User,
    val token: String
)
