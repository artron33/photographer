package com.example.reply.data.favoris

import com.example.reply.data.library.room.Favorite
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    suspend fun getAllFavorites(): Flow<List<Favorite>>
    suspend fun deleteThisFavorites(favorite: Favorite)
    suspend fun addThisFavorites(favorite: Favorite)
}