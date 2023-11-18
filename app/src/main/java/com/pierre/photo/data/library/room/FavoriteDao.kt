package com.pierre.photo.data.library.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertThisFavorite(favorite: FavoriteDataBase)

    @Delete
    suspend fun deleteThisFavorite(favorite: FavoriteDataBase)

    @Query("SELECT * FROM favorite_table ORDER BY time DESC")
    fun getAllFavorites(): Flow<List<FavoriteDataBase>?>

}
