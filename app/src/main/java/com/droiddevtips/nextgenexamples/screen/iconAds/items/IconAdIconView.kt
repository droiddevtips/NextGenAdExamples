package com.droiddevtips.nextgenexamples.screen.iconAds.items

import android.view.ViewGroup
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.libraries.ads.mobile.sdk.iconad.IconAd
import com.google.android.libraries.ads.mobile.sdk.iconad.IconAdView
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.ShapeAppearanceModel

@Composable
fun IconAdIconView(
    modifier: Modifier = Modifier,
    iconAd: IconAd,
    iconAdView: IconAdView
) {

    val drawable = iconAd.icon.drawable ?: return

    // icon.scale is documented as "the ratio of pixels to dp" for this drawable, i.e.
    // scale = px / dp. The drawable's intrinsic size is in px, so dividing by scale gives
    // the exact size the SDK intends the icon to be displayed at, independent of the
    // device's own density (no LocalDensity conversion needed here).
    val scale = iconAd.icon.scale
    val iconWidthDp = ((drawable.intrinsicWidth/2.5f) / scale).dp
    val iconHeightDp = ((drawable.intrinsicHeight/2.5f) / scale).dp

    val density = LocalDensity.current

    AndroidView(
        modifier = modifier.size(width = iconWidthDp, height = iconHeightDp),
        factory = { context ->

            ShapeableImageView(context).apply {

                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )

                adjustViewBounds = true

                shapeAppearanceModel =
                    ShapeAppearanceModel.builder()
                        .setAllCornerSizes(with(density) { 8.dp.toPx() })
                        .build()

                setImageDrawable(drawable)
            }.also {
                iconAdView.iconView = it
            }

            /*
            ComposeView(context).apply {
                setContent {

                }
            }.also {
                iconAdView.iconView = it
            }
            */
        })
}