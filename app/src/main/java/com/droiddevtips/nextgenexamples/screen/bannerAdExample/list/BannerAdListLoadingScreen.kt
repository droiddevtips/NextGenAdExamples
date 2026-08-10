package com.droiddevtips.nextgenexamples.screen.bannerAdExample.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.droiddevtips.nextgenexamples.extensions.shimmerEffect

@Composable
fun BannerAdListLoadingScreen(modifier: Modifier = Modifier) {

    Column(modifier = modifier.background(color = MaterialTheme.colorScheme.background)) {

        val items = (1..30).toList()

        items.forEach { item ->
            ShimmerListItem(modifier = Modifier.fillMaxWidth())
        }
    }
}


@Composable
fun ShimmerListItem(modifier: Modifier = Modifier) {

    Column(modifier = modifier.padding(all = 8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .padding(start = 8.dp)
                    .shimmerEffect()
            )

            Column(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .shimmerEffect()
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .shimmerEffect()
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .padding(horizontal = 8.dp)
                .shimmerEffect()
        )
    }
}