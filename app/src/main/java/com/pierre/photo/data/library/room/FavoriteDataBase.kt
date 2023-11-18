package com.pierre.photo.data.library.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pierre.photo.CONSTANTE.Companion.DB_FAVORITE_TABLE

@Entity(tableName = DB_FAVORITE_TABLE)
data class FavoriteDataBase(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val url: String,
    val photographer: String,
    val photographer_url: String,
    val photographer_id: String,
    val avg_color: String,
    val original: String,
    val medium: String,
    val small: String,
    val portrait: String,
    val landscape: String,
    val time: Long = System.currentTimeMillis()
)
