package com.darkjesus.shakeit.data.enums

import androidx.annotation.DrawableRes
import com.darkjesus.shakeit.R
import com.darkjesus.shakeit.ui.navigation.CocktailOfTheDayRoute
import com.darkjesus.shakeit.ui.navigation.FavoritesRoute
import com.darkjesus.shakeit.ui.navigation.SearchRoute

/**
 * Enum representing the main tabs in the application's bottom navigation.
 *
 * Each tab item contains information about its navigation route, display title,
 * and the icon to be shown in the bottom navigation bar.
 *
 * @property route The navigation route object associated with this tab.
 * @property tabTitle The user-facing title displayed for this tab.
 * @property tabIcon The resource ID for the icon displayed in the tab.
 */
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