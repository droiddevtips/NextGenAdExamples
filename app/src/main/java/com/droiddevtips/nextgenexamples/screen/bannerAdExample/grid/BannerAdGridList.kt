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

/**
 * The banner ad grid list composable
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun BannerAdGridList(
    viewState: BannerAdExampleViewState,
    modifier: Modifier = Modifier
) {
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
                    GridItemSpan(maxLineSpan) // Display ads in a separated row
                } else {
                    GridItemSpan(1)
                }
            }
        ) { item ->
            BannerAdGridListItem(item = item, modifier = Modifier.fillMaxWidth())
        }
    }
}