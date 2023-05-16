package com.bangkit.snapcook.data.network.services

import com.bangkit.snapcook.data.network.request.LoginRequest
import com.bangkit.snapcook.data.network.request.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {
    @GET("/user/{id}")
    suspend fun fetchProfile(
        @Path("id") id: String
    ): Nothing

    @POST("/login")
    suspend fun login(
      @Body request: LoginRequest
    ): Nothing

    @POST("/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Nothing

    @POST("/logout")
    suspend fun logout(): Nothing
}