package com.darkjesus.shakeit.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.darkjesus.shakeit.data.database.entity.FavoriteCocktail
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object interface for managing favorite cocktails in the database.
 *
 * Provides methods to create, read, update, and delete favorite cocktails.
 */
@Dao
interface FavoritesDao {

    /**
     * Inserts a cocktail into favorites.
     * If a cocktail with the same ID already exists, it will be replaced.
     *
     * @param favoriteCocktail The cocktail to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteCocktail(favoriteCocktail: FavoriteCocktail)

    /**
     * Deletes a cocktail from favorites.
     *
     * @param favoriteCocktail The cocktail to delete.
     */
    @Delete
    suspend fun deleteFavoriteCocktail(favoriteCocktail: FavoriteCocktail)

    /**
     * Deletes a cocktail from favorites by its ID.
     *
     * @param cocktailId The ID of the cocktail to delete.
     */
    @Query("DELETE FROM favorite_cocktails WHERE id = :cocktailId")
    suspend fun deleteFavoriteCocktailById(cocktailId: String)

    /**
     * Retrieves all favorite cocktails ordered by name.
     *
     * @return A Flow emitting a list of all favorite cocktails.
     */
    @Query("SELECT * FROM favorite_cocktails ORDER BY name ASC")
    fun getAllFavoriteCocktails(): Flow<List<FavoriteCocktail>>

    /**
     * Checks if a cocktail is marked as favorite.
     *
     * @param cocktailId The ID of the cocktail to check.
     * @return A Flow emitting true if the cocktail is a favorite, false otherwise.
     */
    @Query("SELECT EXISTS(SELECT 1 FROM favorite_cocktails WHERE id = :cocktailId)")
    fun isFavorite(cocktailId: String): Flow<Boolean>

    /**
     * Counts the number of favorite cocktails.
     *
     * @return A Flow emitting the count of favorite cocktails.
     */
    @Query("SELECT COUNT(*) FROM favorite_cocktails")
    fun getFavoriteCount(): Flow<Int>

    /**
     * Deletes all cocktails from favorites.
     */
    @Query("DELETE FROM favorite_cocktails")
    suspend fun deleteAllFavorites()
}