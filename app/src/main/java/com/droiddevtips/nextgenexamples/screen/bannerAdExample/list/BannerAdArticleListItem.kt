package com.droiddevtips.nextgenexamples.screen.bannerAdExample.list

import androidx.activity.compose.LocalActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
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
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.droiddevtips.nextgenexamples.ads.data.preloader.AdLoaderImpl
import com.droiddevtips.nextgenexamples.ads.ui.BannerAdPreview
import com.droiddevtips.nextgenexamples.ads.ui.NoBannerAdPlaceholder
import com.droiddevtips.nextgenexamples.extensions.addBannerAdRefreshCallback
import com.droiddevtips.nextgenexamples.extensions.addEventCallback
import com.droiddevtips.nextgenexamples.screen.bannerAdExample.data.BannerAdExampleDisplayItem
import com.google.android.libraries.ads.mobile.sdk.banner.AdView

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
        BannerAdPreview(modifier = modifier)
        return
    }

    val bannerAd = remember { mutableStateOf(AdLoaderImpl.loadBannerAd(item.key)) }
//    bannerAd.value = AdLoaderImpl.loadBannerAd(item.key)

    if (bannerAd.value == null) {
        NoBannerAdPlaceholder(modifier = modifier)
        return
    }

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