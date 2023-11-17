package com.example.reply.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation

@Composable
fun PhotographerImage(
    drawableResource: String,
    description: String,
    modifier: Modifier = Modifier,
    isRound: Boolean = false,
) {
    Image(
        modifier = modifier,
        painter = rememberImagePainter(
            data = drawableResource,
            builder = {
                if (isRound) transformations(CircleCropTransformation())
//                crossfade(true)
            }
        ),
        contentDescription = description,
        contentScale = ContentScale.Crop,
    )
}
