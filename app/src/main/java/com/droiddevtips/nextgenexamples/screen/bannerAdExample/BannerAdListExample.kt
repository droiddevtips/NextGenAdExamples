package com.droiddevtips.nextgenexamples.screen.bannerAdExample

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.droiddevtips.nextgenexamples.screen.bannerAdExample.data.BannerAdArticle
import com.droiddevtips.nextgenexamples.screen.bannerAdExample.ui.BannerAdListViewModel
import com.droiddevtips.nextgenexamples.screen.bannerAdExample.ui.BannerAdListViewModelFactory

@Composable
fun BannerAdListExample(
    modifier: Modifier = Modifier,
    viewModel: BannerAdListViewModel = viewModel(factory = BannerAdListViewModelFactory())
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    BannerAdArticleList(articles = viewState.articles, modifier = modifier)
}

@Composable
private fun BannerAdArticleList(articles: List<BannerAdArticle>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(items = articles, key = { it.id }) { article ->
            Column(modifier = Modifier.padding(all = 16.dp)) {
                Text(text = article.title, fontWeight = FontWeight.Bold)
                Text(text = article.summary, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            HorizontalDivider()
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