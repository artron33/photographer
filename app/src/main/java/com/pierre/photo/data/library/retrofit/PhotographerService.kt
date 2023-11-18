package com.pierre.photo.data.library.retrofit

import com.pierre.photo.CONSTANTE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotographerService {

    @GET("search?per_page=${CONSTANTE.PAGINED_PAGE_SIZE}")
    suspend fun listRepos(@Query("query") query: String, @Query("page") page: Int): Response<Photos>

}
