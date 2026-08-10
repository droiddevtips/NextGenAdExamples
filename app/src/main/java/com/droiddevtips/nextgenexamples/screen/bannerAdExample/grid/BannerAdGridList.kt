package com.droiddevtips.nextgenexamples.screen.bannerAdExample.grid

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.droiddevtips.nextgenexamples.screen.bannerAdExample.data.BannerAdExampleViewState

@Composable
fun BannerAdGridList(
    viewState: BannerAdExampleViewState,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Adaptive(minSize = 180.dp)
    ) {
        items(viewState.articles) { item ->
            BannerAdGridListItem(item = item, modifier = Modifier.fillMaxWidth())
        }
    }
}