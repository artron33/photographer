package com.example.reply.data.photographer


interface PhotographersRepository {
    suspend fun getAllPhotographers(): Photo
    suspend fun getPhotographersByName(name: String): Photo
}