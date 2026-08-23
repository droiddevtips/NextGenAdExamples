package com.droiddevtips.nextgenexamples.screen.interstitialAds.grid

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.droiddevtips.nextgenexamples.screen.bannerAdExample.grid.BannerAdGridLoadingScreen
import com.droiddevtips.nextgenexamples.screen.interstitialAds.data.InterstitialAdArticle
import com.droiddevtips.nextgenexamples.screen.interstitialAds.data.InterstitialAdsExampleViewState

/**
 * The interstitial ad grid example composable
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
@Composable
fun InterstitialAdsGridExample(
    viewState: InterstitialAdsExampleViewState,
    modifier: Modifier = Modifier,
    onArticleClick: (InterstitialAdArticle) -> Unit = {}
) {
    Box(modifier = modifier) {

        InterstitialAdsGridList(viewState = viewState, onArticleClick = onArticleClick)

        BannerAdGridLoadingScreen(
            visible = viewState.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )
    }
}