package com.example.reply.data.library.retrofit

import com.example.reply.data.photographer.PhotographerService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitApp {
    private val APIKEY = "1Ae3i5BCPTcQUsJAuukK8lt7BD9rQsdRN4XQqxyNv050wFQEccl0czuL"
    private val AUTHORIZATION = "Authorization"
    private val URL = "https://api.pexels.com/v1/"

    val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(URL)
            .client(OkHttpClient.Builder()
                .addInterceptor (
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                )
                .addInterceptor { chain ->
                chain.request().newBuilder().addHeader(AUTHORIZATION, APIKEY).build().let {
                    chain.proceed(it)
                }
            }.build())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }
    val photographerService by lazy { retrofit.create(PhotographerService::class.java) }

}
