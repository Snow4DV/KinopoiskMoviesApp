package ru.snowadv.kinopoiskfeaturedmovies.presentation.ui.common

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlin.math.max

fun Modifier.shimmerEffect(round: Boolean = true): Modifier = composed {
    val infiniteTransition = rememberInfiniteTransition(label = "shimmer transition")

    var size by remember { mutableStateOf(IntSize.Zero) }

    val startOffsetX by infiniteTransition.animateFloat(
        initialValue = -3 * max(size.width, size.height).toFloat(),
        targetValue = 3 * max(size.width, size.height).toFloat(),
        animationSpec = infiniteRepeatable(animation = tween(1200)),
        label = "shimmer effect"
    )

    if (round) {
        clip(RoundedCornerShape(7.dp))
    } else {
        this
    }
        .background(
            brush = Brush.linearGradient(
                colors = if (isSystemInDarkTheme()) {
                    listOf(
                        Color(0xFF424242),
                        Color(0xFF3D3D3D),
                        Color(0xFF424242),
                    )
                } else {
                    listOf(
                        Color(0xFFE2E2E2),
                        Color(0xFFB8B5B5),
                        Color(0xFFE2E2E2),
                    )
                },
                start = Offset(startOffsetX, 0f),
                end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
            )
        )
        .onGloballyPositioned {
            size = it.size
        }


}

fun getBlankString(length: Int): String {
    return buildString {
        repeat(length) {
            append(' ')
        }
    }
}