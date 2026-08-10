package com.droiddevtips.nextgenexamples.extensions

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

fun Modifier.borderRight(
    width: Dp = 1.dp,
    color: Color = Color.Blue
) = this.drawBehind {
    val strokeWidthPx = width.toPx()
    val x = size.width - strokeWidthPx / 2

    drawLine(
        color = color,
        start = Offset(x, 0f),
        end = Offset(x, size.height),
        strokeWidth = strokeWidthPx
    )
}

@Composable
fun Modifier.shimmerEffect(
    colors: List<Color> = listOf(
        MaterialTheme.colorScheme.secondaryContainer, // Color(0xFFB8B5B5),
        MaterialTheme.colorScheme.secondary,
        MaterialTheme.colorScheme.secondaryContainer,  // Color(0xFFB8B5B5)
    )
): Modifier = composed {

    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition()
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000
            )
        )
    )

    background(
        brush = Brush.linearGradient(
            colors = colors,
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    ).onGloballyPositioned {
        size = it.size
    }
}