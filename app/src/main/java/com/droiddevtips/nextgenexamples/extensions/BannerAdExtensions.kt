package com.droiddevtips.nextgenexamples.extensions

import com.droiddevtips.nextgenexamples.logging.data.LoggerImpl
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAd
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAdEventCallback
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAdRefreshCallback
import com.google.android.libraries.ads.mobile.sdk.common.LoadAdError

/**
 * Banner ad extension functions.
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
fun BannerAd.addEventCallback(key: String) {
    val logger = LoggerImpl(className = "BannerAd.addEventCallback extensions func")
    adEventCallback = object : BannerAdEventCallback {

        override fun onAdImpression() {
            super.onAdImpression()
            logger.log(message = "'${key} on ad impression'")
        }

        override fun onAdClicked() {
            super.onAdClicked()
            logger.log(message = "'${key}' on ad clicked!")
        }
    }
}

fun BannerAd.addBannerAdRefreshCallback(key: String) {
    val logger = LoggerImpl("BannerAd.addBannerAdRefreshCallback extensions func")
    bannerAdRefreshCallback = object : BannerAdRefreshCallback {

        override fun onAdRefreshed() {
            super.onAdRefreshed()
            logger.log(message = "'${key}' on ad refreshed!")
        }

        override fun onAdFailedToRefresh(adError: LoadAdError) {
            super.onAdFailedToRefresh(adError)
            logger.log(message = "'${key}' on ad failed to refresh")
        }
    }
}