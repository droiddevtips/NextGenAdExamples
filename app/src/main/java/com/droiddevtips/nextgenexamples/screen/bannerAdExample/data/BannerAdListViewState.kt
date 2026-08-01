package com.droiddevtips.nextgenexamples.screen.bannerAdExample.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BannerAdListViewState(
    val articles: List<BannerAdExampleDisplayItem> = emptyList()
) : Parcelable