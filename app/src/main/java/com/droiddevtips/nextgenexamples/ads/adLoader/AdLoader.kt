package com.droiddevtips.nextgenexamples.ads.adLoader

import android.content.Context
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAd

interface AdLoader {

    suspend fun loadBannerAd(context: Context, adUnit: String): BannerAd?

}