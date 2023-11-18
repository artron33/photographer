package com.pierre.photo.presentation.screen.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pierre.photo.data.localmock.LocalPhotographersDataProvider.photographerDetail
import com.pierre.photo.presentation.navigation.ReplyRoute
import com.pierre.photo.presentation.components.appbar.EmailDetailAppBar
import com.pierre.photo.presentation.components.PhotographerFullScreen
import com.pierre.photo.presentation.screen.PhotoUIState
import com.pierre.photo.presentation.screen.PhotographerUi
import com.pierre.photo.presentation.screen.comingsoon.EmptyComingSoon
import kotlinx.coroutines.flow.StateFlow

@Composable
fun PhotographerDetail(
    photoUIState: StateFlow<PhotoUIState>?,
    modifier: Modifier = Modifier.fillMaxSize(),
    controlFavorite: (PhotographerUi) -> Unit,
    openDetailScreen: (PhotographerUi?, Boolean) -> Unit,
    route: String,
    isSearchingRoute: Boolean,
    placeholder: PhotoUIState? = null
) {
    val uiState = photoUIState?.collectAsStateWithLifecycle()?.value ?: placeholder!!
    val photographerDetail = if (isSearchingRoute) {
        uiState.searchDetail
    } else {
        uiState.favoriteDetail
    }
    Column(
        modifier = modifier.padding(top = 16.dp)
    ) {
        EmailDetailAppBar(photographerDetail) {
            openDetailScreen(null, route == ReplyRoute.SEARCH)
        }
        PhotographerFullScreen(
            photographer =  photographerDetail,
            controlFavorite = controlFavorite,
        )
    }
}


@Preview
@Composable
fun PhotographerDetailPreview() {
    PhotographerDetail(
        photoUIState = null,
        controlFavorite = {},
        openDetailScreen = { _, _ -> },
        route = ReplyRoute.SEARCH,
        isSearchingRoute = true,
        placeholder = PhotoUIState(
            searchDetail = photographerDetail,
            favoriteDetail = photographerDetail
        )
    )
}