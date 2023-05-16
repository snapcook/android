package com.bangkit.snapcook.data.network.services

import com.bangkit.snapcook.data.network.request.AddBookmarkRequest
import com.bangkit.snapcook.data.network.request.RecipeRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface BookmarkService {

    @POST("/bookmark")
    suspend fun addToBookmark(
      @Body request: AddBookmarkRequest
    ): Nothing

    @DELETE("/bookmark/{id}")
    suspend fun removeFromBookmark(
        @Path("id") id: String
    ): Nothing

}