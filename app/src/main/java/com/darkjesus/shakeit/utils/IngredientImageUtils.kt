package com.darkjesus.shakeit.utils

object IngredientImageUtils {
    private const val BASE_URL = "https://www.thecocktaildb.com/images/ingredients/"
    private const val SMALL_SUFFIX = "-Small.png"
    private const val MEDIUM_SUFFIX = "-Medium.png"
    private const val LARGE_SUFFIX = ".png"

    fun getSmallImageUrl(ingredientName: String): String {
        return "$BASE_URL${ingredientName.replace(" ", "%20")}$SMALL_SUFFIX"
    }

    fun getMediumImageUrl(ingredientName: String): String {
        return "$BASE_URL${ingredientName.replace(" ", "%20")}$MEDIUM_SUFFIX"
    }

    fun getLargeImageUrl(ingredientName: String): String {
        return "$BASE_URL${ingredientName.replace(" ", "%20")}$LARGE_SUFFIX"
    }
}
