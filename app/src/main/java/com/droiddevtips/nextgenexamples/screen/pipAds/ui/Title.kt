package com.droiddevtips.nextgenexamples.screen.pipAds.ui

import androidx.compose.foundation.ScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import com.droiddevtips.nextgenexamples.core.AppString

/**
 * The animated Machu Picchu title. While the 'scroll' state changes, the title scales down and
 * moves from the bottom of the header into the toolbar.
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
@Composable
fun Title(
    scroll: ScrollState,
    headerHeight: Dp,
    toolbarHeight: Dp,
    modifier: Modifier = Modifier
) {

    val titleFontScaleStart = 1f
    val titleFontScaleEnd = 0.66f
    val paddingMedium = 16.dp
    val titlePaddingStart = 16.dp
    val titlePaddingEnd = 72.dp

    var titleHeightPx by remember { mutableFloatStateOf(0f) }
    var titleWidthPx by remember { mutableFloatStateOf(0f) }

    Text(
        text = stringResource(id = AppString.machu_picchu),
        fontSize = 30.sp,
        color = Color.White,
        modifier = modifier.graphicsLayer {

            val collapseRange: Float = (headerHeight.toPx() - toolbarHeight.toPx())
            val collapseFraction: Float = (scroll.value / collapseRange).coerceIn(0f, 1f)

            val scaleXY = lerp(
                titleFontScaleStart.dp,
                titleFontScaleEnd.dp,
                collapseFraction
            )

            val titleExtraStartPadding = titleWidthPx.toDp() * (1 - scaleXY.value) / 2f

            val titleYFirstInterpolatedPoint = lerp(
                headerHeight - titleHeightPx.toDp() - paddingMedium,
                headerHeight / 2,
                collapseFraction
            )

            val titleXFirstInterpolatedPoint = lerp(
                titlePaddingStart,
                (titlePaddingEnd - titleExtraStartPadding) * 5 / 4,
                collapseFraction
            )

            val titleYSecondInterpolatedPoint = lerp(
                headerHeight / 2,
                toolbarHeight / 2 - titleHeightPx.toDp() / 2,
                collapseFraction
            )

            val titleXSecondInterpolatedPoint = lerp(
                (titlePaddingEnd - titleExtraStartPadding) * 5 / 4,
                0.dp,
                collapseFraction
            )

            val titleY = lerp(
                titleYFirstInterpolatedPoint,
                titleYSecondInterpolatedPoint,
                collapseFraction
            )

            val titleX = lerp(
                titleXFirstInterpolatedPoint,
                titleXSecondInterpolatedPoint,
                collapseFraction
            )

            translationY = titleY.toPx()
            translationX = titleX.toPx()
            scaleX = scaleXY.value
            scaleY = scaleXY.value

        }.onGloballyPositioned {
            titleHeightPx = it.size.height.toFloat()
            titleWidthPx = it.size.width.toFloat()
        },
        fontWeight = FontWeight.Bold
    )
}