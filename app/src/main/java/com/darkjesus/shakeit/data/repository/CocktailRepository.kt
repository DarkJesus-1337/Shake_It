package com.darkjesus.shakeit.data.repository

import com.darkjesus.shakeit.data.api.ApiService
import com.darkjesus.shakeit.data.model.Cocktail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Repository class that handles cocktail data operations.
 *
 * This class abstracts the data sources and provides a clean API to the rest of the application
 * for accessing cocktail data from the remote API.
 *
 * @property apiService The service used to make API calls to the cocktail database.
 */
class CocktailRepository(
    private val apiService: ApiService
) {

    /**
     * Searches for cocktails by name.
     *
     * @param name The name or part of the name to search for.
     * @return A Flow emitting a list of cocktails matching the search criteria or an empty list if none found.
     */
    fun searchCocktailsByName(name: String): Flow<List<Cocktail>> = flow {
        try {
            val response = apiService.searchCocktailsByName(name)
            emit(response.drinks ?: emptyList())
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    /**
     * Searches for cocktails by their first letter.
     *
     * @param letter The first letter to search for.
     * @return A Flow emitting a list of cocktails starting with the given letter or an empty list if none found.
     */
    fun searchCocktailsByFirstLetter(letter: String): Flow<List<Cocktail>> = flow {
        try {
            val response = apiService.searchCocktailsByFirstLetter(letter)
            emit(response.drinks ?: emptyList())
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    /**
     * Fetches a random cocktail from the API.
     *
     * @return A Flow emitting a random cocktail or null if the request fails.
     */
    fun getRandomCocktail(): Flow<Cocktail?> = flow {
        try {
            val response = apiService.getRandomCocktail()
            emit(response.drinks?.firstOrNull())
        } catch (e: Exception) {
            emit(null)
        }
    }

    /**
     * Searches for cocktails containing a specific ingredient.
     *
     * @param ingredient The ingredient to search for.
     * @return A Flow emitting a list of cocktails containing the given ingredient or an empty list if none found.
     */
    fun searchCocktailsByIngredient(ingredient: String): Flow<List<Cocktail>> = flow {
        try {
            val response = apiService.searchCocktailsByIngredient(ingredient)
            val cocktails = response.drinks ?: emptyList()
            emit(cocktails)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    /**
     * Fetches a specific cocktail by its ID.
     *
     * @param id The unique identifier of the cocktail.
     * @return A Flow emitting the cocktail with the given ID or null if not found.
     */
    fun getCocktailById(id: String): Flow<Cocktail?> = flow {
        try {
            val response = apiService.getCocktailById(id)
            val cocktail = response.drinks?.firstOrNull()
            emit(cocktail)
        } catch (e: Exception) {
            emit(null)
        }
    }

    /**
     * Fetches all available ingredients from the API.
     *
     * This function retrieves the complete list of ingredients from the Cocktail DB API.
     * The ingredients are sorted alphabetically for easier browsing.
     *
     * @return A Flow emitting a list of ingredient names or an empty list if the request fails.
     */
    fun getAllIngredients(): Flow<List<String>> = flow {
        try {
            val response = apiService.getAllIngredients()
            val ingredients = response.drinks?.map { it.strIngredient1 }?.sorted() ?: emptyList()
            emit(ingredients)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }
}