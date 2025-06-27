package com.darkjesus.shakeit.data.api

import com.darkjesus.shakeit.data.model.IngredientListResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Base URL for The Cocktail DB API.
 */
const val BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

/**
 * Retrofit interface for The Cocktail DB API.
 *
 * Provides methods to fetch cocktail data from the API endpoints.
 */
interface ApiService {
    /**
     * Searches for cocktails by first letter.
     *
     * @param firstLetter The first letter to search for.
     * @return A response containing a list of cocktails.
     */
    @GET("search.php")
    suspend fun searchCocktailsByFirstLetter(@Query("f") firstLetter: String): CocktailResponse

    /**
     * Searches for cocktails by name.
     *
     * @param name The name or partial name to search for.
     * @return A response containing a list of cocktails.
     */
    @GET("search.php")
    suspend fun searchCocktailsByName(@Query("s") name: String): CocktailResponse

    /**
     * Fetches a random cocktail.
     *
     * @return A response containing a single random cocktail.
     */
    @GET("random.php")
    suspend fun getRandomCocktail(): CocktailResponse

    /**
     * Searches for cocktails by ingredient.
     *
     * @param ingredient The ingredient to search for.
     * @return A response containing a list of cocktails.
     */
    @GET("filter.php")
    suspend fun searchCocktailsByIngredient(@Query("i") ingredient: String): CocktailResponse

    /**
     * Fetches detailed information for a specific cocktail by ID.
     *
     * @param id The ID of the cocktail.
     * @return A response containing the requested cocktail.
     */
    @GET("lookup.php")
    suspend fun getCocktailById(@Query("i") id: String): CocktailResponse

    /**
     * Fetches the list of all available ingredients from the API.
     *
     * @param type The type of list to fetch, default is "list" for ingredients.
     * @return A response containing a list of ingredients.
     */
    @GET("list.php")
    suspend fun getAllIngredients(@Query("i") type: String = "list"): IngredientListResponse
}

/**
 * Singleton object that provides access to the API service.
 */
object CocktailAPI {
    val retrofitService: ApiService by lazy { retrofit.create(ApiService::class.java) }
}