package com.bangkit.snapcook.data.network.services

import com.bangkit.snapcook.data.network.request.AddBookmarkRequest
import com.bangkit.snapcook.data.network.response.BasicResponse
import com.bangkit.snapcook.data.network.response.BookmarkResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BookmarkService {

    @GET("/bookmark/{authorId}")
    suspend fun fetchBookmarkedRecipe(
        @Path("authorId") authorId: String,
    ): List<BookmarkResponse>

    @POST("/bookmark")
    suspend fun addToBookmark(
      @Body request: AddBookmarkRequest
    ): BasicResponse

    @DELETE("/bookmark/{id}")
    suspend fun removeFromBookmark(
        @Path("id") id: String
    ): BasicResponse

}