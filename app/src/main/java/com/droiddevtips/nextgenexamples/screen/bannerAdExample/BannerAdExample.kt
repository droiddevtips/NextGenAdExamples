package com.droiddevtips.nextgenexamples.screen.bannerAdExample

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun BannerAdExample(modifier: Modifier = Modifier) {

    Box(modifier = modifier) {

        Text(text = "Banner ad example", modifier = Modifier.align(alignment = Alignment.Center))

    }
}