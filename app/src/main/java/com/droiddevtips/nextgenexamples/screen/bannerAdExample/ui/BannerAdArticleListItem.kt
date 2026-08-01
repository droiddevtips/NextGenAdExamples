package com.droiddevtips.nextgenexamples.screen.bannerAdExample.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.droiddevtips.nextgenexamples.screen.bannerAdExample.data.BannerAdExampleDisplayItem

@Composable
fun BannerAdArticleListItem(
    item: BannerAdExampleDisplayItem,
    modifier: Modifier = Modifier
) {
    when (item) {
        is BannerAdExampleDisplayItem.AdView -> {
            BannerAdView(item = item, modifier = modifier)
        }

        is BannerAdExampleDisplayItem.Article -> {
            Article(item = item, modifier = modifier)
        }
    }

//    Column(modifier = Modifier.padding(all = 16.dp)) {
//        Text(text = article.title, fontWeight = FontWeight.Bold)
//        Text(text = article.summary, color = MaterialTheme.colorScheme.onSurfaceVariant)
//    }
}

@Composable
private fun Article(item: BannerAdExampleDisplayItem.Article, modifier: Modifier = Modifier) {
    Column {
        Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {

            Image(
                painter = painterResource(id = item.icon),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .weight(1f)
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = item.title, fontWeight = FontWeight.Bold)
                Text(
                    text = item.description,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
            }
        }
        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
    }
}

@Composable
private fun BannerAdView(item: BannerAdExampleDisplayItem.AdView, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(300.dp)
            .background(color = Color.Red)
    )
}