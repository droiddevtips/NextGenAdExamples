package com.droiddevtips.nextgenexamples.screen.bannerAdExample.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * The banner ad article data model
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Parcelize
data class BannerAdArticle(
    val id: Int,
    val title: String,
    val summary: String
) : Parcelable