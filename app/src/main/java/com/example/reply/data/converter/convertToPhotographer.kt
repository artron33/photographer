package com.example.reply.data.converter

import com.example.reply.data.library.room.Favorite
import com.example.reply.data.photographer.Photographer
import com.example.reply.data.photographer.Src

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