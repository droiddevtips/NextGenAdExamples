package com.droiddevtips.nextgenexamples.screen.bannerAdExample.ui

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
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.droiddevtips.appwindowsizeandorientationdetector.Device
import com.droiddevtips.appwindowsizeandorientationdetector.deviceDetectorCurrentWindowSize
import com.droiddevtips.nextgenexamples.navigator.data.Screen
import com.droiddevtips.nextgenexamples.screen.bannerAdExample.list.BannerAdListExample
import com.droiddevtips.nextgenexamples.screen.bannerAdExample.data.bannerAdTabTitles
import kotlinx.coroutines.launch

@Composable
fun BannerAdExample(screen: Screen, modifier: Modifier = Modifier) {

    val windowSize = deviceDetectorCurrentWindowSize()

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

                Text(text = screen.title)

            }
        }
    }) { paddingValues ->

        val pagerState = rememberPagerState(pageCount = { bannerAdTabTitles.size })
        val coroutineScope = rememberCoroutineScope()

        Column(modifier = modifier.padding(paddingValues)) {
            PrimaryTabRow(
                selectedTabIndex = pagerState.currentPage,
                divider = {
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.secondaryContainer
                    )
                },
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
                    0 -> {
//                        val context = LocalContext.current.applicationContext
                        val viewModel: BannerAdExampleViewModel =
                            viewModel(factory = BannerAdExampleViewModelFactory())
                        val viewState = viewModel.viewState.collectAsStateWithLifecycle()

                        BannerAdListExample(
                            viewState = viewState.value,
                            modifier = Modifier.fillMaxSize(),
                            action = viewModel::performAction
                        )
                    }

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