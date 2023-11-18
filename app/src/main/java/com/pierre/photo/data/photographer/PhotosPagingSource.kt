package com.pierre.photo.data.photographer

import android.util.Log
import androidx.paging.PagingState
import com.pierre.photo.data.library.retrofit.PhotographerService
import com.pierre.photo.domain.converter.convertToFavoritePhotographerUi
import com.pierre.photo.presentation.screen.PhotographerUi

class PhotosPagingSource(
    val service: PhotographerService, val name: String, val favoritesIds: MutableSet<Long>
) : androidx.paging.PagingSource<Int, PhotographerUi>() {

    override fun getRefreshKey(state: PagingState<Int, PhotographerUi>) =
        state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotographerUi> = try {
        val currentPage = params.key ?: 1
        service.listRepos(name, currentPage).let { response ->
            if (response.isSuccessful && response.body() != null) {
                val photos = response.body()!!
                val isFullCapacityReached = photos.totalResults <= (photos.perPage * photos.page)
                if (isFullCapacityReached) return LoadResult.Error(Exception("no more item to load"))

                val photographerAndFavorites = photos.photos.map {
                    it.convertToFavoritePhotographerUi(isFavorite = favoritesIds.contains(it.id))
                }
                LoadResult.Page(
                    data = photographerAndFavorites,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = currentPage + 1
                )
            } else {
                // todo proper error network handling
                Log.e("yallah", "error is : " + response.errorBody()?.string())
                LoadResult.Error(Exception("error is : " + response.errorBody()?.string()))
            }

        }
    } catch (e: Exception) {
        Log.e("yallah", "Retrofit Exception: web.error is : " + e.message)
        LoadResult.Error(e)
    }

}
