package com.droiddevtips.nextgenexamples.screen.bannerAdExample.grid

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.droiddevtips.nextgenexamples.screen.bannerAdExample.data.BannerAdExampleViewState

/**
 * The banner ad grid example composable
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun BannerAdGridExample(
    viewState: BannerAdExampleViewState,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {

        BannerAdGridList(viewState = viewState)

        BannerAdGridLoadingScreen(visible = viewState.isLoading, modifier = Modifier.fillMaxWidth().fillMaxHeight())
    }
}