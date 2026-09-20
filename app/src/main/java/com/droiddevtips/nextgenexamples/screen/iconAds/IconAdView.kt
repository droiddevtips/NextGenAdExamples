package com.droiddevtips.nextgenexamples.screen.iconAds

import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.droiddevtips.nextgenexamples.core.Drawable
import com.droiddevtips.nextgenexamples.screen.iconAds.items.AdIconCallToActionView
import com.droiddevtips.nextgenexamples.screen.iconAds.items.AdIconRatingView
import com.droiddevtips.nextgenexamples.screen.iconAds.items.IconAdHeadlines
import com.droiddevtips.nextgenexamples.screen.iconAds.items.IconAdIconView
import com.google.android.libraries.ads.mobile.sdk.common.AdChoicesPlacement
import com.google.android.libraries.ads.mobile.sdk.common.AdValue
import com.google.android.libraries.ads.mobile.sdk.common.LoadAdError
import com.google.android.libraries.ads.mobile.sdk.iconad.IconAd
import com.google.android.libraries.ads.mobile.sdk.iconad.IconAdEventCallback
import com.google.android.libraries.ads.mobile.sdk.iconad.IconAdLoadCallback
import com.google.android.libraries.ads.mobile.sdk.iconad.IconAdPlacement
import com.google.android.libraries.ads.mobile.sdk.iconad.IconAdRequest
import com.google.android.libraries.ads.mobile.sdk.iconad.IconAdView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Preview
@Composable
fun IconAdView(modifier: Modifier = Modifier) {

    val isPreviewMode = LocalInspectionMode.current
    if (isPreviewMode) {
        PreviewIconAdView(modifier = Modifier.fillMaxWidth().height(300.dp))
        return
    }

    val iconAd = remember { mutableStateOf<IconAd?>(null) }
    val density = LocalDensity.current
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var contentHeightPx by remember { mutableIntStateOf(0) }
    var contentWidthPx by remember { mutableIntStateOf(0) }

    iconAd.value?.let { icon_ad ->
        Column(modifier = modifier.padding(bottom = 10.dp)) {
            AndroidView(
                modifier = Modifier
                    .then(
                        if (contentHeightPx > 0)
                            Modifier.height(with(density) { contentHeightPx.toDp() })
                        else
                            Modifier.wrapContentHeight()
                    )
                    .then(
                        if (contentWidthPx > 0)
                            Modifier.width(contentWidthPx.dp)
                        else
                            Modifier.wrapContentWidth()
                    )
                    .onSizeChanged {
                        if (contentWidthPx == 0) {
                            contentWidthPx = it.width / 2
                        }
                    }
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color.White,
                                Color(0XFFA2CAF2),
                                Color.White
                            ), center = Offset.Unspecified,
                            radius = 800f
                        ),
                        shape = RoundedCornerShape(
                            topStart = 8.dp,
                            topEnd = 8.dp,
                            bottomStart = 8.dp
                        )
                    ),
                factory = { context ->

                    IconAdView(context).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                    }.also { iconAdView ->

                        attachIconAdComposableView(iconAdView) {

                            Column(
                                modifier = Modifier.onSizeChanged { contentHeightPx = it.height },
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                Spacer(modifier = Modifier.height(5.dp))

                                IconAdHeadlines(iconAd = icon_ad, iconAdView = iconAdView)

                                IconAdIconView(iconAd = icon_ad, iconAdView = iconAdView)

                                AdIconRatingView(iconAd = icon_ad, iconAdView = iconAdView)

                                AdIconCallToActionView(iconAd = icon_ad, iconAdView = iconAdView)

                                Spacer(modifier = Modifier.height(5.dp))

                            }
                        }

                        iconAdView.registerIconAd(icon_ad)
                    }
                })
        }
    }

    LaunchedEffect(Unit) {

        val iconAdRequest =
            IconAdRequest.Builder("ca-app-pub-3940256099942544/1476272466")
                .setAdChoicesPlacement(AdChoicesPlacement.BOTTOM_RIGHT)
                .setIconAdPlacement(
                    IconAdPlacement.BROWSER
                ).build()

        IconAd.load(iconAdRequest, object : IconAdLoadCallback {

            override fun onAdFailedToLoad(adError: LoadAdError) {
                super.onAdFailedToLoad(adError)
                scope.launch(Dispatchers.Main) {
                    Toast.makeText(context, "Icon ad failed to load, cause: ${adError.message}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onAdLoaded(ad: IconAd) {
                super.onAdLoaded(ad)
                scope.launch(Dispatchers.Main) {
                    Toast.makeText(context, "Icon ad loaded!", Toast.LENGTH_LONG).show()
                }
                iconAd.value?.destroy()
                iconAd.value = ad
                ad.adEventCallback = object : IconAdEventCallback {

                    override fun onAdImpression() {
                        super.onAdImpression()
                        scope.launch(Dispatchers.Main) {
                            Toast.makeText(context, "On ad impression", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onAdClicked() {
                        super.onAdClicked()
                        scope.launch(Dispatchers.Main) {
                            Toast.makeText(context, "On ad clicked", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onAdPaid(value: AdValue) {
                        super.onAdPaid(value)
                        scope.launch(Dispatchers.Main) {
                            Toast.makeText(context, "Paid: ${value.valueMicros} ${value.currencyCode}", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        })
    }

    DisposableEffect(Unit) {
        onDispose {
            iconAd.value?.destroy()
        }
    }
}

private fun attachIconAdComposableView(
    containerView: IconAdView,
    content: @Composable () -> Unit
) {

    val iconAdViewComposeView = ComposeView(containerView.context).apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
    iconAdViewComposeView.setContent(content)
    containerView.addView(iconAdViewComposeView)
}

@Composable
fun PreviewIconAdView(modifier: Modifier = Modifier) {
    Box(modifier = modifier.background(color = MaterialTheme.colorScheme.background)) {

        Image(
            painter = painterResource(id = Drawable.icon_ads),
            contentDescription = null,
            modifier = Modifier.align(alignment = Alignment.Center)
        )
    }
}