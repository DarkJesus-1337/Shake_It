package com.darkjesus.shakeit.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.darkjesus.shakeit.utils.IngredientImageUtils

/**
 * A composable that displays an ingredient with its measurement.
 *
 * This component shows the ingredient name and optional measurement information in a card layout.
 * It also displays an image of the ingredient loaded from the Cocktail DB API.
 *
 * @param ingredient The name of the ingredient to display.
 * @param measure The measurement of the ingredient (e.g., "2 oz"), can be null if no measurement is available.
 */
@Composable
fun IngredientItem(ingredient: String, measure: String?) {
    val showDetailDialog = remember { mutableStateOf(false) }

    if (showDetailDialog.value) {
        IngredientDetailDialog(
            ingredientName = ingredient,
            measure = measure,
            onDismiss = { showDetailDialog.value = false }
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { showDetailDialog.value = true },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(IngredientImageUtils.getSmallImageUrl(ingredient))
                            .crossfade(true)
                            .build(),
                        contentDescription = ingredient,
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = ingredient,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                if (measure != null) {
                    Text(
                        text = measure,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
