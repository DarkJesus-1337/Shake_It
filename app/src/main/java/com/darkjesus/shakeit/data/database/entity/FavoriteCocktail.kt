package com.darkjesus.shakeit.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.darkjesus.shakeit.data.model.Cocktail

/**
 * Room entity representing a favorite cocktail in the database.
 *
 * Contains simplified data structure optimized for storage, with conversion
 * methods to and from the Cocktail model.
 *
 * @property id Unique identifier for the cocktail, used as primary key.
 * @property name Name of the cocktail.
 * @property imageUrl URL of the cocktail image, can be null.
 * @property category Category of the cocktail, can be null.
 * @property instructions Preparation instructions, can be null.
 * @property alcoholic Indicates if the cocktail contains alcohol, can be null.
 * @property glass Type of glass recommended for serving, can be null.
 * @property ingredients Comma-separated list of ingredients.
 * @property measures Comma-separated list of ingredient measures.
 */
@Entity(tableName = "favorite_cocktails")
data class FavoriteCocktail(
    @PrimaryKey val id: String,
    val name: String,
    val imageUrl: String?,
    val category: String? = null,
    val instructions: String? = null,
    val alcoholic: String? = null,
    val glass: String? = null,
    val ingredients: String = "",
    val measures: String = ""
) {
    /**
     * Converts this database entity to a Cocktail model.
     *
     * @return A Cocktail object with the data from this entity.
     */
    fun toCocktail(): Cocktail {
        val ingredientsList = if (ingredients.isNotEmpty()) {
            ingredients.split(",").map { it.trim() }
        } else emptyList()

        val measuresList = if (measures.isNotEmpty()) {
            measures.split(",").map { it.trim() }
        } else emptyList()

        return Cocktail(
            id = id,
            name = name,
            imageUrl = imageUrl,
            category = category,
            instructions = instructions,
            alcoholic = alcoholic,
            glass = glass,
            ingredient1 = ingredientsList.getOrNull(0),
            ingredient2 = ingredientsList.getOrNull(1),
            ingredient3 = ingredientsList.getOrNull(2),
            ingredient4 = ingredientsList.getOrNull(3),
            ingredient5 = ingredientsList.getOrNull(4),
            ingredient6 = ingredientsList.getOrNull(5),
            ingredient7 = ingredientsList.getOrNull(6),
            ingredient8 = ingredientsList.getOrNull(7),
            ingredient9 = ingredientsList.getOrNull(8),
            ingredient10 = ingredientsList.getOrNull(9),
            ingredient11 = ingredientsList.getOrNull(10),
            ingredient12 = ingredientsList.getOrNull(11),
            ingredient13 = ingredientsList.getOrNull(12),
            ingredient14 = ingredientsList.getOrNull(13),
            ingredient15 = ingredientsList.getOrNull(14),
            measure1 = measuresList.getOrNull(0),
            measure2 = measuresList.getOrNull(1),
            measure3 = measuresList.getOrNull(2),
            measure4 = measuresList.getOrNull(3),
            measure5 = measuresList.getOrNull(4),
            measure6 = measuresList.getOrNull(5),
            measure7 = measuresList.getOrNull(6),
            measure8 = measuresList.getOrNull(7),
            measure9 = measuresList.getOrNull(8),
            measure10 = measuresList.getOrNull(9),
            measure11 = measuresList.getOrNull(10),
            measure12 = measuresList.getOrNull(11),
            measure13 = measuresList.getOrNull(12),
            measure14 = measuresList.getOrNull(13),
            measure15 = measuresList.getOrNull(14)
        )
    }

    companion object {
        /**
         * Creates a FavoriteCocktail entity from a Cocktail model.
         *
         * @param cocktail The Cocktail model to convert.
         * @return A FavoriteCocktail entity containing the data from the Cocktail.
         */
        fun fromCocktail(cocktail: Cocktail): FavoriteCocktail {
            return FavoriteCocktail(
                id = cocktail.id,
                name = cocktail.name,
                imageUrl = cocktail.imageUrl,
                category = cocktail.category,
                instructions = cocktail.instructions,
                alcoholic = cocktail.alcoholic,
                glass = cocktail.glass,
                ingredients = cocktail.ingredients.joinToString(","),
                measures = cocktail.measures.joinToString(",")
            )
        }
    }
}