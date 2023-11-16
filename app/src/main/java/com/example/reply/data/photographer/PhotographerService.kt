package com.example.reply.data.photographer

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotographerService {

    @GET("search?per_page=42")
    fun listRepos(@Query("query") query: String?="ben"): Call<Photo>?

}