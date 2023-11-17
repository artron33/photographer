package com.example.reply.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.reply.data.photographer.Photographer
import com.example.reply.ui.screen.PhotographerDetail
import com.example.reply.ui.screen.PhotographerList


@Composable
fun ReplyApp(
    replyHomeUIState: ReplyHomeUIState,
    openDetailScreen: (Photographer?) -> Unit = {},
    searchPhotoWith: (String) -> Unit = { _ -> },
    controlFavorite: (Photographer)-> Boolean = { _ -> false},
) {
    if (replyHomeUIState.bigScreen != null) {
        BackHandler {
            openDetailScreen(null)
        }
        PhotographerDetail(
            photographer = replyHomeUIState.bigScreen,
            controlFavorite = controlFavorite,
            openDetailScreen = openDetailScreen,
        )
    } else {
        PhotographerList(
            photographers = replyHomeUIState.photographers,
            isSearching = replyHomeUIState.isSearching,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.inverseOnSurface),
            searchPhotoWith = searchPhotoWith,
            controlFavorite = controlFavorite,
            openDetailScreen = openDetailScreen,
        )
    }
}


