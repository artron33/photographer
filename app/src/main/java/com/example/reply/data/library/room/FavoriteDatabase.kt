package com.example.reply.data.library.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.reply.ui.LocationApplication

private const val DB_NAME = "favorite_database"

@Database(entities = [(Favorite::class)], version = 2)
abstract class FavoriteDatabase : RoomDatabase() {

    abstract fun locationDao(): FavoriteDao

    companion object {
        fun create(): FavoriteDatabase {
            return Room.databaseBuilder(
                    LocationApplication.mContext,
                    FavoriteDatabase::class.java,
                    DB_NAME
            ).build()
        }
    }

}
