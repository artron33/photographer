package com.pierre.photo.domain.converter

import com.pierre.photo.data.library.room.FavoriteDataBase
import com.pierre.photo.data.library.retrofit.PhotographerWeb
import com.pierre.photo.presentation.screen.PhotographerUi

fun FavoriteDataBase.convertToFavoritePhotographerUi() = PhotographerUi(
    id = this.id,
    url = this.url,
    photographer = this.photographer,
    photographer_url = this.photographer_url,
    photographer_id = this.photographer_id,
    avg_color = this.avg_color,
    isFavorite = true,
    original = this.original,
    medium = this.medium,
    small = this.small,
    landscape = this.landscape,
    portrait = this.portrait,
)

fun PhotographerWeb.convertToFavoritePhotographerUi(isFavorite: Boolean = false) = PhotographerUi(
    id = this.id,
    url = this.url,
    photographer = this.photographer,
    photographer_url = this.photographerUrl,
    photographer_id = this.photographerId,
    avg_color = this.avgColor,
    isFavorite = isFavorite,
    original = this.src.original,
    medium = this.src.medium,
    small = this.src.small,
    landscape = this.src.landscape,
    portrait = this.src.portrait,
)