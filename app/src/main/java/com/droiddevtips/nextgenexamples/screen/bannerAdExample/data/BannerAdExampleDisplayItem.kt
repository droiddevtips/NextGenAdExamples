package com.droiddevtips.nextgenexamples.screen.bannerAdExample.data

import android.os.Parcelable
import com.droiddevtips.nextgenexamples.ads.domain.model.AdUnit
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class BannerAdExampleDisplayItem(val key: String) : Parcelable {
    data class Article(private val _key: Int, val icon: Int, val title: String, val description: String) :
        BannerAdExampleDisplayItem(key = _key.toString())

    data class AdView(private val _adUnit: AdUnit) : BannerAdExampleDisplayItem(key = _adUnit.key)
}