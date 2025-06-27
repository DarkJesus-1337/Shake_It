package com.darkjesus.shakeit.data.model

/**
 * Data class representing the API response for ingredient list requests.
 *
 * @property drinks List of ingredient items returned by the API, or null if none found.
 */
data class IngredientListResponse(
    val drinks: List<IngredientItem>?
)

/**
 * Data class representing a single ingredient item from the API response.
 *
 * The API returns ingredients with a strIngredient1 property.
 *
 * @property strIngredient1 The name of the ingredient.
 */
data class IngredientItem(
    val strIngredient1: String
)