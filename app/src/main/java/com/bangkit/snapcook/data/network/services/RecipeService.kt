package com.bangkit.snapcook.data.network.services

import com.bangkit.snapcook.data.model.Recipe
import com.bangkit.snapcook.data.network.request.RecipeRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeService {

    @GET("/recipe")
    suspend fun fetchRecipe(
        @Query("authorId") authorId: String?,
        @Query("mainCategory") category: String?,
        @Query("secondCategory") secondCategory: String?,
        @Query("search") search: String?,
    ): List<Recipe>

    @GET("/recipe/{id}")
    suspend fun fetchRecipeDetail(
        @Path("id") id: String,
    ): Recipe

    @POST("/recipe")
    @Multipart
    suspend fun addRecipe(
        @Part photo: MultipartBody.Part,
        @Part("title") title: RequestBody,
        @Part("mainCategory") mainCategory: RequestBody? = null,
        @Part("authorId") authorId: RequestBody? = null,
        @Part("description") description: RequestBody,
        @Part("totalServing") totalServing: RequestBody,
        @Part("estimatedTime") estimatedTime: RequestBody,
        @Part("mainIngredients[]") mainIngredients: List<RequestBody>,
        @Part("spices[]") spices: List<RequestBody>,
        @Part("steps[]") steps: List<RequestBody>,
        ): Nothing

    @PUT("/recipe/{id}")
    suspend fun editRecipe(
        @Path("id") id: String,
        @Part photo: MultipartBody.Part,
        @Part("title") title: RequestBody,
        @Part("mainCategory") mainCategory: RequestBody? = null,
        @Part("authorId") authorId: RequestBody? = null,
        @Part("description") description: RequestBody,
        @Part("totalServing") totalServing: RequestBody,
        @Part("estimatedTime") estimatedTime: RequestBody,
        @Part("mainIngredients[]") mainIngredients: List<RequestBody>,
        @Part("spices[]") spices: List<RequestBody>,
        @Part("steps[]") steps: List<RequestBody>,
    ): Nothing

}