package com.bangkit.snapcook.data.network.services

import com.bangkit.snapcook.data.model.Recipe
import com.bangkit.snapcook.data.network.request.PredictIngredientRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeService {

    @GET("/recipe")
    suspend fun fetchRecipe(
        @Query("authorId") authorId: String? = null,
        @Query("mainCategory") category: String? = null,
        @Query("secondCategory") secondCategory: String? = null,
        @Query("search") search: String? = null,
    ): List<Recipe>

    @POST("/predict")
    suspend fun predictIngredient(
        @Body request: PredictIngredientRequest
    ): List<Recipe>

    @GET("/recipe/{slug}")
    suspend fun fetchRecipeDetail(
        @Path("slug") slug: String,
    ): Recipe

    @POST("/recipe")
    @Multipart
    suspend fun addRecipe(
        @Part photo: MultipartBody.Part,
        @Part("title") title: RequestBody,
        @Part("mainCategory") mainCategory: RequestBody? = null,
        @Part("secondCategoryId") secondCategoryId: RequestBody? = null,
        @Part("authorId") authorId: RequestBody? = null,
        @Part("description") description: RequestBody,
        @Part("totalServing") totalServing: RequestBody,
        @Part("estimatedTime") estimatedTime: RequestBody,
        @Part("mainIngredients[]") mainIngredients: List<@JvmSuppressWildcards RequestBody>,
        @Part("spices[]") spices: List<@JvmSuppressWildcards RequestBody>,
        @Part("steps[]") steps: List<@JvmSuppressWildcards RequestBody>,
        @Part("utensils[]") utensils: List<@JvmSuppressWildcards RequestBody>,
    ): Recipe

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