package com.droiddevtips.nextgenexamples.screen.bannerAdExample.grid

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.droiddevtips.nextgenexamples.ui.theme.DroidDevTipsTheme


@Composable
fun BannerAdGridLoadingScreen(visible: Boolean = true,modifier: Modifier = Modifier) {

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(durationMillis = 1000)),
        exit = fadeOut(animationSpec = tween(durationMillis = 1000))
    ) {
        Box(modifier = modifier.background(color = MaterialTheme.colorScheme.background)) {

            Column(modifier = Modifier.align(alignment = Alignment.Center)) {

                CircularProgressIndicator(modifier = Modifier
                    .size(80.dp)
                    .background(color = MaterialTheme.colorScheme.background), color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

@Preview(name = "Light", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewBannerAdGridLoadingScreen() {
    DroidDevTipsTheme {
        BannerAdGridLoadingScreen(modifier = Modifier.fillMaxSize())
    }
}