package com.droiddevtips.nextgenexamples.ads.adManager

import android.content.Context
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAd

interface AdManager {

    suspend fun getBannerAd(context: Context, adUnit:String): BannerAd?

}