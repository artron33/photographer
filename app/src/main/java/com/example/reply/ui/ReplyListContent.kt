package com.example.reply.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.reply.data.library.room.Favorite
import com.example.reply.data.photographer.Photographer
import com.example.reply.ui.components.EmailDetailAppBar
import com.example.reply.ui.components.PhotographerFullScreen
import com.example.reply.ui.components.ReplyDockedSearchBar
import com.example.reply.ui.components.ReplyEmailListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReplyInboxScreen(
    replyHomeUIState: ReplyHomeUIState,
    openDetailScreen: (Photographer?) -> Unit,
    navigateToDetail: (String) -> Unit,
    controlFavorite: (Photographer) -> Boolean
) {
    ReplySinglePaneContent(
        replyHomeUIState = replyHomeUIState,
        modifier = Modifier.fillMaxSize(),
        openDetailScreen = openDetailScreen,
        navigateToDetail = navigateToDetail,
        controlFavorite = controlFavorite
    )
}

@Composable
fun ReplySinglePaneContent(
    replyHomeUIState: ReplyHomeUIState,
    modifier: Modifier = Modifier,
    openDetailScreen: (Photographer?) -> Unit,
    navigateToDetail: (String) -> Unit,
    controlFavorite: (Photographer) -> Boolean
) {
    if (replyHomeUIState.bigScreen != null) {
        BackHandler {
            openDetailScreen(null)
        }
        ReplyEmailDetail(
            photographer = replyHomeUIState.bigScreen,
            controlFavorite = controlFavorite,
            openDetailScreen = openDetailScreen,
        )
    } else {
        ReplyEmailList(
            photographers = replyHomeUIState.photographers,
            isSearching = replyHomeUIState.isSearching,
            modifier = modifier,
            navigateToDetail = navigateToDetail,
            controlFavorite = controlFavorite,
            openDetailScreen = openDetailScreen,
        )
    }
}

@Composable
fun ReplyEmailList(
    photographers: List<Photographer>,
    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit,
    controlFavorite: (Photographer) -> Boolean,
    openDetailScreen: (Photographer?) -> Unit,
    isSearching: Boolean,
) {
    Box(modifier = modifier) {
        ReplyDockedSearchBar(
            photographers = photographers,
            isSearching = isSearching,
            onSearchItemSelected = { searchedString ->
                navigateToDetail(searchedString)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 80.dp)//, state = emailLazyListState
        ) {
            items(items = photographers,
                key = { it.id ?: 0 }) { photographer ->
                ReplyEmailListItem(
                    photographer = photographer,
                    navigateToDetail = { emailId ->
                        navigateToDetail(emailId)
                    },
                    controlFavorite = controlFavorite,
                    openDetailScreen = openDetailScreen,
                )
            }
        }
    }
}

@Composable
fun ReplyEmailDetail(
    photographer: Photographer,
    isFullScreen: Boolean = true,
    modifier: Modifier = Modifier.fillMaxSize(),
    controlFavorite: (Photographer) -> Boolean,
    openDetailScreen: (Photographer?) -> Unit,
) {
    // todo: replace with a list
    Column(
        modifier = modifier
            .padding(top = 16.dp)
    ) {
        EmailDetailAppBar(photographer, isFullScreen) {
            openDetailScreen(null)
        }
        PhotographerFullScreen(
            photographer = photographer,
            controlFavorite = controlFavorite,
        )
    }
}
