package com.example.reply.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.reply.data.photographer.Photographer
import com.example.reply.ui.components.appbar.ReplyDockedSearchBar
import com.example.reply.ui.components.PhotographerListItem

@Composable
fun PhotographerList(
    photographers: List<Photographer>,
    modifier: Modifier = Modifier,
    searchPhotoWith: (String) -> Unit,
    controlFavorite: (Photographer) -> Boolean,
    openDetailScreen: (Photographer?) -> Unit,
    isSearching: Boolean,
) {
    // box is needed to allow ReplyDockedSearchBar
    // to draw the dropdown above the content
    Box(modifier = modifier) {
        ReplyDockedSearchBar(
            photographers = photographers,
            isSearching = isSearching,
            onSearchItemSelected = searchPhotoWith,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 80.dp)
        ) {
            items(items = photographers,
                key = { it.id ?: 0 }) { photographer ->
                var isAdded by remember { mutableStateOf(photographer) }
                PhotographerListItem(
                    photographer = photographer,
                    isSearching = isSearching,
                    controlFavorite = controlFavorite,
                    openDetailScreen = openDetailScreen,
                    isAdded = isAdded.isFavorite
                )
            }
        }
    }
}
