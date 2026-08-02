package com.droiddevtips.nextgenexamples.screen.bannerAdExample.ui

import android.util.Log
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.droiddevtips.nextgenexamples.ads.adManager.AppAdManager
import com.droiddevtips.nextgenexamples.screen.bannerAdExample.data.BannerAdExampleDisplayItem
import com.google.android.libraries.ads.mobile.sdk.banner.AdView
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAd
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAdEventCallback
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAdRefreshCallback
import com.google.android.libraries.ads.mobile.sdk.common.LoadAdError

@Composable
fun BannerAdArticleListItem(
    viewport: LayoutBoundsHolder,
    item: BannerAdExampleDisplayItem,
    modifier: Modifier = Modifier
) {
    when (item) {
        is BannerAdExampleDisplayItem.AdView -> {
            BannerAdView(item = item, viewport = viewport, modifier = modifier)
        }

        is BannerAdExampleDisplayItem.Article -> {
            Article(item = item, modifier = modifier)
        }
    }
}

@Composable
private fun Article(item: BannerAdExampleDisplayItem.Article, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(all = 8.dp)) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Image(
                painter = painterResource(id = item.icon),
                contentDescription = null,
                modifier = Modifier
                    .size(70.dp)
                    .padding(start = 8.dp)
            )

            Column(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .weight(1f)
            ) {
                Text(text = item.title, fontWeight = FontWeight.Bold)
                Text(
                    text = item.description,
                    fontSize = 12.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
            }
        }
        HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp))
    }
}

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
//    val scope = rememberCoroutineScope()

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
            .then(
                if (bannerAd != null) {
                    Modifier.padding(all = 0.dp)
                } else {
                    Modifier
                }
            )
    ) {

        AnimatedVisibility(
            visible = isVisible.value,
            enter = expandVertically(
                expandFrom = Alignment.Top
            ) + fadeIn(),
            exit = slideOutVertically(),
            modifier = Modifier.align(alignment = Alignment.Center)
        ) {

            /*
            AndroidView(factory = { context ->


                /*
                scope.launch {

                    if (bannerAd.value == null) {
                        bannerAd.value = AppAdManager.getBannerAd(context = context, adUnit = item.adUnit)
                        Log.i("TAG15","Banner ad response -> ${bannerAd.value}")
                    }

                    isVisible.value = bannerAd.value != null



                }

                isVisible.value = true

                if (bannerAd.value == null) {
                    bannerAd.value = AppAdManager.getBannerAd(context = context, adUnit = item.adUnit)
                    Log.i("TAG15","Banner ad response -> ${bannerAd.value}")
                }


                isVisible.value = bannerAd.value != null
                Log.i("TAG15","")


                AdView(context).apply {
                    registerBannerAd(bannerAd.value!!, activity)
                }
                */
            })
            */

            /*
            if (isPreviewMode) {
                Box(
                    modifier = Modifier
                        .align(alignment = Alignment.Center)
                        .size(300.dp)
                        .background(color = Color.Red)
                )
            } else {
                if (bannerAd.value != null && activity != null) {
                    AndroidView(factory = { context ->

                        isVisible.value = true

                        AdView(context).apply {
                            registerBannerAd(bannerAd.value!!, activity)
                        }
                    })
                }
            }
            */

            Log.i("TAG15","Android view banner Ad -> > ${bannerAd.value}")
            Log.i("TAG15","Android view activity -> ${activity}")

            if (activity != null) {
                AndroidView(
                    factory = { viewContext ->
                        Log.i("TAG15", "Android view factory called!")
                        AdView(viewContext)
                    },
                    update = { adView ->
                        Log.i("TAG15", "Android view update called!")
                        val ad = bannerAd.value
                        Log.i("TAG15", "Banner Ad -> ${ad}")
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