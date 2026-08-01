package com.droiddevtips.nextgenexamples.screen.bannerAdExample.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class BannerAdExampleDisplayItem(val key: Int) : Parcelable {
    data class Article(private val _key: Int, val icon: Int, val title: String, val description: String) :
        BannerAdExampleDisplayItem(key = _key)

    data class AdView(private val _key: Int, val adUnit: String) : BannerAdExampleDisplayItem(key = _key)
}