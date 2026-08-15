package com.droiddevtips.nextgenexamples.ads.domain

import com.google.android.libraries.ads.mobile.sdk.banner.BannerAd

interface AdManager {

    fun getBannerAd(preLoaderID:String): BannerAd?

}