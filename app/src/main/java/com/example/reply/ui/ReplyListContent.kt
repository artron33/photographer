package com.example.reply.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.window.layout.DisplayFeature
import com.example.reply.data.library.room.Favorite
import com.example.reply.data.photographer.Photographer
import com.example.reply.ui.components.EmailDetailAppBar
import com.example.reply.ui.components.ReplyDockedSearchBar
import com.example.reply.ui.components.ReplyEmailListItem
import com.example.reply.ui.components.ReplyEmailThreadItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReplyInboxScreen(
    replyHomeUIState: ReplyHomeUIState,
    displayFeatures: List<DisplayFeature>,
    openDetailScreen: (Photographer?) -> Unit,
    navigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier,
    controlFavorite: (Favorite) -> Unit
) {
    val emailLazyListState = rememberLazyListState()
    // TODO: Show top app bar over full width of app when in multi-select mode
    ReplySinglePaneContent(
        replyHomeUIState = replyHomeUIState,
        emailLazyListState = emailLazyListState,
        modifier = Modifier.fillMaxSize(),
        openDetailScreen = openDetailScreen,
        navigateToDetail = navigateToDetail,
        controlFavorite = controlFavorite
    )
}

@Composable
fun ReplySinglePaneContent(
    replyHomeUIState: ReplyHomeUIState,
    emailLazyListState: LazyListState,
    modifier: Modifier = Modifier,
    openDetailScreen: (Photographer?) -> Unit,
    navigateToDetail: (String) -> Unit,
    controlFavorite: (Favorite) -> Unit
) {
    if (replyHomeUIState.photographers.isNullOrEmpty().not()) {
        BackHandler {
            openDetailScreen(null)
        }
        ReplyEmailDetail(email = replyHomeUIState.photographers) {
            openDetailScreen(null)
        }
    } else {
        ReplyEmailList(
            photographers = replyHomeUIState.photographers,
            emailLazyListState = emailLazyListState,
            modifier = modifier,
            navigateToDetail = navigateToDetail,
            controlFavorite = controlFavorite
        )
    }
}

@Composable
fun ReplyEmailList(
    photographers: List<Photographer>,
    emailLazyListState: LazyListState,
    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit,
    controlFavorite: (Favorite) -> Unit
) {
    Box(modifier = modifier) {
        ReplyDockedSearchBar(
            photographers = photographers, onSearchItemSelected = { searchedString ->
                navigateToDetail(searchedString)
            },

            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        //todo change this to a list
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 80.dp), state = emailLazyListState
        ) {
            items(items = photographers, key = { it.id ?: 0 }) { email ->
                ReplyEmailListItem(
                    photographer = email,
                    navigateToDetail = { emailId ->
                        navigateToDetail(emailId)
                    },
                    controlFavorite = controlFavorite,
                )
            }
        }
    }
}

@Composable
fun ReplyEmailDetail(
    email: List<Photographer>,
    isFullScreen: Boolean = true,
    modifier: Modifier = Modifier.fillMaxSize(),
    onBackPressed: () -> Unit = {}
) {
    // todo: replace with a list
    LazyColumn(
        modifier = modifier
            .background(MaterialTheme.colorScheme.inverseOnSurface)
            .padding(top = 16.dp)
    ) {
        item {
            EmailDetailAppBar(email, isFullScreen) {
                onBackPressed()
            }
        }
        items(items = email, key = { it.id ?: 0L }) { email ->
            ReplyEmailThreadItem(photographer = email)
        }
    }
}
