package com.droiddevtips.nextgenexamples.screen.bannerAdExample

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
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.droiddevtips.appwindowsizeandorientationdetector.Device
import com.droiddevtips.appwindowsizeandorientationdetector.deviceDetectorCurrentWindowSize
import com.droiddevtips.nextgenexamples.core.AppString
import com.droiddevtips.nextgenexamples.core.Drawable
import com.droiddevtips.nextgenexamples.ui.theme.DroidDevTipsTheme
import kotlinx.coroutines.launch

private val bannerAdTabTitles = listOf(
    Tab(title = AppString.list, icon = Drawable.list_icon),
    Tab(title = AppString.grid, icon = Drawable.grid_icon)
)

data class Tab(val title: Int, val icon: Int)

@Composable
fun BannerAdExample(modifier: Modifier = Modifier) {

    val windowSize = deviceDetectorCurrentWindowSize()

    Scaffold(modifier = modifier, topBar = {
        if (windowSize.device is Device.Mobile) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painter = painterResource(id = Drawable.banner_ads),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )

                Text(text = "BannerAd example")

            }
        }
    }) { paddingValues ->

        val pagerState = rememberPagerState(pageCount = { bannerAdTabTitles.size })
        val coroutineScope = rememberCoroutineScope()

        Column(modifier = modifier.padding(paddingValues)) {
            PrimaryTabRow(
                selectedTabIndex = pagerState.currentPage,
                containerColor = MaterialTheme.colorScheme.background,
                indicator = {
                    TabRowDefaults.PrimaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(
                            pagerState.currentPage,
                            matchContentSize = false
                        ),
                        width = Dp.Unspecified
                    )
                }
            ) {
                bannerAdTabTitles.forEachIndexed { index, tabItem ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(page = index)
                            }
                        },
                        text = { Text(text = stringResource(id = tabItem.title)) },
                        icon = {
                            Image(
                                painter = painterResource(id = tabItem.icon),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                when (page) {
                    0 -> BannerAdListExample(modifier = Modifier.fillMaxSize())
                    1 -> BannerAdGridTab()
                }
            }
        }


    }


}

@Composable
private fun BannerAdGridTab(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Grid", color = MaterialTheme.colorScheme.onSurface)
    }
}

@Preview
@Composable
private fun BannerAdExamplePreview() {
    DroidDevTipsTheme {
        BannerAdExample()
    }
}