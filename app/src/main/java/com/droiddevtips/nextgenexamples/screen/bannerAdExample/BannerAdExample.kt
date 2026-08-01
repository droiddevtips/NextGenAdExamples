package com.droiddevtips.nextgenexamples.screen.bannerAdExample

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.droiddevtips.nextgenexamples.ui.theme.DroidDevTipsTheme
import kotlinx.coroutines.launch

private val bannerAdTabTitles = listOf("List", "Grid")

@Composable
fun BannerAdExample(modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState(pageCount = { bannerAdTabTitles.size })
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = modifier.fillMaxSize()) {
        PrimaryTabRow(
            selectedTabIndex = pagerState.currentPage,
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
            bannerAdTabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(page = index)
                        }
                    },
                    text = { Text(text = title) }
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