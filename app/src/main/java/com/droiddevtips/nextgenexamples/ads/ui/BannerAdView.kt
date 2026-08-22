package com.droiddevtips.nextgenexamples.ads.ui

import androidx.activity.compose.LocalActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.droiddevtips.nextgenexamples.ads.domain.AdManager
import com.droiddevtips.nextgenexamples.extensions.addBannerAdRefreshCallback
import com.droiddevtips.nextgenexamples.extensions.addEventCallback
import com.droiddevtips.nextgenexamples.logging.data.LoggerImpl
import com.droiddevtips.nextgenexamples.logging.domain.LogLevel
import com.droiddevtips.nextgenexamples.screen.bannerAdExample.data.BannerAdExampleDisplayItem
import com.google.android.libraries.ads.mobile.sdk.banner.AdSize
import com.google.android.libraries.ads.mobile.sdk.banner.AdView
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAd
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAdEventCallback
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAdRefreshCallback
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAdRequest
import com.google.android.libraries.ads.mobile.sdk.common.AdLoadCallback
import com.google.android.libraries.ads.mobile.sdk.common.LoadAdError

/**
 * Load and displays the new [AdView] within the composition based on the preload ID provided.
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
@Composable
fun BannerAdView(
    item: BannerAdExampleDisplayItem.AdView,
    adManager: AdManager,
    modifier: Modifier = Modifier
) {
    val isPreviewMode = LocalInspectionMode.current
    if (isPreviewMode) {
        BannerAdPreview(modifier = modifier)
        return
    }

    val bannerAd = remember { mutableStateOf(adManager.getBannerAd(preLoaderID = item.key)) }

    if (bannerAd.value == null) {
        NoBannerAdPlaceholder(modifier = modifier)
        return
    }

    bannerAd.value?.let { ad ->
        val activity = LocalActivity.current
        val isVisible = rememberSaveable { mutableStateOf(true) }

        Box(
            modifier = modifier
        ) {

            AnimatedVisibility(
                visible = isVisible.value,
                enter = expandVertically(
                    expandFrom = Alignment.Top
                ) + fadeIn(),
                exit = slideOutVertically(),
                modifier = Modifier.align(alignment = Alignment.Center)
            ) {

                if (activity != null) {
                    AndroidView(
                        update = {
                            // Without this sometimes you might get a blank screen
                            it.requestLayout()
                        },
                        factory = { viewContext ->
                            AdView(viewContext).apply {
                                ad.let {
                                    it.addEventCallback(key = item.key)
                                    it.addBannerAdRefreshCallback(key = item.key)
                                    registerBannerAd(it, activity)
                                    isVisible.value = true
                                }
                            }
                        },
                        modifier = Modifier.padding(all = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun LoadBannerAdWithoutPreLoader(modifier: Modifier = Modifier) {

    val isPreviewMode = LocalInspectionMode.current
    if (isPreviewMode) {
        BannerAdPreview(modifier = modifier)
        return
    }

    val logger = remember { LoggerImpl("LoadBannerAdWithoutPreLoader composable") }
    val activity = LocalActivity.current
    if (activity != null) {
        AndroidView(
            update = {
                // Without this sometimes you might get a blank screen
                it.requestLayout()
            },
            factory = { viewContext ->

                val adLoadCallback = object : AdLoadCallback<BannerAd> {

                    override fun onAdLoaded(ad: BannerAd) {
                        super.onAdLoaded(ad)
                        logger.log(level = LogLevel.Info, message = "Banner ad successfully loaded!")
                        ad.let {

                            it.adEventCallback = object : BannerAdEventCallback {

                                override fun onAdImpression() {
                                    super.onAdImpression()
                                    logger.log(message = "On ad impression'")
                                }

                                override fun onAdClicked() {
                                    super.onAdClicked()
                                    logger.log(message = "On ad clicked!")
                                }
                            }

                            it.bannerAdRefreshCallback = object : BannerAdRefreshCallback {

                                override fun onAdRefreshed() {
                                    super.onAdRefreshed()
                                    logger.log(message = "on ad refreshed!")
                                }

                                override fun onAdFailedToRefresh(adError: LoadAdError) {
                                    super.onAdFailedToRefresh(adError)
                                    logger.log(message = "on ad failed to refresh")
                                }
                            }
                        }
                    }

                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        super.onAdFailedToLoad(adError)
                        logger.log(level = LogLevel.Error, message = "Unable to load banner ad, cause: ${adError.message}")
                    }
                }

                val adSize = AdSize.getCurrentOrientationInlineAdaptiveBannerAdSize(activity, 320)
                val adRequest = BannerAdRequest.Builder(adUnitId = "ca-app-pub-3940256099942544/9214589741", adSize).build()

                AdView(viewContext).apply { loadAd(adRequest,adLoadCallback) }
            },
            modifier = Modifier.padding(all = 8.dp)
        )
    }
}
