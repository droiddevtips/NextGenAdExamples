package com.droiddevtips.nextgenexamples.screen.bannerAdExample.data

/**
 * The available banner ad example view model actions
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
sealed interface BannerAdExampleViewModelAction {
    data object DestroyAllBannerAds: BannerAdExampleViewModelAction
}