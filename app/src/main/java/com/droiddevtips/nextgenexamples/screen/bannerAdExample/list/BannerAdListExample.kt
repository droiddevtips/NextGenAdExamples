package com.droiddevtips.nextgenexamples.screen.bannerAdExample.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.droiddevtips.nextgenexamples.screen.bannerAdExample.data.BannerAdExampleViewModelAction
import com.droiddevtips.nextgenexamples.screen.bannerAdExample.data.BannerAdExampleViewState
import com.droiddevtips.nextgenexamples.screen.bannerAdExample.data.BannerAdExampleDisplayItem

@Composable
fun BannerAdListExample(
    modifier: Modifier = Modifier,
    viewState: BannerAdExampleViewState,
    action: (BannerAdExampleViewModelAction) -> Unit
) {
    Box(modifier = modifier) {

        BannerAdArticleList(articles = viewState.articles, modifier = modifier, action = action)

        AnimatedVisibility(
            visible = viewState.isLoading,
            enter = fadeIn(animationSpec = tween(1000)),
            exit = fadeOut(animationSpec = tween(1000))
        ) {
            BannerAdListLoadingScreen(modifier = modifier)
        }
    }
}

@Composable
private fun BannerAdArticleList(
    articles: List<BannerAdExampleDisplayItem>,
    modifier: Modifier = Modifier,
    action: (BannerAdExampleViewModelAction) -> Unit
) {

    Scaffold(modifier = modifier.statusBarsPadding(), topBar = {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Using the deprecated BannerAd.getView()", fontSize = 12.sp)
        }
    }) { paddingValues ->

        LazyColumn(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            items(items = articles, key = { it.key }) { displayItem ->
                BannerAdArticleListItem(
                    item = displayItem,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            action(BannerAdExampleViewModelAction.DestroyAllBannerAds)
        }
    }
}