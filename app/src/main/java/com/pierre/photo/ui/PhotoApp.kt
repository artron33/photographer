package com.pierre.photo.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pierre.photo.data.photographer.Photographer
import com.pierre.photo.ui.screen.PhotographerDetail
import com.pierre.photo.ui.screen.PhotographerList


@Composable
fun PhotoApp(
    photoUIState: PhotoUIState,
    openDetailScreen: (Photographer?) -> Unit = {},
    searchPhotoWith: (String) -> Unit = { _ -> },
    controlFavorite: (Photographer)-> Boolean = { _ -> false},
) {
    if (photoUIState.bigScreen != null) {
        BackHandler {
            openDetailScreen(null)
        }
        PhotographerDetail(
            photographer = photoUIState.bigScreen,
            controlFavorite = controlFavorite,
            openDetailScreen = openDetailScreen,
        )
    } else {
        PhotographerList(
            photographers = photoUIState.photographers,
            isSearching = photoUIState.isSearching,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.inverseOnSurface),
            searchPhotoWith = searchPhotoWith,
            controlFavorite = controlFavorite,
            openDetailScreen = openDetailScreen,
        )
    }
}


