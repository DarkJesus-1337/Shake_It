package com.darkjesus.shakeit.data.enums

import androidx.annotation.DrawableRes
import com.darkjesus.shakeit.R
import com.darkjesus.shakeit.ui.navigation.CocktailOfTheDayRoute
import com.darkjesus.shakeit.ui.navigation.FavoritesRoute
import com.darkjesus.shakeit.ui.navigation.SearchRoute

enum class TabItem(
    val route: Any,
    val tabTitle: String,
    @DrawableRes
    val tabIcon: Int
) {
    COD(
        route = CocktailOfTheDayRoute,
        tabTitle = "Cocktail of the Day",
        tabIcon = R.drawable.cod
    ),
    SEARCH(
        route = SearchRoute,
        tabTitle = "Search",
        tabIcon = R.drawable.search
    ),
    FAV(
        route = FavoritesRoute,
        tabTitle = "Favorites",
        tabIcon = R.drawable.favorites
    )
}