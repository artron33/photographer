package com.pierre.photo.data.favoris

import com.pierre.photo.data.library.room.Favorite
import com.pierre.photo.data.photographer.Photographer

interface FavoriteRepository {
    suspend fun getAllFavorites(): List<Favorite>?
    suspend fun deleteThisPhotographerFromFavorites(photographer: Photographer)
    suspend fun addThisPhotographerToFavorites(photographer: Photographer)
}
