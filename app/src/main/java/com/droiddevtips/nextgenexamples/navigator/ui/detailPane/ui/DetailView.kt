package com.droiddevtips.nextgenexamples.navigator.ui.detailPane.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.droiddevtips.nextgenexamples.navigator.data.Screen
import com.droiddevtips.nextgenexamples.screen.bannerAdExample.ui.BannerAdExample
import com.droiddevtips.nextgenexamples.screen.emptyScreen.EmptyScreen
import com.droiddevtips.nextgenexamples.screen.iconAds.IconAdsExample
import com.droiddevtips.nextgenexamples.screen.interstitialAds.InterstitialAdsExample
import com.droiddevtips.nextgenexamples.screen.nativeAdExample.NativeAdExample
import com.droiddevtips.nextgenexamples.screen.rewardedInterstitialAds.RewardedInterstitialAdsExample

@Composable
fun DetailView(screen: Screen, modifier: Modifier = Modifier) {
    when(screen) {
        Screen.BannerAdExample -> BannerAdExample(screen = screen, modifier = modifier)
        Screen.EmptyScreen -> EmptyScreen(modifier = modifier)
        Screen.IconAd -> IconAdsExample(modifier = modifier)
        Screen.InterstitialAds -> InterstitialAdsExample(modifier = modifier)
        Screen.NativeAdExample -> NativeAdExample(modifier = modifier)
        Screen.NoItemSelected -> EmptyScreen(modifier = modifier)
        Screen.RewardedInterstitialAdExample -> RewardedInterstitialAdsExample(modifier = modifier)
    }
}