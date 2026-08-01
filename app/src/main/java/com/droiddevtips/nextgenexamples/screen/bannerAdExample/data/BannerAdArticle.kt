package com.droiddevtips.nextgenexamples.screen.bannerAdExample.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BannerAdArticle(
    val id: Int,
    val title: String,
    val summary: String
) : Parcelable