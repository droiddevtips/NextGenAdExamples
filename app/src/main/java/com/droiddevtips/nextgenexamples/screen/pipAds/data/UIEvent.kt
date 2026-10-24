package com.droiddevtips.nextgenexamples.screen.pipAds.data

/**
 * One-off UI events sent from the PipAdExampleViewModel
 * to the UI, such as requesting the picture-in-picture ad to be shown or displaying a toast message.
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
sealed interface UIEvent {
    data object ShowPipAd : UIEvent
    data class ShowToast(val message: String) : UIEvent
}