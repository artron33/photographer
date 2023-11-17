package com.example.reply.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.reply.data.photographer.Photographer
import com.example.reply.ui.components.appbar.EmailDetailAppBar
import com.example.reply.ui.components.PhotographerFullScreen

@Composable
fun PhotographerDetail(
    photographer: Photographer,
    modifier: Modifier = Modifier.fillMaxSize(),
    controlFavorite: (Photographer) -> Boolean,
    openDetailScreen: (Photographer?) -> Unit,
) {
    Column(
        modifier = modifier.padding(top = 16.dp)
    ) {
        EmailDetailAppBar(photographer) {
            openDetailScreen(null)
        }
        PhotographerFullScreen(
            photographer = photographer,
            controlFavorite = controlFavorite,
        )
    }
}
