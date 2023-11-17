package com.pierre.photo.data.library.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertThisFavorite(favorite: Favorite)

    @Delete
    suspend fun deleteThisFavorite(favorite: Favorite)

    @Query("SELECT * FROM favorite_table ORDER BY time DESC")
    fun getAllFavorites(): List<Favorite>?

}
