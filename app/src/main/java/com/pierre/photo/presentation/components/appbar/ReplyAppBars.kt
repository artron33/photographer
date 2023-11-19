package com.pierre.photo.presentation.components.appbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.pierre.photo.R
import com.pierre.photo.data.localmock.LocalPhotographersDataProvider
import com.pierre.photo.presentation.components.PhotographerImage
import com.pierre.photo.presentation.navigation.ReplyRoute
import com.pierre.photo.presentation.screen.PhotoUIState
import com.pierre.photo.presentation.screen.PhotographerUi
import com.pierre.photo.presentation.screen.ReplySinglePaneContent
import com.pierre.photo.presentation.screen.isDetailState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


@Preview
@Composable
fun EmailDetailAppBarPreview() {
    EmailDetailAppBar(
        photographer = LocalPhotographersDataProvider.photographerDetail,
        onBackPressed = {},
    )

}

@Preview
@Composable
fun FavoriteAppBarPreview() {
    FavoriteAppBar()
}


@Preview
@Composable
fun PhotoDockedSearchBarPreview() {
    PhotoDockedSearchBar(
        photographers = null,
        onSearchItemSelected = {},
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoDockedSearchBar(
    photographers: StateFlow<PagingData<PhotographerUi>>? = null,
    onSearchItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    var isFavorite by remember { mutableStateOf(true) }
    var isArrowBack by remember { mutableStateOf(active || isFavorite.not()) }

    LaunchedEffect(query) {
        if (query.isNotEmpty()) {
            onSearchItemSelected.invoke(query)
            isFavorite = false
        }
    }

    DockedSearchBar(
        modifier = modifier,
        query = query,
        onQueryChange = {
            query = it
        },
        onSearch = {
            active = false
            onSearchItemSelected.invoke(query)
            query = ""
        },
        active = active,
        onActiveChange = {
            active = it
        },
        placeholder = {
            Text(
                text = if (query.isBlank()) stringResource(R.string.search_here)
                else stringResource(R.string.search_query, query)
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(R.string.search_description),
                modifier = Modifier.padding(start = 16.dp),
            )
        },
    ) {
        if (query.isNotEmpty()) {
            val lazyItems = photographers?.collectAsLazyPagingItems()
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                if (lazyItems == null)  return@LazyColumn
                isArrowBack = true
                items(lazyItems.itemCount, key = { lazyItems[it]?.id ?: 0 }) { index ->
                    ListItem(
                        headlineContent = {
                            Text(
                                text = lazyItems[index]!!.photographer,
                                maxLines = 1
                            )
                        },
                        supportingContent = {
                            Text(
                                text = lazyItems[index]!!.photographer_url,
                                maxLines = 1
                            )
                        },
                        leadingContent = {
                            PhotographerImage(
                                drawableResource = lazyItems[index]!!.medium,
                                description = stringResource(R.string.image_description),
                                modifier = Modifier
                                    .size(48.dp),
                                isRound = true,
                                mockData = lazyItems[index]!!.resIdDrawableMock
                            )
                        },
                        modifier = Modifier.clickable {
                            active = false
                            onSearchItemSelected.invoke(query)
                            query = ""
                        }
                    )
                }
            }
        } else if (query.isNotEmpty()) {
            Text(
                text = stringResource(R.string.no_item_found),
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailDetailAppBar(
    photographer: PhotographerUi?,
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit
) {
    if (photographer == null) return
    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.inverseOnSurface
        ),
        title = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = photographer.photographer,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        navigationIcon = {
            FilledIconButton(
                onClick = onBackPressed,
                modifier = Modifier.padding(8.dp),
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back_button),
                    modifier = Modifier.size(14.dp)
                )
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteAppBar(
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.inverseOnSurface
        ),
        title = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    modifier = modifier.padding(end = 8.dp),
                    text = stringResource(R.string.tab_favorite), // todo toString ress
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        navigationIcon = {
            Image(
                imageVector = Icons.Default.Star,
                contentDescription = stringResource(R.string.back_button),
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(24.dp)
            )
        },
    )
}
