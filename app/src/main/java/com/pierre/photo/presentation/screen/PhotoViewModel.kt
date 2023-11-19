package com.pierre.photo.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.pierre.photo.CONSTANTE.Companion.DELAY_BETWEEN_SEARCHES_CONST
import com.pierre.photo.data.PhotographerUiRepository
import com.pierre.photo.data.PhotographerUiRepositoryImpl
import com.pierre.photo.domain.converter.convertToFavoriteDatabase
import com.pierre.photo.presentation.library.AppDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class PhotoViewModel(
    private val photographerRepository: PhotographerUiRepository = PhotographerUiRepositoryImpl(),
    private val dispatcher: CoroutineDispatcher = AppDispatchers.IO,
    private val DELAY_BETWEEN_SEARCHES: Long = DELAY_BETWEEN_SEARCHES_CONST
) : ViewModel() {

    private var searchJob: Job? = null

//    private val _uiState = MutableStateFlow(PhotoUIState())
//    val uiState: StateFlow<PhotoUIState> = _uiState

    private val _uiDetailsState = MutableStateFlow(isDetailState())
    val uiDetailState: StateFlow<isDetailState> = _uiDetailsState

    private val _photographerState: MutableStateFlow<PagingData<PhotographerUi>> = MutableStateFlow(value = PagingData.empty())
    val photographerState: StateFlow<PagingData<PhotographerUi>> = _photographerState

    private val _favoritesState: MutableStateFlow<PagingData<PhotographerUi>> = MutableStateFlow(value = PagingData.empty())
    val favoritesState: StateFlow<PagingData<PhotographerUi>> = _favoritesState

    init {
        fetchFavorite()
    }

    private fun fetchFavorite() = runBackgroundCoroutine(dispatcher) {
        val data = photographerRepository.getAllFavoritesFromDb()
        data.collect { favorites ->
            if (favorites == null) return@collect

            _favoritesState.update { PagingData.from(favorites) }
//            _favoritesState.value = PagingData.from(favorites)

            _photographerState.update {it.map { photographer ->
                    favorites.firstOrNull { f-> f.id == photographer.id }.let { favorite ->
                        photographer.copy(isFavorite = favorite != null)
                    }
                }
            }

//            favorites.firstOrNull { it.id == _uiState.value.favoriteDetail?.id }.let {
//                _uiState.value = _uiState.value.copy(
//                    favoriteDetail = _uiState.value.favoriteDetail?.copy(isFavorite = it != null)
//                )
//            }
            favorites.firstOrNull { it.id == _uiState.value.favoriteDetail?.id }.let {
                _uiState.update {state->
                    state.copy(favoriteDetail = state.favoriteDetail?.copy(isFavorite = it != null))
                }
            }
//            favorites.firstOrNull { it.id == _uiState.value.searchDetail?.id }.let {
//                _uiState.value = _uiState.value.copy(
//                    searchDetail = _uiState.value.searchDetail?.copy(isFavorite = it != null)
//                )
//            }
            favorites.firstOrNull { it.id == _uiState.value.searchDetail?.id }.let {
                _uiState.update { state ->
                    state.copy(searchDetail = state.searchDetail?.copy(isFavorite = it != null))
                }
            }

        }
    }

    fun searchPhotographerBy(searchedString: String) {
        if (searchedString.isBlank()) return

        // cancel previous request to fetch photographer by query
        searchJob?.cancel()
        searchJob = runBackgroundCoroutine(dispatcher) {
            // delay if user is currently typing
            delay(DELAY_BETWEEN_SEARCHES)
            photographerRepository.getPagingData(searchedString)
                .distinctUntilChanged()
                .cachedIn(viewModelScope).collect { paging ->
                    _photographerState.value = paging
                }

        }
    }

    fun addFavorite(photographer: PhotographerUi) {
        runBackgroundCoroutine(dispatcher) {
            photographerRepository.insertOrDeleteThisPhotographerAsFavorites(photographer.convertToFavoriteDatabase())
        }
    }

    private val _uiState = MutableStateFlow(PhotoUIState())
    val uiState: StateFlow<PhotoUIState> = _uiState

    fun openDetailScreen(photographer: PhotographerUi?, isSearchingRoute: Boolean) {
        if (isSearchingRoute) {
//            _uiState.value = _uiState.value.copy(searchDetail = photographer)
            _uiState.update { it.copy(searchDetail = photographer) }
            _uiDetailsState.update { it.copy(isSearchOpen = photographer != null) }
//            _uiDetailsState.value = _uiDetailsState.value.copy(isSearchOpen = photographer != null)
        } else {

            /**  1-> Wrong  **/
//            _uiState.value = _uiState.value.copy(favoriteDetail = photographer)
//            _uiDetailsState.value = _uiDetailsState.value.copy(isFavoriteOpen = photographer != null)
            /**  2-> Right  **/
            _uiState.update { it.copy(favoriteDetail = photographer) }
            _uiDetailsState.update { it.copy(isFavoriteOpen = photographer != null) }

        }

    }
}

data class PhotoUIState(
    val favoriteDetail: PhotographerUi? = null,
    val searchDetail: PhotographerUi? = null,
)

data class isDetailState(
    val isFavoriteOpen: Boolean = false,
    val isSearchOpen: Boolean = false
)

data class PhotographerUi(
    val id: Long,
    val url: String,
    val photographer: String,
    val photographer_url: String,
    val photographer_id: String,
    val avg_color: String,
    val original: String,
    val medium: String,
    val small: String,
    val portrait: String,
    val landscape: String,
    val isFavorite: Boolean,
    val resIdDrawableMock: Int? = null,
)

fun ViewModel.runBackgroundCoroutine(
    dispatcher: CoroutineDispatcher, block: suspend () -> Unit
) = viewModelScope.launch {
    withContext(dispatcher) {
        block()
    }
}
