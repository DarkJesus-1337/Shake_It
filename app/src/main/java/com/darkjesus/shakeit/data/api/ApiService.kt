package com.darkjesus.shakeit.data.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ApiService {
    @GET("search.php")
    suspend fun searchCocktailsByFirstLetter(@Query("f") firstLetter: String): CocktailResponse

    @GET("search.php")
    suspend fun searchCocktailsByName(@Query("s") name: String): CocktailResponse

    @GET("random.php")
    suspend fun getRandomCocktail(): CocktailResponse

    @GET("filter.php")
    suspend fun searchCocktailsByIngredient(@Query("i") ingredient: String): CocktailResponse

}

object CocktailAPI {
    val retrofitService: ApiService by lazy { retrofit.create(ApiService::class.java) }
}