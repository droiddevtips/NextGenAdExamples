package com.droiddevtips.nextgenexamples.screen.pipAds.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.droiddevtips.nextgenexamples.core.MipMap

/**
 * The collapsing parallax header displaying the Machu Picchu image with a dark gradient overlay
 * at the bottom behind the title. While the [scroll] state changes, the header translates up at
 * half the scroll speed and fades out.
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
@Composable
fun Header(
    headerHeight: Dp,
    scroll: ScrollState,
    modifier: Modifier = Modifier
) {

    val headerHeightPx = with(LocalDensity.current) { headerHeight.toPx() }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(headerHeight)
            .graphicsLayer {
                translationY = -scroll.value.toFloat() / 2f
                alpha = (-1f / headerHeightPx) * scroll.value + 1
            }
    ) {

        Image(
            painter = painterResource(id = MipMap.machu_picchu),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color(0xAA000000),
                            Color(0xAA000000)
                        ),
                        startY = 3 * headerHeightPx / 4 // to wrap the title only
                    )
                )
        )
    }
}