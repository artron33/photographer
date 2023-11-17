package com.example.reply.data.favoris

import com.example.reply.data.library.room.Favorite
import com.example.reply.data.photographer.Photographer

interface FavoriteRepository {
    suspend fun getAllFavorites(): List<Favorite>?
    suspend fun deleteThisPhotographerFromFavorites(photographer: Photographer)
    suspend fun addThisPhotographerToFavorites(photographer: Photographer)
}
