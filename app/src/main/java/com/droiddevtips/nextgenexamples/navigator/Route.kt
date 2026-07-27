package com.droiddevtips.nextgenexamples.navigator

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class Route: Parcelable {
    data object BannerAdExample: Route()
    data object IconAd: Route()
    data object InterstitialAds: Route()
    data object NativeAdExample: Route()
    data object NoItemSelected: Route()
    data object EmptyScreen: Route()
    data object RewardedInterstitialAdExample: Route()
}