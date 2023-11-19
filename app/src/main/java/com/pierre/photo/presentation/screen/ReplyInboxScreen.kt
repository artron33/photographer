package com.pierre.photo.presentation.screen

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.window.layout.DisplayFeature
import com.pierre.photo.presentation.navigation.ReplyRoute
import com.pierre.photo.presentation.utils.ReplyContentType
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.google.accompanist.adaptive.TwoPane
import com.pierre.photo.data.localmock.LocalPhotographersDataProvider
import com.pierre.photo.presentation.screen.detail.PhotographerDetail
import com.pierre.photo.presentation.screen.list.PhotographerList
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReplyInboxScreen(
    contentType: ReplyContentType,
    photoUIState: StateFlow<PhotoUIState>?,
    displayFeatures: List<DisplayFeature>,
    closeDetailScreen: () -> Unit,
    modifier: Modifier = Modifier,
    route: String,
    searchPhotoWith: (String) -> Unit,
    controlFavorite: (PhotographerUi) -> Unit,
    openDetailScreen: (PhotographerUi?, Boolean) -> Unit,
    photographerPagingItems: StateFlow<PagingData<PhotographerUi>>?,
    favoritesPagingItems: StateFlow<PagingData<PhotographerUi>>?,
    uiDetailState: StateFlow<isDetailState>?,
    photoUiStatePlaceholder: PhotoUIState? = null,
    isDetailPlaceholder: isDetailState? = null,
    listDataPlaceholder: List<PhotographerUi>? = null
) {
    /**
     * When moving from LIST_AND_DETAIL page to LIST page clear the selection and user should see LIST screen.
     */
    LaunchedEffect(key1 = contentType) {
        if (contentType == ReplyContentType.SINGLE_PANE) {
            closeDetailScreen()
        }
    }

    val emailLazyListState = rememberLazyListState()

    // TODO: Show top app bar over full width of app when in multi-select mode
    if (contentType == ReplyContentType.DUAL_PANE) {
        TwoPane(
            first = {
                val photographerMain = if (route == ReplyRoute.SEARCH) {
                    photographerPagingItems
                } else {
                    favoritesPagingItems
                }
                PhotographerList(
                    photographers = photographerMain,
                    modifier = modifier,
                    searchPhotoWith = searchPhotoWith,
                    controlFavorite = controlFavorite,
                    openDetailScreen = openDetailScreen,
                    route = route,
                    emailLazyListState = emailLazyListState,
                    listDataPlaceholder = listDataPlaceholder,
                )
            },
            second = {
                PhotographerDetail(
                    photoUIState = photoUIState,
                    modifier = modifier,
                    controlFavorite = controlFavorite,
                    openDetailScreen = openDetailScreen,
                    route = route
                )
            },
            strategy = HorizontalTwoPaneStrategy(splitFraction = 0.5f, gapWidth = 16.dp),
            displayFeatures = displayFeatures
        )
    } else {
        Box(modifier = modifier.fillMaxSize()) {
            ReplySinglePaneContent(
                photoUIState = photoUIState,
                uiDetailState = uiDetailState,
                modifier = Modifier.fillMaxSize(),
                closeDetailScreen = closeDetailScreen,
                searchPhotoWith = searchPhotoWith,
                controlFavorite = controlFavorite,
                openDetailScreen = openDetailScreen,
                route = route,
                emailLazyListState = emailLazyListState,
                photographerPagingItems = photographerPagingItems,
                favoritesPagingItems = favoritesPagingItems,
                photoUiStatePlaceholder = photoUiStatePlaceholder,
                isDetailPlaceholder = isDetailPlaceholder,
                listDataPlaceholder = listDataPlaceholder,
            )
        }
    }
}

