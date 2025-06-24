package com.darkjesus.shakeit.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.darkjesus.shakeit.data.database.dao.FavoritesDao
import com.darkjesus.shakeit.data.database.entity.FavoriteCocktail

/**
 * Room database class for managing favorite cocktails.
 *
 * Uses the singleton pattern to ensure only one instance of the database exists.
 */
@Database(
    entities = [FavoriteCocktail::class],
    version = 1,
    exportSchema = false
)
abstract class FavoritesDatabase : RoomDatabase() {
    /**
     * Provides access to the favorites DAO.
     *
     * @return The DAO for accessing favorite cocktail data.
     */
    abstract fun favoritesDao(): FavoritesDao

    companion object {
        @Volatile
        private var INSTANCE: FavoritesDatabase? = null

        /**
         * Gets the singleton instance of the database.
         * Creates it if it doesn't exist.
         *
         * @param context The application context.
         * @return The singleton database instance.
         */
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