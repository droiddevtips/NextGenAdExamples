@file:OptIn(ExperimentalApi::class)

package com.droiddevtips.nextgenexamples.screen.swipeable_ad

import android.util.Log
import android.view.ViewGroup
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.droiddevtips.appwindowsizeandorientationdetector.Device
import com.droiddevtips.appwindowsizeandorientationdetector.deviceDetectorCurrentWindowSize
import com.droiddevtips.nextgenexamples.ads.data.manager.AppAdManager
import com.droiddevtips.nextgenexamples.navigator.data.Screen
import com.google.android.libraries.ads.mobile.sdk.common.ExperimentalApi

@Composable
fun SwipeableScreen(screen: Screen, modifier: Modifier = Modifier) {

    val activity = LocalActivity.current
    val windowSize = deviceDetectorCurrentWindowSize()
    val viewModel: ImageGalleryViewModel =
        viewModel(factory = ImageGalleryViewModelFactory(adManager = AppAdManager))
    val pagerState = rememberPagerState(pageCount = { viewModel.images.size })

    Scaffold(modifier = modifier, topBar = {
        if (windowSize.device is Device.Mobile) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    space = 8.dp,
                    alignment = Alignment.CenterHorizontally
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painter = painterResource(id = screen.icon),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )

                Text(text = screen.title, fontWeight = FontWeight.Bold)

            }
        }
    }) { paddingValues ->

        Column(
            modifier = Modifier.padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            HorizontalPager(
                state = pagerState,
                beyondViewportPageCount = 1,
                flingBehavior = PagerDefaults.flingBehavior(
                    state = pagerState,
                    snapPositionalThreshold = 0.25f
                )
            ) { page ->

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {

                    val item = viewModel.getImage(page = page)

                    Log.i("TAG35","Item -> $item")

                    when (item) {
                        is GalleryItem.Ad -> {
                            activity?.let {
                                AndroidView(
                                    modifier = Modifier.fillMaxSize(),
                                    factory = {
                                    item.swipeAd.getView(activity).also { view ->
                                        (view.parent as? ViewGroup)?.removeView(view)
                                    }
                                })
                            } ?: run {
                                Text("Page $page")
                            }
                        }

                        is GalleryItem.Image -> {
                            Image(
                                painter = painterResource(item.image),
                                modifier = Modifier.fillMaxWidth(),
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }
    }
}