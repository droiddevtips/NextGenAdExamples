package com.droiddevtips.nextgenexamples.ads.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.droiddevtips.nextgenexamples.core.AppString

/**
 * Displays a placeholder in place of an ad when no ad is currently available to show.
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
@Composable
fun NoBannerAdPlaceholder(modifier: Modifier = Modifier) {

    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .size(300.dp)
                .background(color = MaterialTheme.colorScheme.secondaryContainer)
        ) {
            Text(
                text = stringResource(id = AppString.no_ads),
                textAlign = TextAlign.Center,
                modifier = Modifier.align(alignment = Alignment.Center)
            )
        }
    }
}