package com.example.reply.ui.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.unit.dp
import com.example.reply.R
import com.example.reply.data.photographer.Photographer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReplyDockedSearchBar(
    photographers: List<Photographer>,
    onSearchItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    isSearching: Boolean
) {
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    var isFavorite by remember { mutableStateOf(true) }
//    var isArrowBack by remember { mutableStateOf(photographers.isNotEmpty() && query.isNotEmpty()) }
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
        placeholder = { Text(text = stringResource(id = R.string.search_emails)) },
        leadingIcon = {
            Log.e("yallah", "azeaze photographers: ${photographers.size}")
            Log.e("yallah", "azeaze photographers: ${photographers.isNullOrEmpty()}")
//            if (active || isFavorite.not()) {
//            if (photographers.isNullOrEmpty().not()) {
//            if (isArrowBack) {
            if (isSearching) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.back_button),
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .clickable {
                            active = false
                            query = ""
                            isFavorite = true
                            onSearchItemSelected.invoke("")
                        },
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.search),
                    modifier = Modifier.padding(start = 16.dp),
                )
            }
        },
    ) {
        if (photographers.isNotEmpty() && query.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                isArrowBack = true
                items(items = photographers, key = { it.id ?: 0 }) { email ->
                    ListItem(
                        headlineContent = { Text(text = email.photographer, maxLines = 1) },
                        supportingContent = {
                            Text(
                                text = email.photographer_url,
                                maxLines = 1
                            )
                        },
                        leadingContent = {
                            ReplyProfileImage(
                                drawableResource = email.src.medium,
                                description = stringResource(id = R.string.profile),
                                modifier = Modifier
                                    .size(48.dp),
                                isRound = true
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
                text = stringResource(id = R.string.no_item_found),
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailDetailAppBar(
    photographer: Photographer,
    isFullScreen: Boolean,
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit
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
                    text = photographer.photographer,
//                    text =  "Search",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
//                Text(
//                    modifier = Modifier.padding(top = 4.dp),
////                    text = "${stringResource(id = R.string.messages)}",
//                    text = photographer.,
//                    style = MaterialTheme.typography.labelMedium,
//                    color = MaterialTheme.colorScheme.outline
//                )
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
                    contentDescription = stringResource(id = R.string.back_button),
                    modifier = Modifier.size(14.dp)
                )
            }
        },
    )
}
