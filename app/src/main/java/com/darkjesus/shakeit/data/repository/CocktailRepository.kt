package com.darkjesus.shakeit.data.repository

import com.darkjesus.shakeit.data.api.ApiService
import com.darkjesus.shakeit.data.model.Cocktail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CocktailRepository(
    private val apiService: ApiService
) {

    fun searchCocktailsByName(name: String): Flow<List<Cocktail>> = flow {
        try {
            val response = apiService.searchCocktailsByName(name)
            emit(response.drinks ?: emptyList())
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    fun searchCocktailsByFirstLetter(letter: String): Flow<List<Cocktail>> = flow {
        try {
            val response = apiService.searchCocktailsByFirstLetter(letter)
            emit(response.drinks ?: emptyList())
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    fun getRandomCocktail(): Flow<Cocktail?> = flow {
        try {
            val response = apiService.getRandomCocktail()
            emit(response.drinks?.firstOrNull())
        } catch (e: Exception) {
            emit(null)
        }
    }

    fun searchCocktailsByIngredient(ingredient: String): Flow<List<Cocktail>> = flow {
        try {
            val response = apiService.searchCocktailsByIngredient(ingredient)
            emit(response.drinks ?: emptyList())
        } catch (e: Exception) {
            emit(emptyList())
        }
    }
}