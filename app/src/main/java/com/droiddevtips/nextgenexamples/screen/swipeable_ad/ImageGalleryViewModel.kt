@file:OptIn(ExperimentalApi::class)

package com.droiddevtips.nextgenexamples.screen.swipeable_ad

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droiddevtips.nextgenexamples.ads.domain.AdManager
import com.droiddevtips.nextgenexamples.ads.domain.model.AdUnit
import com.droiddevtips.nextgenexamples.core.Drawable
import com.google.android.libraries.ads.mobile.sdk.common.ExperimentalApi
import com.google.android.libraries.ads.mobile.sdk.swipeableinterstitial.SwipeableInterstitialAd
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

/**
 * ViewModel responsible for managing the lifecycle and state of the banner ad examples
 * displayed within a Composable screen.
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
class ImageGalleryViewModel(private val adManager: AdManager) : ViewModel() {

    val images = listOf(
        Pair(Drawable.rome, "webuildvalue.com"),
        Pair(Drawable.atominium, "pexels.com"),
        Pair(Drawable.aruba1, ""),
        Pair(Drawable.aruba2, ""),
        Pair(Drawable.aruba3, ""),
    )

    private val swipeAds = mutableStateMapOf<Int, SwipeableInterstitialAd>()

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
        swipeAds[page]?.let { ad ->
            return GalleryItem.Ad(swipeAd = ad, image = image.first, credit = image.second)
        }

        return GalleryItem.Image(image = image.first, credit = image.second)
    }

    private fun startLoadingSwipeableAds() {

        swipeAdJob = viewModelScope.launch(Dispatchers.IO.limitedParallelism(1)) {

            val tasks = ArrayList<Deferred<SwipeableInterstitialAd?>>()

            adIndexes.forEach { index ->
                tasks.add(async {
                    adManager.loadSwipeableAd(adUnit = AdUnit.SwipeableInterstitialAd)?.also { ad ->
                        swipeAds[index] = ad
                    }
                })
            }

            tasks.awaitAll()
        }
    }

    override fun onCleared() {
        val keys = swipeAds.keys.map { it }.toList()
        keys.forEach {
            swipeAds.remove(it)?.destroy()
        }
    }
}