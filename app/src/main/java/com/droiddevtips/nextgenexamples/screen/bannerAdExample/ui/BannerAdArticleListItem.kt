package com.droiddevtips.nextgenexamples.screen.bannerAdExample.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutBoundsHolder
import androidx.compose.ui.layout.onVisibilityChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.droiddevtips.nextgenexamples.screen.bannerAdExample.data.BannerAdExampleDisplayItem

@Composable
fun BannerAdArticleListItem(
    viewport: LayoutBoundsHolder,
    item: BannerAdExampleDisplayItem,
    modifier: Modifier = Modifier
) {
    when (item) {
        is BannerAdExampleDisplayItem.AdView -> {
            BannerAdView(item = item, viewport = viewport, modifier = modifier)
        }

        is BannerAdExampleDisplayItem.Article -> {
            Article(item = item, modifier = modifier)
        }
    }
}

@Composable
private fun Article(item: BannerAdExampleDisplayItem.Article, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(all = 8.dp)) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Image(
                painter = painterResource(id = item.icon),
                contentDescription = null,
                modifier = Modifier
                    .size(70.dp)
                    .padding(start = 8.dp)
            )

            Column(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .weight(1f)
            ) {
                Text(text = item.title, fontWeight = FontWeight.Bold)
                Text(
                    text = item.description,
                    fontSize = 12.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
            }
        }
        HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp))
    }
}

@Composable
private fun BannerAdView(
    item: BannerAdExampleDisplayItem.AdView,
    viewport: LayoutBoundsHolder,
    modifier: Modifier = Modifier
) {

    val isVisible = rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = modifier
            .onVisibilityChanged(
                minFractionVisible = 1.0f,
                minDurationMs = 2000,
                viewportBounds = viewport
            ) { visible ->

                if (visible)
                    isVisible.value = true

            }.padding(all = 8.dp)
    ) {
        AnimatedVisibility(
            visible = isVisible.value,
            enter = expandVertically(
                expandFrom = Alignment.Top
            ) + fadeIn(),
            exit = slideOutVertically(),
            modifier = Modifier.align(alignment = Alignment.Center)
        ) {
            Box(
                modifier = Modifier
                    .align(alignment = Alignment.Center)
                    .size(300.dp)
                    .background(color = Color.Red)
            )
        }
    }
}