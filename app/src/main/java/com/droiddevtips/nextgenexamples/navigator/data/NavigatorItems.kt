package com.droiddevtips.nextgenexamples.navigator.data

import com.droiddevtips.nextgenexamples.core.Drawable
import com.droiddevtips.nextgenexamples.navigator.ui.detailPane.data.Route
import com.droiddevtips.nextgenexamples.navigator.ui.listPane.data.ListItem

val demoItems = listOf(
    ListItem(
        icon = Drawable.banner_ads,
        route = Route.BannerAdExample,
        title = "Banner Ads",
        subtitle = "Ads occupy a portion of an app's layout."
    ),
    ListItem(
        icon = Drawable.icon_ads,
        route = Route.IconAd,
        title = "Icon Ads",
        subtitle = "Specialized ad format introduced"
    ),
    ListItem(
        icon = Drawable.interstitial,
        route = Route.InterstitialAds,
        title = "Interstitial ads",
        subtitle = "Ads cover the full screen of their host app."
    ),
    ListItem(
        icon = Drawable.native_ads,
        route = Route.NativeAdExample,
        title = "Native ads",
        subtitle = "Ads match the platform's native UI components."
    ),
    ListItem(
        icon = Drawable.rewarded_interstitial,
        route = Route.RewardedInterstitialAdExample,
        title = "Rewarded Interstitial ads",
        subtitle = "Rewarded interstitials offer rewards for ads appearance"
    )
)