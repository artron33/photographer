package com.example.reply.data.favoris

import android.content.Context
import com.example.reply.data.library.room.Favorite
import com.example.reply.data.library.room.FavoriteDao
import com.example.reply.data.library.room.FavoriteDatabase
import kotlinx.coroutines.flow.Flow

class FavoriteRepositoryImpl(
    private val favoriteDao: FavoriteDao = FavoriteDatabase.create().locationDao()

): FavoriteRepository {

    override suspend fun getAllFavorites() = favoriteDao.getAllFavorites()
    override suspend fun deleteThisFavorites(favorite: Favorite) = favoriteDao.deleteThisFavorite(favorite)
    override suspend fun addThisFavorites(favorite: Favorite) = favoriteDao.insertThisFavorite(favorite)

}