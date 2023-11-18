package com.pierre.photo.presentation

import com.pierre.photo.domain.converter.convertToFavoritePhotographerUi
import com.pierre.photo.data.favoris.FavoriteRepository
import com.pierre.photo.data.library.room.FavoriteDataBase
import com.pierre.photo.data.library.retrofit.Photos
import com.pierre.photo.data.library.retrofit.PhotographerWeb
import com.pierre.photo.data.photographer.PhotographersRepository
import com.pierre.photo.data.library.retrofit.Src
import com.pierre.photo.presentation.library.AppDispatchers
import com.pierre.photo.presentation.screen.PhotoViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

class PhotoViewModelTest {
    // mocks
    private val myRepository = Mockito.mock(PhotographersRepository::class.java)
    private val favoriteRepository = Mockito.mock(FavoriteRepository::class.java)

    // tested
    private val myViewModel = PhotoViewModel(
        myRepository,
        favoriteRepository,
        AppDispatchers.UNCONFINED,
        0
    )

    @Test
    fun can_fetch_all_favorites() = runBlocking {
        // Given
        val favorites = mutableListOf(
            FavoriteDataBase(
                1L, "1", "1", "1", "1", "1",
                "1", "1"
            ),
        )

        val photographers = favorites.map { it.convertToFavoritePhotographerUi() }
        // When
        Mockito.`when`(favoriteRepository.getAllFavorites()).thenReturn(favorites)
        myViewModel.fetchFavorite()

        // Then
        Assert.assertEquals(photographers, myViewModel.uiState.value.favorites)
        Assert.assertEquals(photographers, myViewModel.uiState.value.photographers)
    }

    @Test
    fun can_search_by_query() = runBlocking {
        // Given
        val input = "testInput"
        val photosResult = Photos(
            mutableListOf(
                PhotographerWeb(
                    1L, "1", "1", "1", "1", "1",
                    "1", "1", Src(), true
                ),
            )
        )

//        Mockito.`when`(myRepository!!.getPhotographersByName(input)).thenReturn(photosResult)
//
//
//        myViewModel.searchPhotoBy(input)

        // Then
        Assert.assertEquals(1, myViewModel.uiState.value.photographers.size)
    }

    @Test
    fun can_open_full_screen() = runBlocking {
        // Given
        val photographer = PhotographerWeb(
            1L, "1", "1", "1", "1", "1",
            "1", "1", Src(), true
        )

        // When
        myViewModel.openDetailScreen(photographer)

        // Then
        Assert.assertEquals(photographer, myViewModel.uiState.value.favoriteDetail)
    }
}
