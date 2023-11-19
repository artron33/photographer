package com.pierre.photo.presentation.screen.list

import android.util.Log
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.pierre.photo.data.localmock.LocalPhotographersDataProvider
import com.pierre.photo.presentation.components.PhotographerListItem
import com.pierre.photo.presentation.components.appbar.FavoriteAppBar
import com.pierre.photo.presentation.components.appbar.PhotoDockedSearchBar
import com.pierre.photo.presentation.navigation.ReplyRoute
import com.pierre.photo.presentation.screen.PhotographerUi
import kotlinx.coroutines.flow.StateFlow

@Composable
fun PhotographerList(
    photographers: StateFlow<PagingData<PhotographerUi>>?,
    modifier: Modifier = Modifier,
    searchPhotoWith: (String) -> Unit,
    controlFavorite: (PhotographerUi) -> Unit,
    openDetailScreen: (PhotographerUi?, Boolean) -> Unit,
    route: String,
    emailLazyListState: LazyListState,
    listDataPlaceholder: List<PhotographerUi>?,
) {
    val isSearchingRoute = route == ReplyRoute.SEARCH
    // box is needed to allow PhotoDockedSearchBar
    // to draw the dropdown above the content
    Box(modifier = modifier) {
        if (isSearchingRoute)
            PhotoDockedSearchBar(
                photographers = photographers,
                onSearchItemSelected = searchPhotoWith,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        else FavoriteAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        lazyContentList(
            modifier,
            emailLazyListState,
            photographers,
            listDataPlaceholder,
            isSearchingRoute,
            controlFavorite,
            openDetailScreen
        )
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun lazyContentList(
    modifier: Modifier,
    emailLazyListState: LazyListState,
    photographers: StateFlow<PagingData<PhotographerUi>>?,
    listDataPlaceholder: List<PhotographerUi>? = null,
    isSearchingRoute: Boolean,
    controlFavorite: (PhotographerUi) -> Unit,
    openDetailScreen: (PhotographerUi?, Boolean) -> Unit
) {
    val lazyItems = photographers?.collectAsLazyPagingItems()
//    LaunchedEffect(lazyItems?.get(2)?.id) {
//        emailLazyListState.animateScrollToItem(0)
//    }
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 80.dp),
        state = emailLazyListState,

    ) {
        items(
            count = lazyItems?.itemCount ?: listDataPlaceholder?.size ?: 0,
            key = { index: Int ->
                val temps = lazyItems?.get(index)?.id ?: (100..10000).random()
                Log.e("yallahhh", "id: $temps")
                temps
            },
            contentType = {"123"},
        ) { index: Int ->

            PhotographerListItem(
                photographer = lazyItems?.get(index) ?: listDataPlaceholder?.get(index)!!,
                isSearchingRoute = isSearchingRoute,
                controlFavorite = controlFavorite,
                openDetailScreen = openDetailScreen,
                modifier = Modifier
                    .animateItemPlacement()
                    .padding(horizontal = 16.dp, vertical = 10.dp)
                    .height(300.dp)
                    .fillMaxWidth()
            )

        }
    }
}

@Preview
@Composable
fun lazyContentListPreview() {
    PhotographerList(
        modifier = Modifier,
        emailLazyListState = LazyListState(),
        photographers = null,
        route = ReplyRoute.SEARCH,
        searchPhotoWith = {},
        controlFavorite = {},
        openDetailScreen = { _, _ -> },
        listDataPlaceholder = LocalPhotographersDataProvider.photographers
    )
}
