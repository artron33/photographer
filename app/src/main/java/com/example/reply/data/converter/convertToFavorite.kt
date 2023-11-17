package com.example.reply.data.converter

import com.example.reply.data.library.room.Favorite
import com.example.reply.data.photographer.Photographer

fun Photographer.convertToFavorite() = Favorite(
    id = this.id,
    width = this.width,
    height = this.height,
    url = this.url,
    photographer = this.photographer,
    photographer_url = this.photographer_url,
    photographer_id = this.photographer_id,
    avg_color = this.avg_color,
    original = this.src.original,
    medium = this.src.medium,
    small = this.src.small,
    landscape = this.src.landscape,
    portrait = this.src.portrait

)