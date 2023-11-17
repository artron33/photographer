package com.pierre.photo.ui

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import com.pierre.photo.data.converter.convertToPhotographer
import com.pierre.photo.data.favoris.FavoriteRepository
import com.pierre.photo.data.favoris.FavoriteRepositoryImpl
import com.pierre.photo.data.photographer.Photo
import com.pierre.photo.data.photographer.Photographer
import com.pierre.photo.data.photographer.PhotographersRepository
import com.pierre.photo.data.photographer.PhotographersRepositoryImpl
import com.pierre.photo.ui.library.AppDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

val DELAY_BETWEEN_SEARCHES_CONST =  210L

class PhotoViewModel(
    private val photographerRepository: PhotographersRepository = PhotographersRepositoryImpl(),
    private val favoriteRepository: FavoriteRepository = FavoriteRepositoryImpl(),
    private val dispatcher: CoroutineDispatcher = AppDispatchers.IO,
    private val DELAY_BETWEEN_SEARCHES: Long = DELAY_BETWEEN_SEARCHES_CONST,
) : ViewModel() {
    private var searchJob: Job? = null
    private val _uiState = MutableStateFlow(PhotoUIState(loading = true))
    val uiState: StateFlow<PhotoUIState> = _uiState

    init {
        fetchFavorite()
    }

    private fun searchPhotographerBy(searchedString: String) {
        if (searchedString.isBlank()) {
            fetchFavorite()
            return
        }
        // cancel previous request to fetch photographer by query
        searchJob?.cancel()
        searchJob = CoroutineScope(dispatcher).launch {
            // delay if user is currently typing
            delay(DELAY_BETWEEN_SEARCHES)
            val photos = photographerRepository.getPhotographersByName(searchedString)
            val photographerWithFavorite = addFavoriteForEach(photos!!)
            _uiState.value = _uiState.value.copy(
                isSearching = true,
                photographers = photographerWithFavorite.toMutableList(),
            )
        }
    }

    @VisibleForTesting
    fun addFavoriteForEach(data: Photo) =
        data.photos.map { photo ->
            val isFavorite = _uiState.value.favorites
                .firstOrNull { it.id == photo.id }
                .let { it != null }
            photo.copy(
                isFavorite = isFavorite
            )
        }

    @VisibleForTesting
    fun fetchFavorite() {
        CoroutineScope(dispatcher).launch {
            val data = favoriteRepository.getAllFavorites()
            data?.let {
                val photographer = it.map { favorite ->
                    favorite.convertToPhotographer()
                }
                _uiState.value = _uiState.value.copy(
                    isSearching = false,
                    favorites = photographer.toMutableList(),
                    photographers = photographer.toMutableList()
                )
            }
        }
    }

    fun addFavorite(photographer: Photographer): Boolean {
        val photographerFound = _uiState.value.favorites.firstOrNull { it.id == photographer.id }
        val hasPhotographer = photographerFound != null
        CoroutineScope(dispatcher).launch {
            if (hasPhotographer) {
                _uiState.value.favorites.remove(photographerFound)
                updateThisPhotographerFavorite(photographerFound, false)
                favoriteRepository.deleteThisPhotographerFromFavorites(photographer)
            } else {
                _uiState.value.favorites.add(photographer)
                updateThisPhotographerFavorite(photographer, true)
                favoriteRepository.addThisPhotographerToFavorites(photographer)
            }
        }
        return !hasPhotographer
    }

    private fun updateThisPhotographerFavorite(photographerFound: Photographer?, newIsFavorite: Boolean) {
        _uiState.value.photographers
            .firstOrNull { it.id == photographerFound!!.id }
            ?.let {
                val photographerCopy = it.copy(isFavorite = newIsFavorite)
                val index = _uiState.value.photographers.indexOf(it)
                _uiState.value.photographers.remove(it)
                _uiState.value.photographers.add(index, photographerCopy)
            }
        _uiState.value.copy(photographers = _uiState.value.photographers)
    }

    fun searchPhotoBy(photographer: String) {
        searchPhotographerBy(photographer)
    }

    fun openDetailScreen(photographer: Photographer?) {
        val recentPhotographer = _uiState.value.photographers
            .firstOrNull { it.id == photographer?.id }
        _uiState.value = _uiState
            .value.copy(
                isDetailOnlyOpen = true,
                bigScreen = recentPhotographer,
            )
    }
}

data class PhotoUIState(
    val photographers: MutableList<Photographer> = mutableListOf(),
    val isSearching: Boolean = false,
    val favorites: MutableList<Photographer> = mutableListOf(),
    val bigScreen: Photographer? = null,
    val selectedEmails: Set<Long> = emptySet(),
    val openedEmail: Photographer? = null,
    val isDetailOnlyOpen: Boolean = false,
    val loading: Boolean = false,
    val error: String? = null
)
