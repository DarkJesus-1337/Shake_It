package com.darkjesus.shakeit.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.darkjesus.shakeit.data.model.Cocktail
import com.darkjesus.shakeit.ui.viewmodel.CocktailViewModel

/**
 * Displays a list of cocktails in a vertical arrangement.
 *
 * @param cocktails List of cocktails to display
 * @param onCocktailClick Callback when a cocktail is clicked
 * @param viewModel The CocktailViewModel for interacting with favorites
 * @param modifier Modifier for layout customization
 */
@Composable
fun CocktailListView(
    modifier: Modifier = Modifier,
    cocktails: List<Cocktail>,
    onCocktailClick: (Cocktail) -> Unit,
    viewModel: CocktailViewModel? = null
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(cocktails) { cocktail ->
            CocktailCard(
                cocktail = cocktail,
                onClick = { onCocktailClick(cocktail) },
                onFavoriteToggle = { viewModel?.toggleFavorite(it) }
            )
        }
    }
}