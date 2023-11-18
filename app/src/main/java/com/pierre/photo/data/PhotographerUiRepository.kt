package com.pierre.photo.data

import androidx.paging.PagingData
import com.pierre.photo.data.library.room.FavoriteDataBase
import com.pierre.photo.presentation.screen.PhotographerUi
import kotlinx.coroutines.flow.Flow

interface PhotographerUiRepository {
    suspend fun getAllFavoritesFromDb(): Flow<List<PhotographerUi>>
    suspend fun insertOrDeleteThisPhotographerAsFavorites(photographer: FavoriteDataBase)

    fun getPagingData(name: String): Flow<PagingData<PhotographerUi>>
}
