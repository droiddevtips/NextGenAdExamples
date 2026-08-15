package com.droiddevtips.nextgenexamples.ads.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun BannerAdPreview(modifier: Modifier = Modifier) {

    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .size(300.dp)
                .background(color = Color.Red)
        ) {
            Text(
                text = "Preview",
                textAlign = TextAlign.Center,
                modifier = Modifier.align(alignment = Alignment.Center)
            )
        }
    }
}