@file:OptIn(ExperimentalApi::class)

package com.droiddevtips.nextgenexamples.screen.swipeable_ad.ui

import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onVisibilityChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.droiddevtips.appwindowsizeandorientationdetector.Device
import com.droiddevtips.appwindowsizeandorientationdetector.deviceDetectorCurrentWindowSize
import com.droiddevtips.nextgenexamples.ads.data.manager.AppAdManager
import com.droiddevtips.nextgenexamples.navigator.data.Screen
import com.droiddevtips.nextgenexamples.screen.swipeable_ad.data.GalleryItem
import com.droiddevtips.nextgenexamples.screen.swipeable_ad.data.ImageGalleryViewModelAction
import com.google.android.libraries.ads.mobile.sdk.common.ExperimentalApi

@Composable
fun SwipeableScreen(screen: Screen, modifier: Modifier = Modifier) {

    val activity = LocalActivity.current ?: return
    val windowSize = deviceDetectorCurrentWindowSize()
    val viewModel: ImageGalleryViewModel =
        viewModel(factory = ImageGalleryViewModelFactory(adManager = AppAdManager))
    val viewState = viewModel.swipeableViewState.collectAsStateWithLifecycle()

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

        Box(
            modifier = Modifier.padding(paddingValues),
        ) {

            if (viewState.value.items.isNotEmpty()) {
                val pagerState = rememberPagerState(pageCount = { viewState.value.items.size })
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.align(alignment = Alignment.Center),
                    beyondViewportPageCount = 1,
                    userScrollEnabled = viewState.value.userScrollEnabled,
                    flingBehavior = PagerDefaults.flingBehavior(
                        state = pagerState,
                        snapPositionalThreshold = 0.25f
                    )
                ) { page ->

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center
                    ) {

                        when (val item = viewState.value.items[page]) {
                            is GalleryItem.Ad -> {
                                var adView by remember { mutableStateOf<View?>(null) }

                                AndroidView(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .onVisibilityChanged { visible ->
                                            // In case you are dealing with video ads toggle the visibility so the pager keeps this page attached off-screen; hide it so the ad pauses its video
                                            adView?.visibility =
                                                if (visible) View.VISIBLE else View.INVISIBLE
                                            if (visible) {
                                                viewModel.performAction(
                                                    action = ImageGalleryViewModelAction.HoldSwipeableAdOnScreen(
                                                        swipeableInterstitialAd = item.swipeableInterstitialAd
                                                    )
                                                )
                                            }
                                        },
                                    factory = {
                                        item.swipeableInterstitialAd.getView(activity)
                                            .also { view ->
                                                (view.parent as? ViewGroup)?.removeView(view)
                                                adView = view
                                            }
                                    })
                            }

                            is GalleryItem.Image -> {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(color = Color.Red)
                                ) {
                                    Image(
                                        painter = painterResource(item.image),
                                        modifier = Modifier.fillMaxWidth(),
                                        contentDescription = null
                                    )

                                    if (item.credit.isNotBlank()) {
                                        Row(
                                            modifier = Modifier.padding(all = 4.dp)
                                                .background(color = Color.Black.copy(alpha = 0.28f))
                                                .align(alignment = Alignment.BottomEnd)
                                        ) {
                                            Text(
                                                item.credit,
                                                color = Color.White,
                                                modifier = Modifier.padding(
                                                    horizontal = 4.dp
                                                )
                                            )
                                        }
                                    }
                                }

                            }
                        }
                    }
                }
            }

            AnimatedVisibility(
                visible = viewState.value.showLoadingScreen,
                enter = fadeIn(animationSpec = tween(durationMillis = 1000)),
                exit = fadeOut(animationSpec = tween(durationMillis = 1000))
            ) {
                Box(modifier = modifier.background(color = MaterialTheme.colorScheme.background)) {

                    Column(modifier = Modifier.align(alignment = Alignment.Center)) {

                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(80.dp)
                                .background(color = Color.Transparent),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}