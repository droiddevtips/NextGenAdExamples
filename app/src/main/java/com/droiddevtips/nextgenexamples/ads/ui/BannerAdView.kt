package com.droiddevtips.nextgenexamples.ads.ui

import android.util.Log
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutBoundsHolder
import androidx.compose.ui.layout.onVisibilityChanged
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.droiddevtips.nextgenexamples.ads.domain.AdLoader
import com.droiddevtips.nextgenexamples.extensions.addBannerAdRefreshCallback
import com.droiddevtips.nextgenexamples.extensions.addEventCallback
import com.droiddevtips.nextgenexamples.screen.bannerAdExample.data.BannerAdExampleDisplayItem
import com.google.android.libraries.ads.mobile.sdk.banner.AdView
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAd

@Composable
fun BannerAdView2(
    item: BannerAdExampleDisplayItem.AdView,
    adLoader: AdLoader,
    viewport: LayoutBoundsHolder,
    modifier: Modifier = Modifier
) {
    val isPreviewMode = LocalInspectionMode.current
    if (isPreviewMode) {

        Box(modifier = modifier) {
            Box(
                modifier = Modifier
                    .align(alignment = Alignment.Center)
                    .size(300.dp)
                    .background(color = Color.Red)
            )
        }
        return
    }

    //val context = LocalContext.current
    val activity = LocalActivity.current
    val isVisible = rememberSaveable { mutableStateOf(false) }
    val bannerAd = remember { mutableStateOf<BannerAd?>(null) }
    val loadBannerAd = remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .heightIn(min = 1.dp)
            .background(color = Color.Red)
            .onVisibilityChanged(
                minFractionVisible = 1.0f,
                minDurationMs = 500,
                viewportBounds = viewport
            ) { visible ->

                Log.i("TAG15","Visible ${item.key} -> $visible")

                if (visible)
                    loadBannerAd.value = true

            }
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
                    factory = { viewContext ->
                        AdView(viewContext)
                    },
                    update = { adView ->

                        bannerAd.value?.let {
                            it.addEventCallback(key = item.key)
                            it.addBannerAdRefreshCallback(key = item.key)
                            adView.registerBannerAd(it, activity)
                            isVisible.value = true
                        }

                        /*
                        val ad = bannerAd.value
                        if (ad != null) {
                            ad.adEventCallback = object : BannerAdEventCallback {
                                override fun onAdImpression() {
                                    super.onAdImpression()
                                    Log.i("TAG15","On ad impression")
                                }

                                override fun onAdClicked() {
                                    super.onAdClicked()
                                    Log.i("TAG15","On ad clicked")
                                }
                            }
                            ad.bannerAdRefreshCallback = object: BannerAdRefreshCallback {

                                override fun onAdRefreshed() {
                                    super.onAdRefreshed()
                                    Log.i("TAG15","On ad refreshed")
                                }

                                override fun onAdFailedToRefresh(adError: LoadAdError) {
                                    super.onAdFailedToRefresh(adError)
                                    Log.i("TAG15","On ad failed to refresh")
                                }

                            }
                            adView.registerBannerAd(ad, activity)
                            isVisible.value = true
                        }
                        */
                    },
                    modifier = Modifier.padding(all = 8.dp)
                )
            }
        }
    }

    LaunchedEffect(loadBannerAd.value) {

        if (!loadBannerAd.value)
            return@LaunchedEffect

        if (bannerAd.value != null)
            return@LaunchedEffect

//        AdLoaderImpl.loadBannerAd(key = item.key)?.let { ad ->
//            bannerAd.value = ad
//            isVisible.value = true
//        }

        /*
        Log.i("TAG15","Launch effect called! key: ${item.key}")
        Log.i("TAG15","is In preview -> $isPreviewMode")

        Log.i("TAG15","Banner ad state -> ${bannerAd.value}")

        if (bannerAd.value == null) {
            Log.i("TAG15","Requesting banner.... for key: ${item.key}")

            AppAdManager.getBannerAd(context = context, adUnit = item.adUnit) { requestBannerAd ->
                Log.i("TAG15","Requested banner.... for key: ${item.key} -> $requestBannerAd")
                Log.i("TAG15","Requested banner thread -> ${Thread.currentThread()}")

                if (bannerAd.value == null) {
                    bannerAd.value = requestBannerAd
                    isVisible.value = bannerAd.value != null
                }

                Log.i("TAG15","Is visibile: ${isVisible.value}")
            }

            //bannerAd.value = AppAdManager.getBannerAd(context = context, adUnit = item.adUnit)
            Log.i("TAG15","Banner ad response -> ${bannerAd.value}")
        } else {
            isVisible.value = true
        }
        */


        //isVisible.value = bannerAd.value != null
//        Log.i("TAG15","Ended")
//        Log.i("TAG15","")
    }
}

