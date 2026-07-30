package com.droiddevtips.nextgenexamples.extensions

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
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