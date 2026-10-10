@file:OptIn(ExperimentalApi::class)

package com.droiddevtips.nextgenexamples.screen.swipeable_ad.ui

import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
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
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.droiddevtips.appwindowsizeandorientationdetector.Device
import com.droiddevtips.appwindowsizeandorientationdetector.deviceDetectorCurrentWindowSize
import com.droiddevtips.nextgenexamples.ads.data.manager.AppAdManager
import com.droiddevtips.nextgenexamples.navigator.data.Screen
import com.droiddevtips.nextgenexamples.screen.swipeable_ad.data.GalleryItem
import com.droiddevtips.nextgenexamples.screen.swipeable_ad.data.ImageGalleryViewModelAction
import com.droiddevtips.nextgenexamples.screen.swipeable_ad.data.SwipeableAdViewState
import com.droiddevtips.nextgenexamples.screen.swipeable_ad.domain.addEventCallback
import com.google.android.libraries.ads.mobile.sdk.common.ExperimentalApi

@Composable
fun SwipeableScreen(screen: Screen, modifier: Modifier = Modifier) {


    val viewModel: ImageGalleryViewModel =
        viewModel(factory = ImageGalleryViewModelFactory(adManager = AppAdManager))
    val lifecycleOwner = LocalLifecycleOwner.current
    val viewState = viewModel.swipeableViewState.collectAsStateWithLifecycle()
    val adRemainingCount = viewModel.swipeableAdScreenHoldRemaining.collectAsStateWithLifecycle()

    BackHandler(viewState.value.interceptOnBackPressed) { }

    Scaffold(modifier = modifier, topBar = {
        TopBarIcon(screen = screen)
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
                            is GalleryItem.Ad -> SwipeableAdView(
                                item = item,
                                adRemainingCount = adRemainingCount.value,
                                viewState = viewState.value,
                                action = viewModel::performAction
                            )

                            is GalleryItem.Image -> ImageView(item = item)
                        }
                    }
                }

                LaunchedEffect(pagerState.currentPage) {
                    viewModel.performAction(
                        action = ImageGalleryViewModelAction.OnPageIndexChanged(
                            pagerState.currentPage
                        )
                    )
                }
            }

            AnimateComposeVisibility(visible = viewState.value.showLoadingScreen) {
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

    DisposableEffect(lifecycleOwner) {

        val observer = LifecycleEventObserver { _, event ->
            viewModel.performAction(action = ImageGalleryViewModelAction.LifeCycle(event = event))
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            viewModel.performAction(action = ImageGalleryViewModelAction.OnCleared)
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}

@Composable
private fun TopBarIcon(
    screen: Screen,
    modifier: Modifier = Modifier
) {
    val windowSize = deviceDetectorCurrentWindowSize()

    if (windowSize.device is Device.Mobile) {
        Row(
            modifier = modifier.fillMaxWidth(),
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
}

@Composable
private fun SwipeableAdView(
    item: GalleryItem.Ad,
    adRemainingCount: Int,
    viewState: SwipeableAdViewState,
    modifier: Modifier = Modifier,
    action: (ImageGalleryViewModelAction) -> Unit
) {
    val activity = LocalActivity.current ?: return

    var adView by remember { mutableStateOf<View?>(null) }
    Box {
        AndroidView(
            modifier = modifier
                .fillMaxSize()
                .onVisibilityChanged { visible ->
                    if (visible) {
                        action(
                            ImageGalleryViewModelAction.HoldSwipeableAdOnScreen(
                                swipeableInterstitialAd = item.swipeableInterstitialAd
                            )
                        )
                    }
                },
            factory = {
                item.swipeableInterstitialAd.let {
                    it.addEventCallback(action = action)
                    it.getView(activity)
                        .also { view ->
                            (view.parent as? ViewGroup)?.removeView(view)
                            adView = view
                        }
                }
            })

        AnimateComposeVisibility(
            visible = !viewState.userScrollEnabled,
            modifier = Modifier.align(alignment = Alignment.TopEnd)
        ) {
            Row(
                modifier = Modifier
                    .padding(top = 25.dp, end = 12.dp)
                    .background(
                        color = Color.Black.copy(
                            alpha = 0.42f
                        ),
                        shape = RoundedCornerShape(size = 8.dp)
                    )
            ) {
                Text(
                    text = "Skip in $adRemainingCount sec",
                    modifier = Modifier.padding(horizontal = 8.dp),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun ImageView(item: GalleryItem.Image, modifier: Modifier = Modifier) {

    Box(
        modifier = modifier
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
                modifier = Modifier
                    .padding(all = 4.dp)
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

@Composable
private fun AnimateComposeVisibility(
    visible: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(modifier = modifier) {
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(animationSpec = tween(durationMillis = 1000)),
            exit = fadeOut(animationSpec = tween(durationMillis = 1000)),
        ) {
            content()
        }
    }
}
