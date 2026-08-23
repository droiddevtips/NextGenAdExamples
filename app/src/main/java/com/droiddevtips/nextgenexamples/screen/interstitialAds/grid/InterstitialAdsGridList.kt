package com.droiddevtips.nextgenexamples.screen.interstitialAds.grid

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.droiddevtips.nextgenexamples.screen.interstitialAds.data.InterstitialAdArticle
import com.droiddevtips.nextgenexamples.screen.interstitialAds.data.InterstitialAdsExampleViewState

/**
 * The interstitial ad grid list composable
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
@Composable
fun InterstitialAdsGridList(
    viewState: InterstitialAdsExampleViewState,
    modifier: Modifier = Modifier,
    onArticleClick: (InterstitialAdArticle) -> Unit = {}
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
            key = { it.key }
        ) { item ->
            InterstitialAdsGridListItem(
                item = item,
                modifier = Modifier.fillMaxWidth(),
                onClick = { onArticleClick(item) }
            )
        }
    }
}