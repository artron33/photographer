package com.pierre.photo.data.library.retrofit

import com.google.gson.annotations.SerializedName


data class Photos(
    val photos: List<PhotographerWeb> = mutableListOf(),
    @SerializedName("next_page")
    val nextPage: String? = null,
    @SerializedName("total_results")
    val totalResults: Int,
    @SerializedName("per_page")
    val perPage: Int,
    val page: Int,
)

data class PhotographerWeb(
    val id: Long,
    val width: String,
    val height: String,
    val url: String,
    val photographer: String,
    @SerializedName("photographer_url")
    val photographerUrl: String,
    @SerializedName("photographer_id")
    val photographerId: String,
    @SerializedName("avg_color")
    val avgColor: String,
    val src: Src,
    val isFavorite: Boolean,
    val resIdDrawableMock: Int? = null,
)

data class Src(
    val original: String,
    val large2x: String,
    val large: String,
    val medium: String,
    val small: String,
    val portrait: String,
    val landscape: String,
    val tiny: String

)