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
import com.droiddevtips.nextgenexamples.screen.bannerAdExample.data.BannerAdExampleDisplayItem
import com.google.android.libraries.ads.mobile.sdk.banner.AdView

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
                            // Without this sometimes it might result in a blank screen
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