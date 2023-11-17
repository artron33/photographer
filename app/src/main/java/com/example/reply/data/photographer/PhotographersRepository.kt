package com.example.reply.data.photographer


interface PhotographersRepository {
    suspend fun getPhotographersByName(name: String): Photo
}