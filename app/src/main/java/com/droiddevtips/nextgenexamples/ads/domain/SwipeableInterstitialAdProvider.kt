package com.droiddevtips.nextgenexamples.ads.domain

import com.droiddevtips.nextgenexamples.ads.domain.model.AdUnit
import com.google.android.libraries.ads.mobile.sdk.common.ExperimentalApi
import com.google.android.libraries.ads.mobile.sdk.swipeableinterstitial.SwipeableInterstitialAd

interface SwipeableInterstitialAdProvider {

    @OptIn(ExperimentalApi::class)
    suspend fun loadSwipeableAd(adUnit: AdUnit): SwipeableInterstitialAd?

}