package com.darkjesus.shakeit.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.darkjesus.shakeit.data.database.entity.FavoriteCocktail
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteCocktail(favoriteCocktail: FavoriteCocktail)

    @Delete
    suspend fun deleteFavoriteCocktail(favoriteCocktail: FavoriteCocktail)

    @Query("DELETE FROM favorite_cocktails WHERE id = :cocktailId")
    suspend fun deleteFavoriteCocktailById(cocktailId: String)

    @Query("SELECT * FROM favorite_cocktails ORDER BY name ASC")
    fun getAllFavoriteCocktails(): Flow<List<FavoriteCocktail>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_cocktails WHERE id = :cocktailId)")
    fun isFavorite(cocktailId: String): Flow<Boolean>

    @Query("SELECT COUNT(*) FROM favorite_cocktails")
    fun getFavoriteCount(): Flow<Int>

    @Query("DELETE FROM favorite_cocktails")
    suspend fun deleteAllFavorites()
}