@file:OptIn(ExperimentalApi::class)

package com.droiddevtips.nextgenexamples.screen.swipeable_ad

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droiddevtips.nextgenexamples.ads.domain.AdManager
import com.droiddevtips.nextgenexamples.ads.domain.model.AdUnit
import com.droiddevtips.nextgenexamples.core.Drawable
import com.droiddevtips.nextgenexamples.screen.bannerAdExample.data.Article
import com.droiddevtips.nextgenexamples.screen.bannerAdExample.data.BannerAdExampleDisplayItem
import com.droiddevtips.nextgenexamples.screen.bannerAdExample.data.BannerAdExampleViewModelAction
import com.droiddevtips.nextgenexamples.screen.bannerAdExample.data.BannerAdExampleViewState
import com.google.android.libraries.ads.mobile.sdk.common.AdLoadCallback
import com.google.android.libraries.ads.mobile.sdk.common.ExperimentalApi
import com.google.android.libraries.ads.mobile.sdk.common.LoadAdError
import com.google.android.libraries.ads.mobile.sdk.swipeableinterstitial.SwipeableInterstitialAd
import com.google.android.libraries.ads.mobile.sdk.swipeableinterstitial.SwipeableInterstitialAdRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.seconds

/**
 * ViewModel responsible for managing the lifecycle and state of the banner ad examples
 * displayed within a Composable screen.
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
// private val adManager: AdManager
class ImageGalleryViewModel(
) : ViewModel() {

    companion object {
        private val SWIPEABLE_AD_UNIT_ID = "ca-app-pub-3940256099942544/3763914985"
    }

    val images = listOf(
        Pair(Drawable.rome, "webuildvalue.com"),
        Pair(Drawable.atominium, "pexels.com"),
        Pair(Drawable.aruba1, ""),
        Pair(Drawable.aruba2, ""),
        Pair(Drawable.aruba3, ""),
    )

    private val swipeAd: ArrayList<SwipeableInterstitialAd> = ArrayList()

    val adIndexes = arrayListOf(
        1,
        3
    )

    private var swipeAdJob: Job? = null

    init {
        startLoadingSwipeableAds()
    }

    fun getImage(page:Int): GalleryItem {

        if (page > images.lastIndex || page < 0) {
            val firstImage = images[0]
            return GalleryItem.Image(image = firstImage.first, credit = firstImage.second)
        }

        val image = images[page]
        val adIndex = adIndexes.indexOf(page)

        if (adIndex != -1) {

            swipeAd.firstOrNull()?.let { _swipeAd ->
                //val imageTest = images[page]
                return GalleryItem.Ad(swipeAd = _swipeAd, image = image.first, credit = image.second)
            }

            //return GalleryItem.Ad()
        }


        return GalleryItem.Image(image = image.first, credit = image.second)
    }

    private fun startLoadingSwipeableAds() {
        swipeAdJob = viewModelScope.launch {
            val requestBuilder = SwipeableInterstitialAdRequest.Builder(SWIPEABLE_AD_UNIT_ID)
                .setMaxScreenHoldDurationSeconds(5)
            SwipeableInterstitialAd.load(
                requestBuilder.build(),
                object : AdLoadCallback<SwipeableInterstitialAd> {
                    override fun onAdLoaded(ad: SwipeableInterstitialAd) {
                        super.onAdLoaded(ad)
                        Log.i("TAG35","Swipeable ad loaded!")
//                        swipeAdJob?.cancelChildren()
                        swipeAd.add(ad)
                    }

                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        super.onAdFailedToLoad(adError)
                        Log.i("TAG35","Error swipeable ad, cause: ${adError.message}")
                    }
                }
            )
        }
    }

    override fun onCleared() {
        super.onCleared()

    }

}