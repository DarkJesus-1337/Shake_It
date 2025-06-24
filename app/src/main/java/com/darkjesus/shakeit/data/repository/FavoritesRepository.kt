package com.darkjesus.shakeit.data.repository

import com.darkjesus.shakeit.data.database.dao.FavoritesDao
import com.darkjesus.shakeit.data.database.entity.FavoriteCocktail
import com.darkjesus.shakeit.data.model.Cocktail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Repository for managing favorite cocktails in local storage.
 *
 * Handles the conversion between Cocktail models and FavoriteCocktail entities,
 * and provides methods for adding, removing, and querying favorite cocktails.
 *
 * @property favoritesDao The DAO for accessing the favorites database.
 */
class FavoritesRepository(
    private val favoritesDao: FavoritesDao
) {

    /**
     * Gets all favorite cocktails.
     *
     * @return A Flow emitting a list of all favorite cocktails.
     */
    fun getAllFavorites(): Flow<List<Cocktail>> =
        favoritesDao.getAllFavoriteCocktails().map { favoriteList ->
            favoriteList.map { it.toCocktail() }
        }

    /**
     * Checks if a cocktail is marked as favorite.
     *
     * @param cocktailId The ID of the cocktail to check.
     * @return A Flow emitting true if the cocktail is a favorite, false otherwise.
     */
    fun isFavorite(cocktailId: String): Flow<Boolean> =
        favoritesDao.isFavorite(cocktailId)

    /**
     * Adds a cocktail to favorites.
     *
     * @param cocktail The cocktail to add to favorites.
     */
    suspend fun addToFavorites(cocktail: Cocktail) {
        val favoriteCocktail = FavoriteCocktail.fromCocktail(cocktail)
        favoritesDao.insertFavoriteCocktail(favoriteCocktail)
    }

    /**
     * Removes a cocktail from favorites.
     *
     * @param cocktailId The ID of the cocktail to remove.
     */
    suspend fun removeFromFavorites(cocktailId: String) {
        favoritesDao.deleteFavoriteCocktailById(cocktailId)
    }

    /**
     * Removes all cocktails from favorites.
     */
    suspend fun clearAllFavorites() {
        favoritesDao.deleteAllFavorites()
    }
}