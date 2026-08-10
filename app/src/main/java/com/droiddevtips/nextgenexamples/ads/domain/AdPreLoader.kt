package com.droiddevtips.nextgenexamples.ads.domain

import android.content.Context
import com.droiddevtips.nextgenexamples.ads.domain.model.AdUnit
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAd

interface AdPreLoader {

    fun preLoadBannerAd(context: Context, adUnit: String, result: (BannerAd?) -> Unit)

}