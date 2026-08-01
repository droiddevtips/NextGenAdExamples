package com.droiddevtips.nextgenexamples.navigator.ui.detailPane.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.droiddevtips.nextgenexamples.navigator.ui.detailPane.data.Route
import com.droiddevtips.nextgenexamples.screen.bannerAdExample.BannerAdExample
import com.droiddevtips.nextgenexamples.screen.emptyScreen.EmptyScreen
import com.droiddevtips.nextgenexamples.screen.iconAds.IconAdsExample
import com.droiddevtips.nextgenexamples.screen.interstitialAds.InterstitialAdsExample
import com.droiddevtips.nextgenexamples.screen.nativeAdExample.NativeAdExample
import com.droiddevtips.nextgenexamples.screen.rewardedInterstitialAds.RewardedInterstitialAdsExample

@Composable
fun DetailView(route: Route, modifier: Modifier = Modifier) {
    when(route) {
        Route.BannerAdExample -> BannerAdExample(modifier = modifier)
        Route.EmptyScreen -> EmptyScreen(modifier = modifier)
        Route.IconAd -> IconAdsExample(modifier = modifier)
        Route.InterstitialAds -> InterstitialAdsExample(modifier = modifier)
        Route.NativeAdExample -> NativeAdExample(modifier = modifier)
        Route.NoItemSelected -> EmptyScreen(modifier = modifier)
        Route.RewardedInterstitialAdExample -> RewardedInterstitialAdsExample(modifier = modifier)
    }
}