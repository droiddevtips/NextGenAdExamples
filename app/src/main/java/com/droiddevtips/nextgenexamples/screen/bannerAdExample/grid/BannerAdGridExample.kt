package com.droiddevtips.nextgenexamples.screen.bannerAdExample.grid

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.droiddevtips.nextgenexamples.screen.bannerAdExample.data.BannerAdExampleViewState

@Composable
fun BannerAdGridExample(
    viewState: BannerAdExampleViewState,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {

        BannerAdGridList(viewState = viewState)

        BannerAdGridLoadingScreen(visible = viewState.isLoading)
    }
}