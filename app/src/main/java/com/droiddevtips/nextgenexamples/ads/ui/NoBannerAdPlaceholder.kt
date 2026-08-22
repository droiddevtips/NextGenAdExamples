package com.droiddevtips.nextgenexamples.ads.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.droiddevtips.nextgenexamples.core.AppString
import com.droiddevtips.nextgenexamples.core.Drawable

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
            Column(
                modifier = Modifier.align(alignment = Alignment.Center),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = painterResource(id = Drawable.adsense),
                    modifier = Modifier.size(100.dp),
                    contentDescription = null
                )

                Text(
                    text = stringResource(id = AppString.no_ads),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}