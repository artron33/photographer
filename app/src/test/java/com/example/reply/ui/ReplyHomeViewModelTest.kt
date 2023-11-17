package com.example.reply.ui

import com.example.reply.data.converter.convertToPhotographer
import com.example.reply.data.favoris.FavoriteRepository
import com.example.reply.data.library.room.Favorite
import com.example.reply.data.photographer.Photo
import com.example.reply.data.photographer.Photographer
import com.example.reply.data.photographer.PhotographersRepository
import com.example.reply.data.photographer.Src
import com.example.reply.ui.library.AppDispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

class ReplyHomeViewModelTest {
    private val myRepository = Mockito.mock(PhotographersRepository::class.java)
    private val favoriteRepository = Mockito.mock(FavoriteRepository::class.java)

    // tested
    private val myViewModel = ReplyHomeViewModel(
        myRepository,
        favoriteRepository,
        AppDispatchers.UNCONFINED,
        0
    )

    @Test
    fun can_fetch_all_favorites() = runBlocking {
        // Given
        val favorites = mutableListOf(
            Favorite(
                1L, "1", "1", "1", "1", "1",
                "1", "1"
            ),
        )

        val photographers = favorites.map { it.convertToPhotographer() }
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
        val photoResult = Photo(
            mutableListOf(
                Photographer(
                    1L, "1", "1", "1", "1", "1",
                    "1", "1", Src(), true
                ),
            )
        )

        Mockito.`when`(myRepository!!.getPhotographersByName(input)).thenReturn(photoResult)


        myViewModel.searchPhotoBy(input)

        // Then
        Assert.assertEquals(1, myViewModel.uiState.value.photographers.size)
    }

    @Test
    fun can_open_full_screen() = runBlocking {
        // Given
        val photographer = Photographer(
            1L, "1", "1", "1", "1", "1",
            "1", "1", Src(), true
        )

        // When
        myViewModel.openDetailScreen(photographer)

        // Then
        Assert.assertEquals(photographer, myViewModel.uiState.value.bigScreen)
    }
}
