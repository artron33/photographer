package com.pierre.photo.data.photographer


interface PhotographersRepository {
    suspend fun getPhotographersByName(name: String): Photo
}