package com.droiddevtips.nextgenexamples.ads.domain

import android.content.Context
import com.droiddevtips.nextgenexamples.ads.domain.model.AdUnit
import com.google.android.libraries.ads.mobile.sdk.interstitial.InterstitialAd

interface InterstitialAdProvider {

    fun preLoadInterstitialAd(context: Context, adUnit: AdUnit, interstitialAdCount: (Int) -> Unit)
    fun isInterstitialAdAvailable(preLoaderID: String): Boolean
    fun pollInterstitialAd(preLoaderID: String): InterstitialAd?

}