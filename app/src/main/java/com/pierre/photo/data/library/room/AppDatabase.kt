package com.pierre.photo.data.library.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pierre.photo.CONSTANTE.Companion.DB_NAME
import com.pierre.photo.CONSTANTE.Companion.DB_VERSION
import com.pierre.photo.presentation.PhotographerApplication


@Database(entities = [(FavoriteDataBase::class)], version = DB_VERSION)
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
