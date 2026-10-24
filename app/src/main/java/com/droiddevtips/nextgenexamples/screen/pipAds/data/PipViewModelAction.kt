package com.droiddevtips.nextgenexamples.screen.pipAds.data

import android.app.Activity
import androidx.lifecycle.Lifecycle

/**
 * The actions the PipAdExampleViewModel can perform,
 * such as showing the loaded picture-in-picture ad, reacting to lifecycle events and releasing the ad.
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
sealed interface PipViewModelAction {
    data class ShowAd(val activity: Activity) : PipViewModelAction
    data class LifeCycle(val event: Lifecycle.Event) : PipViewModelAction
    data object OnCleared : PipViewModelAction
}