package com.pierre.photo.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation

@Composable
fun PhotographerImage(
    drawableResource: String,
    description: String,
    modifier: Modifier = Modifier,
    isRound: Boolean = false,
    mockData: Int? = null,
) {
    Image(
        modifier = modifier,
        painter = if (mockData != null) painterResource(id = mockData)
                else rememberImagePainter(
            data = drawableResource,
            builder = {
                if (isRound) transformations(CircleCropTransformation())
                crossfade(true)
            }
        ),
        contentDescription = description,
        contentScale = ContentScale.Crop,
    )
}
