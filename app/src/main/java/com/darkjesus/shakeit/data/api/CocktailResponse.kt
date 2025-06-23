package com.darkjesus.shakeit.data.api

import com.darkjesus.shakeit.data.model.Cocktail
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CocktailResponse(
    val drinks: List<Cocktail>?
)
