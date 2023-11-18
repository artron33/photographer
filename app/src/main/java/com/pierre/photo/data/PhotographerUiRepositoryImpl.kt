package com.pierre.photo.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.pierre.photo.CONSTANTE
import com.pierre.photo.data.library.retrofit.PhotographerService
import com.pierre.photo.data.library.retrofit.RetrofitApp
import com.pierre.photo.data.library.room.AppDatabase
import com.pierre.photo.data.library.room.FavoriteDao
import com.pierre.photo.data.library.room.FavoriteDataBase
import com.pierre.photo.data.photographer.PhotosPagingSource
import com.pierre.photo.domain.converter.convertToFavoritePhotographerUi
import com.pierre.photo.presentation.screen.PhotographerUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

class PhotographerUiRepositoryImpl(
    private val favoriteDao: FavoriteDao = AppDatabase.create().favoriteDao(),
    private val service: PhotographerService = RetrofitApp.photographerService
) : PhotographerUiRepository {
    private val favoritesIds: MutableSet<Long> = mutableSetOf()

    override suspend fun getAllFavoritesFromDb(): Flow<List<PhotographerUi>> =
        favoriteDao.getAllFavorites()
            .filter { it != null }
            .map { favoritesDb ->
                favoritesDb!!.filter { it.id != null }
                    .let { favorites ->
                        favoritesIds.clear()
                        favoritesIds.addAll(favorites.map { it.id })
                    }
                favoritesDb.map { it.convertToFavoritePhotographerUi(); }
            }

    override suspend fun insertOrDeleteThisPhotographerAsFavorites(favorite: FavoriteDataBase) {
        if (favoritesIds.remove(favorite.id)) {
            favoriteDao.deleteThisFavorite(favorite)
        } else {
            favoriteDao.insertThisFavorite(favorite)
        }
    }

    override fun getPagingData(name: String): Flow<PagingData<PhotographerUi>> = Pager(
            config = PagingConfig(
                pageSize = CONSTANTE.PAGINED_PAGE_SIZE,
                prefetchDistance = 1,
                enablePlaceholders = false,
                initialLoadSize = CONSTANTE.PAGINED_PAGE_SIZE
            ),
            pagingSourceFactory = { PhotosPagingSource(service = service, name, favoritesIds) }
        ).flow

}
