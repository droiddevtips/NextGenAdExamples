package com.droiddevtips.nextgenexamples.screen.iconAds.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.droiddevtips.nextgenexamples.core.Drawable
import com.google.android.libraries.ads.mobile.sdk.iconad.IconAd
import com.google.android.libraries.ads.mobile.sdk.iconad.IconAdView

@Composable
fun AdIconRatingView(
    modifier: Modifier = Modifier,
    iconAd: IconAd,
    iconAdView: IconAdView
) {
    val starRating = iconAd.starRating ?: return

    AndroidView(
        modifier = modifier,
        factory = { context ->
            ComposeView(context).apply {
                setContent {
                    StarRatingRow(rating = starRating)
                }
            }.also {
                iconAdView.starRatingView = it
            }
        })
}

@Composable
private fun StarRatingRow(
    rating: Double,
    starSize: Dp = 20.dp
) {
    Row(horizontalArrangement = Arrangement.spacedBy(1.dp)) {
        repeat(5) { index ->
            val starValue = rating - index
            val icon = when {
                starValue >= 1.0 -> Drawable.full_star
                starValue >= 0.5 -> Drawable.half_star
                else -> Drawable.star_border
            }
            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(starSize)
            )
        }
    }
}