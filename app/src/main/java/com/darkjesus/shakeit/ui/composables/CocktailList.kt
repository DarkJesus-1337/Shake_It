package com.darkjesus.shakeit.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.darkjesus.shakeit.data.model.Cocktail
import com.darkjesus.shakeit.ui.viewmodel.CocktailViewModel
import com.darkjesus.shakeit.ui.viewmodel.ViewMode

@Composable
fun CocktailList(
    modifier: Modifier = Modifier,
    cocktails: List<Cocktail>,
    onCocktailClick: (Cocktail) -> Unit,
    viewModel: CocktailViewModel? = null,
    viewMode: ViewMode = ViewMode.LIST
) {
    when (viewMode) {
        ViewMode.LIST -> {
            CocktailListView(
                cocktails = cocktails,
                onCocktailClick = onCocktailClick,
                viewModel = viewModel,
                modifier = modifier
            )
        }

        ViewMode.GRID -> {
            CocktailGrid(
                cocktails = cocktails,
                onCocktailClick = onCocktailClick,
                viewModel = viewModel,
                modifier = modifier
            )
        }
    }
}
