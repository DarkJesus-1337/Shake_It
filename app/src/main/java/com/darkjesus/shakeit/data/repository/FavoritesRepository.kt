package com.darkjesus.shakeit.data.repository

import com.darkjesus.shakeit.data.database.dao.FavoritesDao
import com.darkjesus.shakeit.data.database.entity.FavoriteCocktail
import com.darkjesus.shakeit.data.model.Cocktail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoritesRepository(
    private val favoritesDao: FavoritesDao
) {

    fun getAllFavorites(): Flow<List<Cocktail>> =
        favoritesDao.getAllFavoriteCocktails().map { favoriteList ->
            favoriteList.map { it.toCocktail() }
        }

    fun isFavorite(cocktailId: String): Flow<Boolean> =
        favoritesDao.isFavorite(cocktailId)

    fun getFavoriteCount(): Flow<Int> =
        favoritesDao.getFavoriteCount()

    suspend fun addToFavorites(cocktail: Cocktail) {
        val favoriteCocktail = FavoriteCocktail.fromCocktail(cocktail)
        favoritesDao.insertFavoriteCocktail(favoriteCocktail)
    }

    suspend fun removeFromFavorites(cocktailId: String) {
        favoritesDao.deleteFavoriteCocktailById(cocktailId)
    }

    suspend fun clearAllFavorites() {
        favoritesDao.deleteAllFavorites()
    }
}