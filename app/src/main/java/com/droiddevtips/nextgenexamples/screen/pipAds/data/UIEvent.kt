package com.droiddevtips.nextgenexamples.screen.pipAds.data

sealed interface UIEvent {
    data object ShowPipAd : UIEvent
    data class ShowToast(val message: String) : UIEvent
}