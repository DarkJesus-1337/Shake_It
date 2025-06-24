package com.darkjesus.shakeit.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.darkjesus.shakeit.data.database.dao.FavoritesDao
import com.darkjesus.shakeit.data.database.entity.FavoriteCocktail

@Database(
    entities = [FavoriteCocktail::class],
    version = 1,
    exportSchema = false
)
abstract class FavoritesDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao

    companion object {
        @Volatile
        private var INSTANCE: FavoritesDatabase? = null

        fun getDatabase(context: Context): FavoritesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoritesDatabase::class.java,
                    "favorites_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}