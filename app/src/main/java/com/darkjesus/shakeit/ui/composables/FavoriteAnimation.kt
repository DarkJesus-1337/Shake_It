package com.darkjesus.shakeit.ui.composables

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

/**
 * An animated heart component that appears briefly when a cocktail is added to favorites.
 *
 * @param modifier Modifier for positioning and sizing
 * @param duration Duration of the animation in milliseconds
 */
@Composable
fun FavoriteAnimation(
    modifier: Modifier = Modifier,
    duration: Long = 1000
) {
    var visible by remember { mutableStateOf(true) }
    val scale by animateFloatAsState(
        targetValue = if (visible) 1.5f else 0f,
        animationSpec = tween(durationMillis = 500),
        label = "scale"
    )
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = 500),
        label = "alpha"
    )

    LaunchedEffect(Unit) {
        delay(duration)
        visible = false
    }

    Box(modifier = modifier) {
        Icon(
            imageVector = Icons.Filled.Favorite,
            contentDescription = "Added to favorites",
            tint = Color.Red,
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.Center)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    this.alpha = alpha
                }
        )
    }
}
