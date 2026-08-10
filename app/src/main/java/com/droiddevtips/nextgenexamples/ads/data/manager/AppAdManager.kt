package com.droiddevtips.nextgenexamples.ads.data.manager

import android.content.Context
import android.util.Log
import com.droiddevtips.nextgenexamples.ads.data.preloader.AdPreLoaderImplementation
import com.droiddevtips.nextgenexamples.ads.domain.AdManager
import com.droiddevtips.nextgenexamples.googleAdsConsentManager.GoogleAdsConsentManager
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAd
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAdPreloader

object AppAdManager: AdManager {

    val BANNER_AD_UNIT = "ca-app-pub-3940256099942544/9214589741"
    val APP_ID = "ca-app-pub-3940256099942544~3347511713"

    override suspend fun getBannerAd(
        context: Context,
        adUnit: String,
        bannerAd: (BannerAd?) -> Unit
    ) {

        Log.i("TAG15","\n|-------------- get banner ad --------------|")
        Log.i("TAG15","Can request ads -> ${GoogleAdsConsentManager.canRequestAds()}")

        if (!GoogleAdsConsentManager.canRequestAds()) {
            bannerAd(null)
            return
        }

        val isBannerAvailable = BannerAdPreloader.isAdAvailable(preloadId = adUnit)
        Log.i("TAG15","is banner ads available -> ${isBannerAvailable}")
        if (isBannerAvailable) {
            bannerAd(BannerAdPreloader.pollAd(preloadId = adUnit))
            return
        }

        AdPreLoaderImplementation.preLoadBannerAd(context = context, adUnit = adUnit) { bannerAd ->
            bannerAd(bannerAd)
        }

        Log.i("TAG15","\n|-------------- get banner ad --------------|")
    }
}