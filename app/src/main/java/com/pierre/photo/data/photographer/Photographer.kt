package com.pierre.photo.data.photographer

data class Photo(
    val photos: List<Photographer> = mutableListOf()
)

data class Photographer(
    val id: Long? = 2L,
    val width: String = "",
    val height: String = "",
    val url: String = "",
    val photographer: String = "",
    val photographer_url: String = "",
    val photographer_id: String = "",
    val avg_color: String = "",
    val src: Src = Src(),
    val isFavorite: Boolean
)

data class Src(
    val original: String = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg",
    val large2x: String = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",
    val large: String = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&h=650&w=940",
    val medium: String = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&h=350",
    val small: String = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&h=130",
    val portrait: String = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=1200&w=800",
    val landscape: String = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=627&w=1200",
    val tiny: String = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&dpr=1&fit=crop&h=200&w=280"

)