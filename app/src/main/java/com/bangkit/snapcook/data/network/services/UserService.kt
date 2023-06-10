package com.bangkit.snapcook.data.network.services

import com.bangkit.snapcook.data.model.User
import com.bangkit.snapcook.data.network.request.LoginRequest
import com.bangkit.snapcook.data.network.request.RegisterRequest
import com.bangkit.snapcook.data.network.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {
    @GET("/user/{id}")
    suspend fun fetchProfile(
        @Path("id") id: String
    ): User

    @POST("/login")
    suspend fun login(
      @Body request: LoginRequest
    ): LoginResponse

    @POST("/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Nothing

    @POST("/logout")
    suspend fun logout(): Nothing
}