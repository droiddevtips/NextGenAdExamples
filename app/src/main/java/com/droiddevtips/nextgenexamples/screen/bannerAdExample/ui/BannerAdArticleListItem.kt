package com.droiddevtips.nextgenexamples.screen.bannerAdExample.ui

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
    val isPreviewMode = LocalInspectionMode.current
    val context = LocalContext.current
    val activity = LocalActivity.current
    val isVisible = rememberSaveable { mutableStateOf(false) }
    val bannerAd = remember { mutableStateOf<BannerAd?>(null) }
    val loadBannerAd = remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .onVisibilityChanged(
                minFractionVisible = 1.0f,
                minDurationMs = 2000,
                viewportBounds = viewport
            ) { visible ->

                if (visible)
                    loadBannerAd.value = true

            }
            .then(
                if (bannerAd != null) {
                    Modifier.padding(all = 8.dp)
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
                        AdView(context).apply {
                            registerBannerAd(bannerAd.value!!, activity)
                        }
                    })
                }
            }
        }
    }

    LaunchedEffect(loadBannerAd) {

        if (isPreviewMode)
            return@LaunchedEffect

        if (bannerAd.value == null)
            bannerAd.value = AppAdManager.getBannerAd(context = context, adUnit = item.adUnit)

        isVisible.value = bannerAd.value != null
    }
}