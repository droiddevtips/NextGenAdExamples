@file:OptIn(ExperimentalApi::class)

package com.droiddevtips.nextgenexamples.screen.pipAds.ui

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droiddevtips.nextgenexamples.ads.domain.AdManager
import com.droiddevtips.nextgenexamples.ads.domain.model.AdUnit
import com.droiddevtips.nextgenexamples.screen.pipAds.data.PipViewModelAction
import com.droiddevtips.nextgenexamples.screen.pipAds.data.UIEvent
import com.google.android.libraries.ads.mobile.sdk.common.AdValue
import com.google.android.libraries.ads.mobile.sdk.common.ExperimentalApi
import com.google.android.libraries.ads.mobile.sdk.pip.PictureInPictureAd
import com.google.android.libraries.ads.mobile.sdk.pip.PictureInPictureAdEventCallback
import com.google.android.libraries.ads.mobile.sdk.pip.PictureInPictureAdOptions.Builder
import com.google.android.libraries.ads.mobile.sdk.pip.PictureInPictureAdPosition
import com.google.android.libraries.ads.mobile.sdk.pip.PictureInPictureAdPresentationScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * The picture-in-picture ad example view model, responsible for loading, showing and destroying
 * the 'PictureInPictureAd' based on the lifecycle events received, and for sending 'UIEvent'
 * to the UI whenever an ad event occurs.
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
class PipAdExampleViewModel(private val adManager: AdManager): ViewModel() {

    private val _pipAd = MutableStateFlow<PictureInPictureAd?>(null)
    private val _viewEvent = Channel<UIEvent>()
    val viewEvent = _viewEvent.receiveAsFlow()

    private suspend fun loadPipAd() {
        val pipAd = adManager.loadPipAd(adUnit = AdUnit.PictureInPictureAd)
        _pipAd.update { pipAd }
        _pipAd.value?.let { pip_ad ->
            pip_ad.adEventCallback = object: PictureInPictureAdEventCallback {

                override fun onAdClicked() {
                    super.onAdClicked()
                    showToast(message = "Pip ad clicked")
                }

                override fun onAdHidden() {
                    super.onAdHidden()
                    destroyPipAds()
                    showToast(message = "Pip ad hidden")
                }

                override fun onAdImpression() {
                    super.onAdImpression()
                    showToast(message = "Pip ad Impression")
                }

                override fun onAdShown() {
                    super.onAdShown()
                    showToast(message = "Pip ad shown")
                }

                override fun onAdPaid(value: AdValue) {
                    super.onAdPaid(value)
                    showToast(message = "Paid: ${value.valueMicros} ${value.currencyCode}")
                }
            }

            _viewEvent.send(UIEvent.ShowPipAd)
        }?: run {
            _viewEvent.send(UIEvent.ShowToast("Failed to load pip ad"))
        }
    }

    private fun showToast(message:String) {
        viewModelScope.launch {
            _viewEvent.send(UIEvent.ShowToast(message))
        }
    }

    fun performAction(action: PipViewModelAction) {

        when(action) {
            is PipViewModelAction.LifeCycle -> when(action.event) {
                Lifecycle.Event.ON_RESUME -> {
                    if (_pipAd.value == null) {
                        viewModelScope.launch {
                            loadPipAd()
                        }
                    }
                }
                Lifecycle.Event.ON_STOP -> destroyPipAds()
                else -> Unit
            }

            is PipViewModelAction.ShowAd -> _pipAd.value?.let {
                val options =
                    Builder()
                        // SCREEN: The PiP ad persists globally across the application.
                        // APPLICATION: The PiP ad is scoped to a single screen (Activity).
                        .setPresentationScope(PictureInPictureAdPresentationScope.SCREEN)
                        // BOTTOM_LEFT: The bottom-left corner of the screen.
                        // BOTTOM_RIGHT: The bottom-right corner of the screen.
                        // TOP_LEFT: The top-left corner of the screen.
                        // TOP_RIGHT: The top-right corner of the screen.
                        // DEFAULT: Uses the SDK's default ad position.
                        .setPosition(PictureInPictureAdPosition.DEFAULT)
                        .build()

                adManager.showPiPAd(activity = action.activity, pipAd = it, options = options)
            }

            PipViewModelAction.OnCleared -> destroyPipAds()
        }
    }

    private fun destroyPipAds() {
        _pipAd.value?.destroy()
        _pipAd.update { null }
    }
}