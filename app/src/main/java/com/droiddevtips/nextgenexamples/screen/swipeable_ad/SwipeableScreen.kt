package com.droiddevtips.nextgenexamples.screen.swipeable_ad

import androidx.compose.foundation.Image
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.droiddevtips.appwindowsizeandorientationdetector.Device
import com.droiddevtips.appwindowsizeandorientationdetector.deviceDetectorCurrentWindowSize
import com.droiddevtips.nextgenexamples.navigator.data.Screen

@Composable
fun SwipeableScreen(screen: Screen, modifier: Modifier = Modifier) {

    val windowSize = deviceDetectorCurrentWindowSize()
    val viewModel: ImageGalleryViewModel =
        viewModel(factory = ImageGalleryViewModelFactory())
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

                Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {

                    val item = viewModel.getImage(page = page)

                    if (item is GalleryItem.Image) {
                        Image(
                            painter = painterResource(item.image),
                            modifier = Modifier.fillMaxWidth(),
                            contentDescription = null
                        )
                    } else {
                        Text("Page $page")
                    }
                }
            }
        }
    }
}