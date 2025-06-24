package com.darkjesus.shakeit.data.api

import com.darkjesus.shakeit.data.model.Cocktail
import com.squareup.moshi.JsonClass

/**
 * Data class representing the API response for cocktail-related requests.
 *
 * The API returns cocktail data in a wrapper object with a "drinks" property.
 * The drinks property can be null if no cocktails are found.
 *
 * @property drinks List of cocktails returned by the API, or null if none found.
 */
@JsonClass(generateAdapter = true)
data class CocktailResponse(
    val drinks: List<Cocktail>?
)
