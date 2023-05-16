package com.bangkit.snapcook.data.network.services

import com.bangkit.snapcook.data.network.request.RecipeRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RecipeService {
    @GET("/recipe/{id}")
    suspend fun fetchRecipeDetail(
        @Path("id") id: String
    ): Nothing

    @POST("/recipe")
    suspend fun addRecipe(
      @Body request: RecipeRequest
    ): Nothing

    @PUT("/recipe/{id}")
    suspend fun editRecipe(
        @Path("id") id: String,
        @Body request: RecipeRequest
    ): Nothing

}