package com.droiddevtips.nextgenexamples.ads.domain

import android.content.Context
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAd

interface AdManager {

    suspend fun getBannerAd(context: Context, adUnit:String, bannerAd: (BannerAd?) -> Unit)

}