@Composable
fun ReplySinglePaneContent(
    photoUIState: StateFlow<PhotoUIState>?,
    uiDetailState: StateFlow<isDetailState>?,
    modifier: Modifier = Modifier,
    closeDetailScreen: () -> Unit,
    emailLazyListState: LazyListState,
    searchPhotoWith: (String) -> Unit,
    controlFavorite: (PhotographerUi) -> Unit,
    openDetailScreen: (PhotographerUi?, Boolean) -> Unit,
    route: String,
    photographerPagingItems: StateFlow<PagingData<PhotographerUi>>?,
    favoritesPagingItems: StateFlow<PagingData<PhotographerUi>>?,
    photoUiStatePlaceholder: PhotoUIState? = null,
    isDetailPlaceholder: isDetailState? = null,
    listDataPlaceholder: List<PhotographerUi>? = null,
) {
    val isSearchingRoute = route == ReplyRoute.SEARCH
    val photographerMain = if (isSearchingRoute) {
        photographerPagingItems
    } else {
        favoritesPagingItems
    }
    val photographerDetail = uiDetailState?.let  { details: StateFlow<isDetailState> ->
        val temp by details.collectAsStateWithLifecycle()
        if (isSearchingRoute) {
            temp.isSearchOpen
        } else {
            temp.isFavoriteOpen
        }
    } ?: isDetailPlaceholder?.isSearchOpen ?: false

    if (photographerDetail) {
        BackHandler {
            closeDetailScreen()
        }
        PhotographerDetail(
            photoUIState = photoUIState,
            modifier = modifier,
            controlFavorite = controlFavorite,
            openDetailScreen = openDetailScreen,
            route = route,
            placeholder = photoUiStatePlaceholder,
        )
////        {
////            closeDetailScreen()
////        }
    } else {
        PhotographerList(
            photographers = photographerMain,
            modifier = modifier,
            searchPhotoWith = searchPhotoWith,
            controlFavorite = controlFavorite,
            openDetailScreen = openDetailScreen,
            route = route,
            emailLazyListState = emailLazyListState,
            listDataPlaceholder = listDataPlaceholder
        )
    }
}

@Preview
@Composable
fun ReplyInboxScreen2Preview() {
    ReplyInboxScreen(
        route = ReplyRoute.SEARCH,
        photoUIState = null,
        controlFavorite = {},
        openDetailScreen = { _, _ -> },
//        contentType = ReplyContentType.SINGLE_PANE,
        uiDetailState = null,
//        displayFeatures = emptyList(),
        closeDetailScreen = {},
        searchPhotoWith = {},
        photographerPagingItems = null,
        favoritesPagingItems = null,
        photoUiStatePlaceholder = PhotoUIState(
            searchDetail = LocalPhotographersDataProvider.photographerDetail,
            favoriteDetail = LocalPhotographersDataProvider.photographerDetail
        ),
        isDetailPlaceholder= isDetailState(
            isSearchOpen = false,
            isFavoriteOpen = false
        ),
        listDataPlaceholder = LocalPhotographersDataProvider.photographers,
        contentType = ReplyContentType.SINGLE_PANE,
        displayFeatures = emptyList(),
    )

}

@Preview
@Composable
fun ReplySinglePanePreview() {
    ReplySinglePaneContent(
        route = ReplyRoute.SEARCH,
        photoUIState = null,
        controlFavorite = {},
        openDetailScreen = { _, _ -> },
        uiDetailState = null,
        closeDetailScreen = {},
        searchPhotoWith = {},
        photographerPagingItems = null,
        favoritesPagingItems = null,
        photoUiStatePlaceholder = PhotoUIState(
            searchDetail = LocalPhotographersDataProvider.photographerDetail,
            favoriteDetail = LocalPhotographersDataProvider.photographerDetail
        ),
        isDetailPlaceholder= isDetailState(
            isSearchOpen = false,
            isFavoriteOpen = false
        ),
        listDataPlaceholder = LocalPhotographersDataProvider.photographers,
        emailLazyListState = rememberLazyListState()
    )

}
