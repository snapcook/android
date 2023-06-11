package com.bangkit.snapcook.data.network.services

import com.bangkit.snapcook.data.model.Utensil
import retrofit2.http.GET

interface UtensilService {

    @GET("/utensil")
    suspend fun fetchUtensils(): List<Utensil>

}