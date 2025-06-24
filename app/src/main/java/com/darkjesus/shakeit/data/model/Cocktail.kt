package com.darkjesus.shakeit.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Cocktail(
    @Json(name = "idDrink") val id: String,
    @Json(name = "strDrink") val name: String,
    @Json(name = "strDrinkThumb") val imageUrl: String?,

    @Json(name = "strCategory") val category: String? = null,
    @Json(name = "strInstructions") val instructions: String? = null,
    @Json(name = "strAlcoholic") val alcoholic: String? = null,
    @Json(name = "strGlass") val glass: String? = null,

    @Json(name = "strIngredient1") val ingredient1: String? = null,
    @Json(name = "strIngredient2") val ingredient2: String? = null,
    @Json(name = "strIngredient3") val ingredient3: String? = null,
    @Json(name = "strIngredient4") val ingredient4: String? = null,
    @Json(name = "strIngredient5") val ingredient5: String? = null,
    @Json(name = "strIngredient6") val ingredient6: String? = null,
    @Json(name = "strIngredient7") val ingredient7: String? = null,
    @Json(name = "strIngredient8") val ingredient8: String? = null,
    @Json(name = "strIngredient9") val ingredient9: String? = null,
    @Json(name = "strIngredient10") val ingredient10: String? = null,
    @Json(name = "strIngredient11") val ingredient11: String? = null,
    @Json(name = "strIngredient12") val ingredient12: String? = null,
    @Json(name = "strIngredient13") val ingredient13: String? = null,
    @Json(name = "strIngredient14") val ingredient14: String? = null,
    @Json(name = "strIngredient15") val ingredient15: String? = null,

    @Json(name = "strMeasure1") val measure1: String? = null,
    @Json(name = "strMeasure2") val measure2: String? = null,
    @Json(name = "strMeasure3") val measure3: String? = null,
    @Json(name = "strMeasure4") val measure4: String? = null,
    @Json(name = "strMeasure5") val measure5: String? = null,
    @Json(name = "strMeasure6") val measure6: String? = null,
    @Json(name = "strMeasure7") val measure7: String? = null,
    @Json(name = "strMeasure8") val measure8: String? = null,
    @Json(name = "strMeasure9") val measure9: String? = null,
    @Json(name = "strMeasure10") val measure10: String? = null,
    @Json(name = "strMeasure11") val measure11: String? = null,
    @Json(name = "strMeasure12") val measure12: String? = null,
    @Json(name = "strMeasure13") val measure13: String? = null,
    @Json(name = "strMeasure14") val measure14: String? = null,
    @Json(name = "strMeasure15") val measure15: String? = null
) {
    val ingredients: List<String>
        get() = listOfNotNull(
            ingredient1, ingredient2, ingredient3, ingredient4, ingredient5,
            ingredient6, ingredient7, ingredient8, ingredient9, ingredient10,
            ingredient11, ingredient12, ingredient13, ingredient14, ingredient15
        ).filter { it.isNotEmpty() }

    val measures: List<String>
        get() = listOfNotNull(
            measure1, measure2, measure3, measure4, measure5,
            measure6, measure7, measure8, measure9, measure10,
            measure11, measure12, measure13, measure14, measure15
        ).filter { it.isNotEmpty() }

    val ingredientsWithMeasures: List<Pair<String, String?>>
        get() {
            val ingredientsList = ingredients
            val measuresList = measures
            return ingredientsList.mapIndexed { index, ingredient ->
                ingredient to measuresList.getOrNull(index)
            }
        }
}