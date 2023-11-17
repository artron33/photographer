package com.pierre.photo.data.favoris

import com.pierre.photo.data.converter.convertToFavorite
import com.pierre.photo.data.library.room.FavoriteDao
import com.pierre.photo.data.library.room.AppDatabase
import com.pierre.photo.data.photographer.Photographer

class FavoriteRepositoryImpl(
    private val favoriteDao: FavoriteDao = AppDatabase.create().favoriteDao()

) : FavoriteRepository {

    override suspend fun getAllFavorites() = favoriteDao.getAllFavorites()

    override suspend fun deleteThisPhotographerFromFavorites(photographer: Photographer) {
        val favorite = photographer.convertToFavorite()
        favoriteDao.deleteThisFavorite(favorite)
    }

    override suspend fun addThisPhotographerToFavorites(photographer: Photographer) {
        val favorite = photographer.convertToFavorite()
        favoriteDao.insertThisFavorite(favorite)
    }

}
