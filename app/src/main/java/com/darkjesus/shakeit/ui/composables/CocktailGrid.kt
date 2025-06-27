package com.darkjesus.shakeit.ui.composables

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.darkjesus.shakeit.data.model.Cocktail
import com.darkjesus.shakeit.ui.viewmodel.CocktailViewModel

/**
 * A grid view for cocktails displayed in a 2-column grid.
 *
 * @param cocktails List of cocktails to display
 * @param onCocktailClick Callback when a cocktail is clicked
 * @param viewModel The CocktailViewModel for interacting with favorites
 * @param modifier Modifier for layout customization
 */
@Composable
fun CocktailGrid(
    modifier: Modifier = Modifier,
    cocktails: List<Cocktail>,
    onCocktailClick: (Cocktail) -> Unit,
    viewModel: CocktailViewModel? = null,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.fillMaxSize()
    ) {
        items(cocktails) { cocktail ->
            var showFavoriteIcon by remember { mutableStateOf(false) }

            // Beobachte den Favoriten-Status
            val isFavorite by viewModel?.isFavorite(cocktail.id)?.collectAsState(initial = false) ?: remember { mutableStateOf(false) }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .combinedClickable(
                        onClick = { onCocktailClick(cocktail) },
                        onDoubleClick = {
                            viewModel?.toggleFavorite(cocktail)
                            showFavoriteIcon = true
                        }
                    ),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column {
                    Box {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(cocktail.imageUrl)
                                .crossfade(true)
                                .build(),
                            contentDescription = cocktail.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                        )

                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(8.dp)
                        ) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                                tint = if (isFavorite) Color.Red else Color.White,
                                modifier = Modifier
                                    .combinedClickable(
                                        onClick = {
                                            viewModel?.toggleFavorite(cocktail)
                                        }
                                    )
                            )
                        }

                        if (showFavoriteIcon) {
                            FavoriteAnimation(
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }

                    Text(
                        text = cocktail.name,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }
            }
        }
    }
}
