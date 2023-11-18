package com.pierre.photo.domain.converter

import com.pierre.photo.data.library.room.FavoriteDataBase
import com.pierre.photo.data.library.retrofit.PhotographerWeb
import com.pierre.photo.presentation.screen.PhotographerUi

fun PhotographerUi.convertToFavoriteDatabase() = FavoriteDataBase(
    id = this.id,
    url = this.url,
    photographer = this.photographer,
    photographer_url = this.photographer_url,
    photographer_id = this.photographer_id,
    avg_color = this.avg_color,
    original = this.original,
    medium = this.medium,
    small = this.small,
    landscape = this.landscape,
    portrait = this.portrait

)