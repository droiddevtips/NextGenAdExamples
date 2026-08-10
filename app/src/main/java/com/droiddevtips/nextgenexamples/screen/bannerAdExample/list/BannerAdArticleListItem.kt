package com.droiddevtips.nextgenexamples.screen.bannerAdExample.list

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.droiddevtips.nextgenexamples.ads.data.preloader.AdLoaderImpl
import com.droiddevtips.nextgenexamples.screen.bannerAdExample.data.BannerAdExampleDisplayItem

@Composable
fun BannerAdArticleListItem(
    item: BannerAdExampleDisplayItem,
    modifier: Modifier = Modifier
) {
    when (item) {
        is BannerAdExampleDisplayItem.AdView -> {
            BannerAdView(item = item, modifier = modifier)
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

    val bannerAd = remember { mutableStateOf(AdLoaderImpl.loadBannerAd(item.key)) }
//    bannerAd.value = AdLoaderImpl.loadBannerAd(item.key)

    if (bannerAd.value == null)
        return


    bannerAd.value?.let { ad ->
        val activity = LocalActivity.current
        val isVisible = rememberSaveable { mutableStateOf(true) }
//        val loadBannerAd = remember { mutableStateOf(false) }

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
                        factory = { viewContext ->
                            Log.i("TAG12","")
                            Log.i("TAG12","Ad view factory with key: ${item.key} called!")

                            ad.getView(activity)

//                        AdView(viewContext).apply {
//                            bannerAd.value?.let {
//                                it.addEventCallback(key = item.key)
//                                it.addBannerAdRefreshCallback(key = item.key)
//                                registerBannerAd(it, activity)
//                                isVisible.value = true
//                            }
//                        }
                        },
                        update = { adView ->

                            Log.i("TAG12","Ad view update called for banner ad with key: ${item.key} - banner Ad -> ${bannerAd.value}")
                            /*
                            bannerAd.value?.let {
                                it.addEventCallback(key = item.key)
                                it.addBannerAdRefreshCallback(key = item.key)
                                adView.registerBannerAd(it, activity)
                                isVisible.value = true
                            }
                            */

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
    }



    /*
    LaunchedEffect(Unit) {

        return@LaunchedEffect

        if (bannerAd.value != null)
            return@LaunchedEffect
        Log.i("TAG12","Launch effect called! key: ${item.key} and loading banner ad")
        AdLoaderImpl.loadBannerAd(key = item.key)?.let { ad ->
            bannerAd.value = ad
            isVisible.value = true
        }

        return@LaunchedEffect

        if (!loadBannerAd.value)
            return@LaunchedEffect

        Log.i("TAG15","Launch effect called! key: ${item.key}")
        Log.i("TAG15","is In preview -> $isPreviewMode")

        Log.i("TAG15","Banner ad state -> ${bannerAd.value}")

        if (bannerAd.value == null) {
            Log.i("TAG15","Requesting banner.... for key: ${item.key}")

//            AppAdManager.getBannerAd(context = context, adUnit = item.adUnit) { requestBannerAd ->
//                Log.i("TAG15","Requested banner.... for key: ${item.key} -> $requestBannerAd")
//                Log.i("TAG15","Requested banner thread -> ${Thread.currentThread()}")
//
//               if (bannerAd.value == null) {
//                   bannerAd.value = requestBannerAd
//                   isVisible.value = bannerAd.value != null
//               }
//
//                Log.i("TAG15","Is visibile: ${isVisible.value}")
//            }

            //bannerAd.value = AppAdManager.getBannerAd(context = context, adUnit = item.adUnit)
            Log.i("TAG15","Banner ad response -> ${bannerAd.value}")
        } else {
            isVisible.value = true
        }


        //isVisible.value = bannerAd.value != null
        Log.i("TAG15","Ended")
        Log.i("TAG15","")
    }
    */

//    DisposableEffect(Unit) {
//        onDispose {
//            Log.i("TAG12","onDispose banner ad with key: ${item.key}")
//            bannerAd.value?.destroy()
//            bannerAd.value = null
//        }
//    }
}