package com.example.reply.ui.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.reply.data.library.room.Favorite
import com.example.reply.data.photographer.Photographer

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalAnimationApi::class
)
@Composable
fun ReplyEmailListItem(
    photographer: Photographer,
    navigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier,
    controlFavorite: (Photographer) -> Boolean,
    openDetailScreen: (Photographer?) -> Unit
) {
    Card(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .height(300.dp)
            .fillMaxWidth()
            .clip(CardDefaults.shape)
            .combinedClickable(
                //todo: verify this
                onClick = { openDetailScreen(photographer) },
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .weight(1f)
            ) {
                ReplyProfileImage(
                    photographer.src.landscape,
                    "photographer.sender.fullName32",
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxSize(),
                )
                extracted(controlFavorite, photographer, Modifier
                    .padding(8.dp)
                    .clip(CircleShape)
                    .align(Alignment.TopEnd)
                    .background(MaterialTheme.colorScheme.surface)
                )
            }

            Text(
                text = photographer.photographer ?: "",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 12.dp, start = 8.dp, end = 8.dp, bottom = 8.dp),
            )
            Text(
                modifier = Modifier
                    .padding(bottom = 8.dp, start = 8.dp, end = 8.dp),
                text = photographer.photographer_url ?: "",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
