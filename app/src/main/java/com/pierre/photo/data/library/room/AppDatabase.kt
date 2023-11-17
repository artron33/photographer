package com.pierre.photo.data.library.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pierre.photo.ui.PhotographerApplication

private const val DB_NAME = "favorite_database"

@Database(entities = [(Favorite::class)], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao

    companion object {
        fun create(): AppDatabase {
            return Room.databaseBuilder(
                    PhotographerApplication.mContext,
                    AppDatabase::class.java,
                    DB_NAME
            ).build()
        }
    }

}
