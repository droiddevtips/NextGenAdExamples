package com.droiddevtips.nextgenexamples.ads.adManager

import android.content.Context
import com.droiddevtips.nextgenexamples.ads.adLoader.AdLoaderImplementation
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAd
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAdPreloader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object AppAdManager: AdManager {

    val BANNER_AD_UNIT = "ca-app-pub-3940256099942544/9214589741"

    override suspend fun getBannerAd(
        context: Context,
        adUnit: String
    ): BannerAd? = withContext(Dispatchers.IO) {

        val isBannerAvailable = BannerAdPreloader.isAdAvailable(preloadId = adUnit)
        if (isBannerAvailable) {
            return@withContext withContext(Dispatchers.Main) {
                BannerAdPreloader.pollAd(preloadId = adUnit)
            }
        }

        return@withContext withContext(Dispatchers.Main) {
            AdLoaderImplementation.loadBannerAd(context = context, adUnit = adUnit)
        }
    }
}