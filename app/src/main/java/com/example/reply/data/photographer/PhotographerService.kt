package com.example.reply.data.photographer

import retrofit2.http.GET
import retrofit2.http.Query

interface PhotographerService {

    @GET("search?per_page=42")
    suspend fun listRepos(@Query("query") query: String): Photo

}
