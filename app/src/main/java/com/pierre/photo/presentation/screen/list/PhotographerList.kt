package com.pierre.photo.presentation.screen.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.pierre.photo.data.localmock.LocalPhotographersDataProvider
import com.pierre.photo.presentation.components.PhotographerListItem
import com.pierre.photo.presentation.components.appbar.FavoriteAppBar
import com.pierre.photo.presentation.components.appbar.PhotoDockedSearchBar
import com.pierre.photo.presentation.navigation.ReplyRoute
import com.pierre.photo.presentation.screen.PhotographerUi
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun PhotographerList(
    photographers: MutableStateFlow<PagingData<PhotographerUi>>?,
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
                listDataPlaceholder = listDataPlaceholder,
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

@Composable
private fun lazyContentList(
    modifier: Modifier,
    emailLazyListState: LazyListState,
    photographers: MutableStateFlow<PagingData<PhotographerUi>>?,
    listDataPlaceholder: List<PhotographerUi>? = null,
    isSearchingRoute: Boolean,
    controlFavorite: (PhotographerUi) -> Unit,
    openDetailScreen: (PhotographerUi?, Boolean) -> Unit
) {
    val lazyItems = photographers?.collectAsLazyPagingItems()
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 80.dp),
        state = emailLazyListState,
    ) {
        items(
            lazyItems?.itemCount ?: listDataPlaceholder?.size ?: 0,
            key = { lazyItems?.get(it)?.id ?: listDataPlaceholder?.get(it)?.id ?: 0 }
        ) { index ->
            PhotographerListItem(
                photographer = lazyItems?.get(index) ?: listDataPlaceholder?.get(index)!!,
                isSearchingRoute = isSearchingRoute,
                controlFavorite = controlFavorite,
                openDetailScreen = openDetailScreen,
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
