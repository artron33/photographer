package com.example.reply.ui

import androidx.lifecycle.ViewModel
import com.example.reply.data.favoris.FavoriteRepository
import com.example.reply.data.favoris.FavoriteRepositoryImpl
import com.example.reply.data.library.room.Favorite
import com.example.reply.data.photographer.Photographer
import com.example.reply.data.photographer.PhotographersRepository
import com.example.reply.data.photographer.PhotographersRepositoryImpl
import com.example.reply.data.photographer.Src
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ReplyHomeViewModel(
    private val photographerRepository: PhotographersRepository = PhotographersRepositoryImpl(),
    private val favoriteRepository: FavoriteRepository = FavoriteRepositoryImpl()
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReplyHomeUIState(loading = true))
    val uiState: StateFlow<ReplyHomeUIState> = _uiState

    init {
        observeFavorite()
    }

    private fun observeEmails(searchedString: String) {
        if (searchedString.isBlank()) {
            observeFavorite()
            return
        }
        CoroutineScope(Dispatchers.IO).launch {
            val data = photographerRepository.getPhotographersByName(searchedString)
            val pictures = data.photos.map { photo ->
                val isFavorite = _uiState.value.favorites
                    .firstOrNull { it.photographer_id == photo.photographer_id }
                    .let { it != null }
                photo.copy(
                   isFavorite = isFavorite
                )
            }
            _uiState.value = _uiState.value.copy(
                isSearching = true,
                photographers = pictures,
            )
        }
    }

    private fun observeFavorite() {
        CoroutineScope(Dispatchers.IO).launch {

            val data = favoriteRepository.getAllFavorites()
            data.first().let {
                val photographer = it.map { favorite ->
                    Photographer(
                        id = favorite.id,
                        width = favorite.width,
                        height = favorite.height,
                        url = favorite.url,
                        photographer = favorite.photographer,
                        photographer_url = favorite.photographer_url,
                        photographer_id = favorite.photographer_id,
                        avg_color = favorite.avg_color,
                        isFavorite = true,
                        src = Src(
                            original = favorite.original,
                            medium = favorite.medium,
                            small = favorite.small,
                            landscape = favorite.landscape,
                            portrait = favorite.portrait,
                        )
                    )
                }
                _uiState.value = _uiState.value.copy(
                    isSearching = false,
                    favorites = photographer.toMutableList(),
                    photographers = photographer
                )
            }
        }
    }

    fun addFavorite(photographer: Photographer): Boolean {
        val foundPhotographer = _uiState.value.favorites.firstOrNull { it.photographer_id == photographer.photographer_id }
        val hasPhotographer = foundPhotographer != null
        CoroutineScope(Dispatchers.IO).launch {
            if (hasPhotographer) {
                _uiState.value.favorites.remove(foundPhotographer)
                favoriteRepository.deleteThisFavorites(
                    Favorite(
                        id = photographer.id,
                        width = photographer.width,
                        height = photographer.height,
                        url = photographer.url,
                        photographer = photographer.photographer,
                        photographer_url = photographer.photographer_url,
                        photographer_id = photographer.photographer_id,
                        avg_color = photographer.avg_color,
                        original = photographer.src.original,
                        medium = photographer.src.medium,
                        small = photographer.src.small,
                        landscape = photographer.src.landscape,
                        portrait = photographer.src.portrait,
                    )
                )
            } else {
                _uiState.value.favorites.add(photographer)
                favoriteRepository.addThisFavorites(
                    Favorite(
                        id = photographer.id,
                        width = photographer.width,
                        height = photographer.height,
                        url = photographer.url,
                        photographer = photographer.photographer,
                        photographer_url = photographer.photographer_url,
                        photographer_id = photographer.photographer_id,
                        avg_color = photographer.avg_color,
                        original = photographer.src.original,
                        medium = photographer.src.medium,
                        small = photographer.src.small,
                        landscape = photographer.src.landscape,
                        portrait = photographer.src.portrait,
                    )
                )
            }
        }
        return !hasPhotographer
    }


    ////////////////////
    ////////////////////
    ////////////////////
    fun setOpenedEmail(photographer: String) {
        observeEmails(photographer)
    }
    fun openDetailScreen(photographer: Photographer?) {
        _uiState.value = _uiState
            .value.copy(
                isDetailOnlyOpen = true,
                bigScreen = photographer,
            )
    }
    ////////////////////
    ////////////////////
    ////////////////////
}

data class ReplyHomeUIState(
    val photographers: List<Photographer> = emptyList(),
    val isSearching: Boolean = false,
    val favorites: MutableList<Photographer> = mutableListOf(),
    val bigScreen: Photographer? = null,
    val selectedEmails: Set<Long> = emptySet(),
    val openedEmail: Photographer? = null,
    val isDetailOnlyOpen: Boolean = false,
    val loading: Boolean = false,
    val error: String? = null
)
