package com.droiddevtips.nextgenexamples.screen.bannerAdExample.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * The banner ad example view state data model
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Parcelize
data class BannerAdExampleViewState(
    val isLoading: Boolean = true,
    val articles: List<BannerAdExampleDisplayItem> = emptyList()
) : Parcelable