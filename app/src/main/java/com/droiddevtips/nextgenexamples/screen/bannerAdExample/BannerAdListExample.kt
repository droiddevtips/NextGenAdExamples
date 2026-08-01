package com.droiddevtips.nextgenexamples.screen.bannerAdExample

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.droiddevtips.nextgenexamples.screen.bannerAdExample.data.BannerAdExampleDisplayItem
import com.droiddevtips.nextgenexamples.screen.bannerAdExample.data.BannerAdListViewState
import com.droiddevtips.nextgenexamples.screen.bannerAdExample.ui.BannerAdArticleListItem

@Composable
fun BannerAdListExample(
    modifier: Modifier = Modifier,
    viewState: BannerAdListViewState
) {
    BannerAdArticleList(articles = viewState.articles, modifier = modifier)
}

@Composable
private fun BannerAdArticleList(articles: List<BannerAdExampleDisplayItem>, modifier: Modifier = Modifier) {
    if (articles.isNotEmpty()) {
        LazyColumn(modifier = modifier.fillMaxSize()) {
            items(items = articles, key = { it.key }) { displayItem ->
                BannerAdArticleListItem(item = displayItem, modifier = Modifier.fillMaxWidth())
            }
        }
    }
}

//@Preview
//@Composable
//private fun BannerAdListExamplePreview() {
//    DroidDevTipsTheme {
//        BannerAdArticleList(articles = dummyBannerAdArticles())
//    }
//}