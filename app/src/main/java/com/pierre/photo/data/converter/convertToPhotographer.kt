package com.pierre.photo.data.converter

import com.pierre.photo.data.library.room.Favorite
import com.pierre.photo.data.photographer.Photographer
import com.pierre.photo.data.photographer.Src

fun Favorite.convertToPhotographer() = Photographer(
        id = this.id,
        width = this.width,
        height = this.height,
        url = this.url,
        photographer = this.photographer,
        photographer_url = this.photographer_url,
        photographer_id = this.photographer_id,
        avg_color = this.avg_color,
        isFavorite = true,
        src = Src(
                original = this.original,
                medium = this.medium,
                small = this.small,
                landscape = this.landscape,
                portrait = this.portrait,
        )
)