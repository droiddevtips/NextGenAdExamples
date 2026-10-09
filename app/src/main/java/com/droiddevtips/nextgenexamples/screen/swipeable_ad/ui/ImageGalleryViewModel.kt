@file:OptIn(ExperimentalApi::class)

package com.droiddevtips.nextgenexamples.screen.swipeable_ad.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droiddevtips.nextgenexamples.ads.data.provider.SwipeableInterstitialAdProviderImpl
import com.droiddevtips.nextgenexamples.ads.domain.AdManager
import com.droiddevtips.nextgenexamples.ads.domain.model.AdUnit
import com.droiddevtips.nextgenexamples.core.Drawable
import com.droiddevtips.nextgenexamples.logging.data.LoggerImpl
import com.droiddevtips.nextgenexamples.logging.domain.LogLevel
import com.droiddevtips.nextgenexamples.logging.domain.Logger
import com.droiddevtips.nextgenexamples.screen.swipeable_ad.data.ImageGalleryViewModelAction
import com.droiddevtips.nextgenexamples.screen.swipeable_ad.data.SwipeableAdViewState
import com.droiddevtips.nextgenexamples.screen.swipeable_ad.data.GalleryItem
import com.google.android.libraries.ads.mobile.sdk.common.ExperimentalApi
import com.google.android.libraries.ads.mobile.sdk.swipeableinterstitial.SwipeableInterstitialAd
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.ConcurrentHashMap
import kotlin.time.Duration.Companion.seconds

/**
 * ViewModel responsible for managing the lifecycle and state of the banner ad examples
 * displayed within a Composable screen.
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
class ImageGalleryViewModel(private val adManager: AdManager) : ViewModel(), Logger by LoggerImpl(
    ImageGalleryViewModel::class.java.simpleName
) {

    val images = listOf(
        Pair(Drawable.rome, "webuildvalue.com"),
        Pair(Drawable.atominium, "pexels.com"),
        Pair(Drawable.aruba1, ""),
        Pair(Drawable.aruba2, ""),
        Pair(Drawable.aruba3, ""),
    )

    private val _swipeableViewState = MutableStateFlow(SwipeableAdViewState())
    val swipeableViewState: StateFlow<SwipeableAdViewState> = _swipeableViewState.asStateFlow()

    private val swipeAds = ConcurrentHashMap<Int, SwipeableInterstitialAd>()

    val adIndexes = listOf(
        1,
        3
    )

    private var swipeAdJob: Job? = null

    init {
        viewModelScope.launch {
            startLoadingSwipeableAds()
        }
    }

    fun performAction(action: ImageGalleryViewModelAction) {
        when(action) {
            is ImageGalleryViewModelAction.HoldSwipeableAdOnScreen -> viewModelScope.launch {
                holdAdOnScreen(ad = action.swipeableInterstitialAd)
            }
        }
    }

    private suspend fun holdAdOnScreen(ad: SwipeableInterstitialAd) {
        val duration = if (ad.getScreenHoldDuration() == 0) SwipeableInterstitialAdProviderImpl.SCREEN_HOLD_DURATION_IN_SECONDS else ad.getScreenHoldDuration()
        log(level = LogLevel.Info, message = "Hold on screen started for $duration duration!")
        _swipeableViewState.update { it.copy(userScrollEnabled = false) }
        delay(duration.seconds)
        _swipeableViewState.update { it.copy(userScrollEnabled = true) }
        log(level = LogLevel.Info, message = "Hold on screen stopped!")
    }

    private suspend fun startLoadingSwipeableAds() =
        withContext(Dispatchers.IO.limitedParallelism(1)) {

            swipeAdJob = launch {

                val tasks = ArrayList<Deferred<Pair<Int, SwipeableInterstitialAd?>>>()

                adIndexes.forEach { index ->
                    tasks.add(async {
                        val ad = adManager.loadSwipeableAd(adUnit = AdUnit.SwipeableInterstitialAd)
                        Pair(index, ad)
                    })
                }

                val result = tasks.awaitAll()

                result.forEach { pair ->
                    pair.second?.let {
                        log(
                            level = LogLevel.Info,
                            message = "Swipeable interstitial ad for index: ${pair.first} -> $it"
                        )
                        swipeAds[pair.first] = it
                    }
                }

                val displayItems: ArrayList<GalleryItem> = ArrayList()
                images.forEachIndexed { index, imagePair ->

                    displayItems.add(
                        GalleryItem.Image(
                            image = imagePair.first,
                            credit = imagePair.second
                        )
                    )

                    adIndexes.firstOrNull { it == index }?.let { adKey ->
                        swipeAds[index]?.let { swipeableInterstitialAd ->
                            displayItems.add(GalleryItem.Ad(swipeableInterstitialAd = swipeableInterstitialAd))
                        }
                    }
                }
                _swipeableViewState.update {
                    it.copy(
                        items = displayItems,
                        showLoadingScreen = false
                    )
                }
            }
        }

    override fun onCleared() {
        val keys = swipeAds.keys.map { it }.toList()
        keys.forEach {
            swipeAds.remove(it)?.destroy()
        }
    }
}