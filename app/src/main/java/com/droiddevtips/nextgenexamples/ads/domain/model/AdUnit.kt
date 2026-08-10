package com.droiddevtips.nextgenexamples.ads.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class AdUnit(val key: String, val adUnit: String): Parcelable {

    data class BannerAd(private val _key: String) : AdUnit(key = "banner_ad_$_key", adUnit = "ca-app-pub-3940256099942544/9214589741")

}