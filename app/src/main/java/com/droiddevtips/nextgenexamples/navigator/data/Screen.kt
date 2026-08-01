package com.droiddevtips.nextgenexamples.navigator.data

import android.os.Parcelable
import com.droiddevtips.nextgenexamples.core.Drawable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class Screen(val title: String, val description: String, val icon: Int, val route: String):
    Parcelable {
    data object BannerAdExample: Screen(title = "Banner Ads", description = "Ads occupy a portion of an app's layout.", icon = Drawable.banner_ads, route = "banner_ad")
    data object IconAd: Screen(title = "Icon Ads", description = "Specialized ad format introduced", icon = Drawable.icon_ads, route = "icon_ad")
    data object InterstitialAds: Screen(title = "Interstitial ads", description = "Ads cover the full screen of their host app.", icon = Drawable.interstitial, route = "interstitial_ad")
    data object NativeAdExample: Screen(title = "Native ads", description = "Ads match the platform's native UI components.", icon = Drawable.native_ads, route = "native_ad")
    data object NoItemSelected: Screen(title = "", description = "", icon = Drawable.placeholder_icon, route = "no_item")
    data object EmptyScreen: Screen(title = "", description = "", icon = Drawable.placeholder_icon, route = "empty_item")
    data object RewardedInterstitialAdExample: Screen(title = "Rewarded Interstitial ads", description = "Rewarded interstitials offer rewards for ads appearance", icon = Drawable.rewarded_interstitial, route = "rewarded_interstitial_ad")
}