/*
@Composable
private fun BannerAdView(
    item: BannerAdExampleDisplayItem.AdView,
    viewport: LayoutBoundsHolder,
    modifier: Modifier = Modifier
) {
    Log.i("TAG15","")
    val isPreviewMode = LocalInspectionMode.current
    if (isPreviewMode) {

        Box(modifier = modifier) {
            Box(
                modifier = Modifier
                    .align(alignment = Alignment.Center)
                    .size(300.dp)
                    .background(color = Color.Red)
            )
        }
        return
    }

    val context = LocalContext.current
    val activity = LocalActivity.current
    val isVisible = rememberSaveable { mutableStateOf(false) }
    val bannerAd = remember { mutableStateOf<BannerAd?>(null) }
    val loadBannerAd = remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .heightIn(min = 1.dp)
            .background(color = Color.Red)
            .onVisibilityChanged(
                minFractionVisible = 1.0f,
                minDurationMs = 500,
                viewportBounds = viewport
            ) { visible ->

                Log.i("TAG15","Visible ${item.key} -> $visible")

                if (visible)
                    loadBannerAd.value = true

            }
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
                    factory = { viewContext ->
                        AdView(viewContext)
                    },
                    update = { adView ->
                        val ad = bannerAd.value
                        if (ad != null) {
                            ad.adEventCallback = object : BannerAdEventCallback {
                                override fun onAdImpression() {
                                    super.onAdImpression()
                                    Log.i("TAG15","On ad impression")
                                }

                                override fun onAdClicked() {
                                    super.onAdClicked()
                                    Log.i("TAG15","On ad clicked")
                                }
                            }
                            ad.bannerAdRefreshCallback = object: BannerAdRefreshCallback {

                                override fun onAdRefreshed() {
                                    super.onAdRefreshed()
                                    Log.i("TAG15","On ad refreshed")
                                }

                                override fun onAdFailedToRefresh(adError: LoadAdError) {
                                    super.onAdFailedToRefresh(adError)
                                    Log.i("TAG15","On ad failed to refresh")
                                }

                            }
                            adView.registerBannerAd(ad, activity)
                            isVisible.value = true
                        }
                    },
                    modifier = Modifier.padding(all = 8.dp)
                )
            }
        }
    }

    LaunchedEffect(loadBannerAd.value) {

        if (!loadBannerAd.value)
            return@LaunchedEffect

        Log.i("TAG15","Launch effect called! key: ${item.key}")
        Log.i("TAG15","is In preview -> $isPreviewMode")

        Log.i("TAG15","Banner ad state -> ${bannerAd.value}")

        if (bannerAd.value == null) {
            Log.i("TAG15","Requesting banner.... for key: ${item.key}")

            AppAdManager.getBannerAd(context = context, adUnit = item.adUnit) { requestBannerAd ->
                Log.i("TAG15","Requested banner.... for key: ${item.key} -> $requestBannerAd")
                Log.i("TAG15","Requested banner thread -> ${Thread.currentThread()}")

               if (bannerAd.value == null) {
                   bannerAd.value = requestBannerAd
                   isVisible.value = bannerAd.value != null
               }

                Log.i("TAG15","Is visibile: ${isVisible.value}")
            }

            //bannerAd.value = AppAdManager.getBannerAd(context = context, adUnit = item.adUnit)
            Log.i("TAG15","Banner ad response -> ${bannerAd.value}")
        } else {
            isVisible.value = true
        }


        //isVisible.value = bannerAd.value != null
        Log.i("TAG15","Ended")
        Log.i("TAG15","")
    }
}
*/