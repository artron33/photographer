package com.example.reply.data.favoris

import com.example.reply.data.converter.convertToFavorite
import com.example.reply.data.library.room.FavoriteDao
import com.example.reply.data.library.room.AppDatabase
import com.example.reply.data.photographer.Photographer

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
