package com.droiddevtips.nextgenexamples.ads.data.loader

interface AdType {
    data class BannerAd(val preloadID:String): AdType
    data class InterstitialAd(val preloadID:String): AdType
}