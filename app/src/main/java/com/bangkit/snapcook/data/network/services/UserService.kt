package com.bangkit.snapcook.data.network.services

import com.bangkit.snapcook.data.model.User
import com.bangkit.snapcook.data.network.request.LoginRequest
import com.bangkit.snapcook.data.network.request.RegisterRequest
import com.bangkit.snapcook.data.network.response.LoginResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
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
    ): User

    @PUT("/user/{id}")
    @Multipart
    suspend fun editProfile(
        @Path("id") id: String,
        @Part photo: MultipartBody.Part? = null,
        @Part("title") title: RequestBody? = null,
    ): User

    @POST("/logout")
    suspend fun logout(): Nothing
}