@file:OptIn(ExperimentalApi::class)

package com.droiddevtips.nextgenexamples.screen.swipeable_ad.ui

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droiddevtips.nextgenexamples.ads.data.provider.SwipeableInterstitialAdProviderImpl
import com.droiddevtips.nextgenexamples.ads.domain.AdManager
import com.droiddevtips.nextgenexamples.ads.domain.model.AdUnit
import com.droiddevtips.nextgenexamples.core.Drawable
import com.droiddevtips.nextgenexamples.logging.data.LoggerImpl
import com.droiddevtips.nextgenexamples.logging.domain.LogLevel
import com.droiddevtips.nextgenexamples.logging.domain.Logger
import com.droiddevtips.nextgenexamples.screen.swipeable_ad.data.GalleryItem
import com.droiddevtips.nextgenexamples.screen.swipeable_ad.data.ImageGalleryViewModelAction
import com.droiddevtips.nextgenexamples.screen.swipeable_ad.data.SwipeableAdViewState
import com.google.android.libraries.ads.mobile.sdk.common.ExperimentalApi
import com.google.android.libraries.ads.mobile.sdk.swipeableinterstitial.SwipeableInterstitialAd
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.ConcurrentHashMap
import kotlin.time.Duration
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

    private val _swipeable_ad_screen_hold_remaining =
        MutableStateFlow(SwipeableInterstitialAdProviderImpl.SCREEN_HOLD_DURATION_IN_SECONDS)
    val swipeableAdScreenHoldRemaining: StateFlow<Int> =
        _swipeable_ad_screen_hold_remaining.asStateFlow()

    private var countdownTimerJob: Job? = null

    private val swipeAds = ConcurrentHashMap<Int, SwipeableInterstitialAd>()

    val adIndexes = listOf(
        1,
        3
    )

    private var loadSwipeAdJob: Job? = null

    fun performAction(action: ImageGalleryViewModelAction) {
        when (action) {
            is ImageGalleryViewModelAction.HoldSwipeableAdOnScreen -> viewModelScope.launch {
                holdAdOnScreen(ad = action.swipeableInterstitialAd)
            }

            is ImageGalleryViewModelAction.OnPageIndexChanged -> {
                if (action.index == 0) {
                    _swipeableViewState.update { it.copy(interceptOnBackPressed = false) }
                    log(level = LogLevel.Info, message = "On back pressed enabled")
                } else if (!_swipeableViewState.value.interceptOnBackPressed) {
                    log(level = LogLevel.Info, message = "On back pressed disabled")
                    _swipeableViewState.update { it.copy(interceptOnBackPressed = true) }
                }
            }

            ImageGalleryViewModelAction.OnCleared -> onCleared()

            is ImageGalleryViewModelAction.LifeCycle -> viewModelScope.launch {
                when(action.event) {

                    Lifecycle.Event.ON_CREATE -> {
                        startLoadingSwipeableAds()
                    }

                    else -> Unit
                }
            }

            is ImageGalleryViewModelAction.Log -> log(level = action.level, message = action.message)
        }
    }

    private suspend fun holdAdOnScreen(ad: SwipeableInterstitialAd) = withContext(Dispatchers.Main) {

        if (countdownTimerJob?.isActive == true)
            return@withContext

        _swipeableViewState.update { it.copy(userScrollEnabled = false) }

        val duration =
            if (ad.getScreenHoldDuration() == 0) SwipeableInterstitialAdProviderImpl.SCREEN_HOLD_DURATION_IN_SECONDS else ad.getScreenHoldDuration()

        log(level = LogLevel.Info, message = "Hold on screen started for $duration duration!")

        countdownTimerJob = launch {
            countdown(duration).onCompletion {
                _swipeableViewState.update {
                    it.copy(
                        userScrollEnabled = true
                    )
                }
                log(level = LogLevel.Info, message = "Hold on screen stopped!")
            }.collect {
                _swipeable_ad_screen_hold_remaining.value = it
            }
        }
    }

    private suspend fun startLoadingSwipeableAds() =
        withContext(Dispatchers.IO.limitedParallelism(1)) {

            loadSwipeAdJob = launch {

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

    private fun countdown(totalSeconds: Int, tickMillis: Duration = 1.seconds): Flow<Int> = flow {
        for (remaining in totalSeconds downTo 0) {
            emit(remaining)
            if (remaining > 0)
                delay(tickMillis)
        }
    }.flowOn(Dispatchers.Default)

    override fun onCleared() {
        log(level = LogLevel.Info, message = "On cleared called and swipe ads removed!")
        val keys = swipeAds.keys.map { it }.toList()
        keys.forEach {
            swipeAds.remove(it)?.destroy()
        }
    }
}