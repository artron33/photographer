package com.lambda.pierre

import com.example.reply.data.converter.convertToPhotographer
import com.example.reply.data.favoris.FavoriteRepository
import com.example.reply.data.library.room.Favorite
import com.example.reply.data.photographer.Photo
import com.example.reply.data.photographer.Photographer
import com.example.reply.data.photographer.PhotographersRepository
import com.example.reply.data.photographer.Src
import com.example.reply.ui.ReplyHomeViewModel
import com.example.reply.ui.library.AppDispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.*

class ExampleViewModelUnitTest {
    private val myRepository = mock(PhotographersRepository::class.java)
    private val favoriteRepository = mock(FavoriteRepository::class.java)

    // tested
    private val myViewModel = ReplyHomeViewModel(myRepository, favoriteRepository,
        AppDispatchers.UNCONFINED, 0)

    @Test
    fun display_all_favorites() = runBlocking {
        // Given
        val favorites = mutableListOf(
            Favorite(1L, "1", "1", "1", "1", "1",
                "1", "1"),
        )

        val photographers = favorites.map { it.convertToPhotographer() }
        // When
        `when`(favoriteRepository.getAllFavorites()).thenReturn(favorites)
        myViewModel.fetchFavorite()

        // Then
        assertEquals(photographers, myViewModel.uiState.value.favorites)
        assertEquals(photographers, myViewModel.uiState.value.photographers)
    }

    @Test
    fun search_by_query_add_photographer_to_the_ui() = runBlocking {
        // Given
        val input = "testInput"
        val photoResult = Photo(mutableListOf(
            Photographer(1L, "1", "1", "1", "1", "1",
                "1", "1", Src(), true),
        ))

        `when`(myRepository!!.getPhotographersByName(input)).thenReturn(photoResult)


        myViewModel.searchPhotoBy(input)

        // Then
        assertEquals(1, myViewModel.uiState.value.photographers.size)
    }

    @Test
    fun open_full_screen() = runBlocking {
        // Given
        val photographer = Photographer(1L, "1", "1", "1", "1", "1",
                "1", "1", Src(), true)

        // When
        myViewModel.openDetailScreen(photographer)

        // Then
        assertEquals(photographer, myViewModel.uiState.value.bigScreen)
    }
}