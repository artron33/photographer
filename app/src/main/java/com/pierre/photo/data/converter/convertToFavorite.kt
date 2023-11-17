package com.pierre.photo.data.converter

import com.pierre.photo.data.library.room.Favorite
import com.pierre.photo.data.photographer.Photographer

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