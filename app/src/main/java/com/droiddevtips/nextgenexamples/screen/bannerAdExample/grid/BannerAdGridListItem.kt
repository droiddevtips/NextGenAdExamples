package com.droiddevtips.nextgenexamples.screen.bannerAdExample.grid

import android.content.res.Configuration
import android.util.Log
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.droiddevtips.nextgenexamples.ads.data.preloader.AdLoaderImpl
import com.droiddevtips.nextgenexamples.ads.ui.BannerAdPreview
import com.droiddevtips.nextgenexamples.ads.ui.NoBannerAdPlaceholder
import com.droiddevtips.nextgenexamples.core.Drawable
import com.droiddevtips.nextgenexamples.extensions.addBannerAdRefreshCallback
import com.droiddevtips.nextgenexamples.extensions.addEventCallback
import com.droiddevtips.nextgenexamples.screen.bannerAdExample.data.BannerAdExampleDisplayItem
import com.droiddevtips.nextgenexamples.ui.theme.DroidDevTipsTheme
import com.google.android.libraries.ads.mobile.sdk.banner.AdView

@Composable
fun BannerAdGridListItem(
    item: BannerAdExampleDisplayItem,
    modifier: Modifier = Modifier
) {

    val isPreviewMode = LocalInspectionMode.current

    Log.i("TAG35","Banner ad grid list item -> ${item}")

    when(item) {
        is BannerAdExampleDisplayItem.AdView -> {
            GridBannerAdView(item = item, modifier = Modifier.fillMaxWidth().fillMaxHeight())
        }
        is BannerAdExampleDisplayItem.Article -> {

            Card(modifier = modifier) {

                Image(
                    painter = painterResource(id = item.icon),
                    contentDescription = null, modifier = Modifier.fillMaxWidth()
                        .height(120.dp)
                        .padding(horizontal = 8.dp)
                        .padding(top = 16.dp)
                        .padding(bottom = 8.dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(item.title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.inversePrimary)
                    Text(item.description, fontSize = 10.sp, color = MaterialTheme.colorScheme.inversePrimary, maxLines = 3, overflow = TextOverflow.Ellipsis)
                }

                Spacer(
                    modifier = Modifier.height(
                        30.dp
                    ).then(
                        if (isPreviewMode) {
                            Modifier
                        } else {
                            Modifier.weight(1f)
                        }
                    )
                )

            }

            return
            Column(
                modifier = modifier
                    .padding(all = 8.dp)
                    .border(width = 1.dp, color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(8.dp))
                ,
                verticalArrangement = Arrangement.spacedBy(
                    8.dp,
                    alignment = Alignment.CenterVertically
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(8.dp))

                Image(
                    painter = painterResource(id = Drawable.amsterdam),
                    contentDescription = null, modifier = Modifier.fillMaxWidth()
                        .height(120.dp)
                        .padding(horizontal = 8.dp))
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(120.dp)
//                        .padding(horizontal = 8.dp)
//                        .background(color = Color.Red)
//                ) {
//
//                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(item.title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text(item.description, fontSize = 10.sp)
                }

                Spacer(
                    modifier = Modifier.height(
                        30.dp
                    ).then(
                        if (isPreviewMode) {
                            Modifier
                        } else {
                            Modifier.weight(1f)
                        }
                    )
                )
            }
        }
    }
}

@Composable
private fun GridBannerAdView(
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

@Preview(name = "Light", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewBannerAdGridListItem() {

    DroidDevTipsTheme {
        BannerAdGridListItem(
            item = BannerAdExampleDisplayItem.Article(
                _key = 123,
                icon = Drawable.banner_ads,
                title = "Test title",
                description = "Test description"
            ), modifier = Modifier.width(128.dp)
        )
    }

}