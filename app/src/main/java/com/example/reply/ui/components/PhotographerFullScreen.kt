package com.example.reply.ui.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.reply.data.photographer.Photographer
import com.example.reply.ui.theme.transparent

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalAnimationApi::class
)
@Composable
fun PhotographerFullScreen(
    photographer: Photographer,
    controlFavorite: (Photographer) -> Boolean,
) {
    Box {
        PhotographerImage(
            photographer.src.portrait,
            "photographer.sender.fullName32",
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.90f)
        )

        favoriteIcon(controlFavorite, photographer, Modifier
            .padding(8.dp)
            .clip(CircleShape)
            .align(Alignment.TopEnd)
            .background(MaterialTheme.colorScheme.surface)
        )
    }

    Text(
        text = photographer.photographer_url,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.padding(top = 12.dp, start = 8.dp, end = 8.dp, bottom = 8.dp),
    )
}

@Composable
fun favoriteIcon(
    controlFavorite: ((Photographer) -> Boolean)?,
    photographer: Photographer,
    modifier: Modifier
) {
//    if (controlFavorite != null) {
        var isAdded by remember { mutableStateOf(photographer.isFavorite) }
        IconButton(
            onClick = { controlFavorite?.let { isAdded = it.invoke(photographer) } },
            modifier = modifier
        ) {
            Icon(
                imageVector = if (isAdded) Icons.Default.Star else Icons.Default.StarBorder,
                contentDescription = "Favorite",
                tint = MaterialTheme.colorScheme.outline
            )
        }
//    } else {
//        Icon(
//            modifier = Modifier
//                .background(transparent),
//            imageVector = Icons.Default.Star,
//            contentDescription = "Favorite",
//            tint = MaterialTheme.colorScheme.outline
//        )
//    }
}
