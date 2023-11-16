package com.example.reply.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.window.layout.DisplayFeature
import com.example.reply.data.library.room.Favorite
import com.example.reply.data.photographer.Photographer


@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun ReplyApp(
    displayFeatures: List<DisplayFeature>,
    replyHomeUIState: ReplyHomeUIState,
    openDetailScreen: (Photographer?) -> Unit = {},
    navigateToDetail: (String) -> Unit = { _ -> },
    controlFavorite: (Favorite)-> Unit = { _ -> },
) {
    ReplyAppContent(
        displayFeatures = displayFeatures,
        replyHomeUIState = replyHomeUIState,
        openDetailScreen = openDetailScreen,
        navigateToDetail = navigateToDetail,
        controlFavorite = controlFavorite
    )
}

@Composable
fun ReplyAppContent(
    modifier: Modifier = Modifier,
    displayFeatures: List<DisplayFeature>,
    replyHomeUIState: ReplyHomeUIState,
    openDetailScreen: (Photographer?) -> Unit,
    navigateToDetail: (String) -> Unit,
    controlFavorite: (Favorite) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.inverseOnSurface)
    ) {
        ReplyInboxScreen(
            displayFeatures = displayFeatures,
            replyHomeUIState = replyHomeUIState,
            openDetailScreen = openDetailScreen,
            navigateToDetail = navigateToDetail,
            controlFavorite = controlFavorite
        )
    }
}

