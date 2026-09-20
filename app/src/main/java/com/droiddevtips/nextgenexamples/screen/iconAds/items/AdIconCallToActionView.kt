package com.droiddevtips.nextgenexamples.screen.iconAds.items

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.droiddevtips.nextgenexamples.core.AppColor
import com.google.android.libraries.ads.mobile.sdk.iconad.IconAd
import com.google.android.libraries.ads.mobile.sdk.iconad.IconAdView

@Composable
fun AdIconCallToActionView(
    modifier: Modifier = Modifier,
    iconAd: IconAd,
    iconAdView: IconAdView
) {
    if (iconAd.callToAction.isNullOrBlank())
        return

    AndroidView(
        modifier = modifier,
        factory = { context ->

            Button(context).apply {
                layoutParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                text = iconAd.callToAction
                setTextColor(Color.WHITE)
                val backgroundDrawable = GradientDrawable().apply {
                    shape = GradientDrawable.RECTANGLE
                    cornerRadius = 52f
                    setColor(context.getColor(AppColor.google_ad_blue))
                }
                background = backgroundDrawable
            }.also {
                iconAdView.callToActionView = it
            }
        })
}