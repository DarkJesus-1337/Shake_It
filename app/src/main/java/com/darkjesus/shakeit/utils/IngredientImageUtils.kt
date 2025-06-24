package com.darkjesus.shakeit.utils

/**
 * Utility object for generating ingredient image URLs from The Cocktail DB API.
 *
 * Provides methods to generate URLs for different image sizes (small, medium, large)
 * based on ingredient names.
 */
object IngredientImageUtils {
    private const val BASE_URL = "https://www.thecocktaildb.com/images/ingredients/"
    private const val SMALL_SUFFIX = "-Small.png"
    private const val MEDIUM_SUFFIX = "-Medium.png"
    private const val LARGE_SUFFIX = ".png"

    /**
     * Generates a URL for a small-sized ingredient image.
     *
     * @param ingredientName The name of the ingredient.
     * @return URL string for the small image.
     */
    fun getSmallImageUrl(ingredientName: String): String {
        return "$BASE_URL${ingredientName.replace(" ", "%20")}$SMALL_SUFFIX"
    }

    /**
     * Generates a URL for a medium-sized ingredient image.
     *
     * @param ingredientName The name of the ingredient.
     * @return URL string for the medium image.
     */
    fun getMediumImageUrl(ingredientName: String): String {
        return "$BASE_URL${ingredientName.replace(" ", "%20")}$MEDIUM_SUFFIX"
    }

    /**
     * Generates a URL for a large-sized ingredient image.
     *
     * @param ingredientName The name of the ingredient.
     * @return URL string for the large image.
     */
    fun getLargeImageUrl(ingredientName: String): String {
        return "$BASE_URL${ingredientName.replace(" ", "%20")}$LARGE_SUFFIX"
    }
}
