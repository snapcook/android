package com.bangkit.snapcook.data.network.services

import com.bangkit.snapcook.data.model.Category
import com.bangkit.snapcook.data.model.Recipe
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path
import retrofit2.http.Query

interface CategoryService {

    @GET("/category")
    suspend fun fetchCategories(): List<Category>

}