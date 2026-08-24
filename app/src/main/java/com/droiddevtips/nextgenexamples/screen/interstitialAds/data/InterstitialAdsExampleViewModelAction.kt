package com.droiddevtips.nextgenexamples.screen.interstitialAds.data

sealed interface InterstitialAdsExampleViewModelAction {
    data object DestroyAllBannerAds: InterstitialAdsExampleViewModelAction
}