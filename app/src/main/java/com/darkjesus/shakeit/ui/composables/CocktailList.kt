package com.darkjesus.shakeit.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.darkjesus.shakeit.data.model.Cocktail
import com.darkjesus.shakeit.ui.viewmodel.CocktailViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CocktailList(
    cocktails: List<Cocktail>,
    onCocktailClick: (Cocktail) -> Unit,
    viewModel: CocktailViewModel = koinViewModel()
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(cocktails) { cocktail ->
            CocktailCard(
                cocktail = cocktail,
                onClick = { onCocktailClick(cocktail) },
                onFavoriteToggle = { viewModel.toggleFavorite(it) }
            )
        }
    }
}