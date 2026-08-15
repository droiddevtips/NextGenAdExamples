package com.droiddevtips.nextgenexamples.screen.bannerAdExample.grid

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.droiddevtips.nextgenexamples.screen.bannerAdExample.data.BannerAdExampleDisplayItem
import com.droiddevtips.nextgenexamples.screen.bannerAdExample.data.BannerAdExampleViewState

@Composable
fun BannerAdGridList(
    viewState: BannerAdExampleViewState,
    modifier: Modifier = Modifier
) {
    // Belgium -> webuildvalue.com
    // Amsterdam -> dutchreview.com
    // Pisa -> britannica.com
    // Eifel tower -> italia.it

    // Question:  In the 'fun BannerAdGridListItem' composable I want the GridBannerAdView to be display in a separated row since it is an AdView in the LazyVerticalGrid
    // LazyVerticalGrid's items() has a span parameter for exactly this — giving an item GridItemSpan(maxLineSpan) makes it occupy the full row width, which forces the grid to break onto a new row for it (and push subsequent items to the row after).
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Adaptive(minSize = 180.dp),
        contentPadding = PaddingValues(all = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = viewState.articles,
            key = { it.key },
            span = { item ->
                if (item is BannerAdExampleDisplayItem.AdView) {
                    GridItemSpan(maxLineSpan)
                } else {
                    GridItemSpan(1)
                }
            }
        ) { item ->
            BannerAdGridListItem(item = item, modifier = Modifier.fillMaxWidth())
        }
    }
}