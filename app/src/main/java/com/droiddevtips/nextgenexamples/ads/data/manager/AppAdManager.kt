package com.droiddevtips.nextgenexamples.ads.data.manager

import com.droiddevtips.nextgenexamples.ads.data.preloader.AdLoaderImpl
import com.droiddevtips.nextgenexamples.ads.domain.AdManager
import com.droiddevtips.nextgenexamples.googleAdsConsentManager.GoogleAdsConsentManager
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAd

object AppAdManager: AdManager {

    val APP_ID = "ca-app-pub-3940256099942544~3347511713"

    override fun getBannerAd(
        preLoaderID: String
    ): BannerAd? {

        if (!GoogleAdsConsentManager.canRequestAds()) {
            return null
        }

        return AdLoaderImpl.getBannerAd(preLoaderID = preLoaderID)
    }
}