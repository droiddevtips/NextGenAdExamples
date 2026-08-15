package com.droiddevtips.nextgenexamples.ads.domain

import android.content.Context
import com.droiddevtips.nextgenexamples.ads.domain.model.AdUnit
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAd

/**
 * Provides banner ad functionality, abstracting the underlying ad SDK implementation
 * from the rest of the application.
 *
 * Implementations are responsible for preloading and polling of banner ads
 * for a given preload ID through domain-level types rather than SDK-specific callbacks.
 * This abstraction allows the ad SDK (e.g. Google Mobile Ads) to be swapped or mocked
 * without affecting consumers such as ViewModels or Composables.
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
interface BannerAdProvider {

    fun preLoadBannerAd(context: Context, adUnit: AdUnit)
    fun isAvailable(preLoaderID: String): Boolean
    fun pollBannerAd(preLoaderID: String): BannerAd?

}