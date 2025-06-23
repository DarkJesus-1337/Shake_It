package com.darkjesus.shakeit.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.darkjesus.shakeit.data.model.Cocktail

@Composable
fun CocktailList(
    cocktails: List<Cocktail>,
    onCocktailClick: (Cocktail) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(cocktails) { cocktail ->
            CocktailCard(
                cocktail = cocktail,
                onClick = { onCocktailClick(cocktail) }
            )
        }
    }
}