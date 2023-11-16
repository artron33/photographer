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
    replyHomeUIState: ReplyHomeUIState,
    openDetailScreen: (Photographer?) -> Unit = {},
    navigateToDetail: (String) -> Unit = { _ -> },
    controlFavorite: (Photographer)-> Boolean = { _ -> false},
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.inverseOnSurface)
    ) {
        ReplyInboxScreen(
            replyHomeUIState = replyHomeUIState,
            openDetailScreen = openDetailScreen,
            navigateToDetail = navigateToDetail,
            controlFavorite = controlFavorite
        )
    }
}


