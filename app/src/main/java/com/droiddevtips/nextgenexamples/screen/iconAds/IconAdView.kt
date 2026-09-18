package com.droiddevtips.nextgenexamples.screen.iconAds

import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.droiddevtips.nextgenexamples.core.AppColor
import com.droiddevtips.nextgenexamples.core.Drawable
import com.google.android.libraries.ads.mobile.sdk.common.AdChoicesPlacement
import com.google.android.libraries.ads.mobile.sdk.common.AdValue
import com.google.android.libraries.ads.mobile.sdk.common.FullScreenContentError
import com.google.android.libraries.ads.mobile.sdk.common.LoadAdError
import com.google.android.libraries.ads.mobile.sdk.iconad.IconAd
import com.google.android.libraries.ads.mobile.sdk.iconad.IconAdEventCallback
import com.google.android.libraries.ads.mobile.sdk.iconad.IconAdLoadCallback
import com.google.android.libraries.ads.mobile.sdk.iconad.IconAdPlacement
import com.google.android.libraries.ads.mobile.sdk.iconad.IconAdRequest
import com.google.android.libraries.ads.mobile.sdk.iconad.IconAdView
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.ShapeAppearanceModel

@Composable
fun IconAdView(modifier: Modifier = Modifier) {

    val iconAd = remember { mutableStateOf<IconAd?>(null) }
    val density = LocalDensity.current

    // The SDK's internal ad view container mutates its own child hierarchy (and can
    // relayout) asynchronously once registerIconAd() is called, so wrap_content on the
    // AndroidView can no longer be trusted to hug our actual content. Instead we measure
    // our own Compose content and pin the AndroidView to that exact height.
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
                    ).then(
                        if (contentWidthPx > 0)
                            Modifier.width(contentWidthPx.dp)
                        else
                            Modifier.wrapContentWidth()
                    ).onSizeChanged {
                        if (contentWidthPx == 0) {
                            contentWidthPx = it.width/2
                        }
                    }
                    .background(brush = Brush.radialGradient(colors = listOf(
                        Color.White,
                        Color.LightGray,
                        Color.White
                    ), center = Offset.Unspecified,
                        radius = 800f
                    ), shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp, bottomStart = 8.dp)),
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



        /*
        AndroidView(modifier = modifier.wrapContentHeight(), factory = { context ->

            FrameLayout(context).apply {

                IconAdView(context = context).apply {

                    ComposeView(context).apply {

                        setContent {

                            Column {

                                AdHeadlinesItem(headlines = icon_ad.headline)

                                icon_ad.icon.drawable?.let { icon ->

                                    AndroidView(
                                        modifier = Modifier.size(100.dp),
                                        factory = {

                                            ShapeableImageView(context).apply {

                                                layoutParams = ViewGroup.LayoutParams(
                                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                                    ViewGroup.LayoutParams.MATCH_PARENT,

                                                    )

                                                adjustViewBounds = true

                                                setBackgroundColor(android.graphics.Color.GREEN)

                                                shapeAppearanceModel =
                                                    ShapeAppearanceModel.builder()
                                                        .setAllCornerSizes(with(density) { 8.dp.toPx() })
                                                        .build()

                                                setImageDrawable(icon)
                                            }
                                        }
                                    )
                                }

                                icon_ad.starRating?.let { _rating ->
                                    AndroidView(factory = { context ->
                                        RatingBar(context).apply {
                                            numStars = 5
                                            stepSize = 0.5f
                                        }
                                    }, update = {
                                        it.rating = _rating.toFloat()
                                    })
                                }

                                icon_ad.callToAction?.let { cta ->
                                    Button(
                                        onClick = {

                                        },
                                        modifier = Modifier.background(
                                            color = colorResource(id = AppColor.google_ad_blue),
                                            shape = RoundedCornerShape(percent = 50)
                                        )
                                    ) {
                                        Text(text = cta)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        })
        */
    }

    /*

  IMPORTANT!!

  What line 130 does
  iconAdViewBinding.adCallToAction.text = iconAd.callToAction
  iconAd.callToAction is just a String asset from the ad response (e.g. "Install", "Shop Now"). This line only sets the label on your Button. It has zero effect on click behavior — a plain TextView/Button doesn't know about
  ads.

  Where the click behavior is actually wired up
  Two other lines in displayIconAd() do the real work:
  - iconAdView.callToActionView = iconAdViewBinding.adCallToAction (line 124) — registers that specific Button as the official CTA view.
  - iconAdView.registerIconAd(iconAd) (line 140) — hands the ad object to the SDK.

  I decompiled IconAdView.registerIconAd(); it does this:
  getNativeAdViewContainer().a(ad.internalNativeAd)  // internal ew1.a(InternalNativeAd)
  So the app never attaches an OnClickListener itself — the SDK's internal native-ad-view-container walks every registered asset view (CTA button, headline, icon, stars, and the outer container) and attaches its own click
  listeners to all of them, pointing back at Google's internal InternalNativeAd. Your Button carries no destination URL — it's just a hook the SDK grabbed.

  What happens when the button is tapped (from the obfuscated internals)
  I grepped the decompiled classes for click/URL-handling code and found the actual mechanism:
  - A class handling ?adurl= / &adurl query params — this is Google's classic click-macro pattern: the SDK fires a request to a Google ad-server click/tracking URL that has the real advertiser destination embedded as an adurl=
    parameter. That URL was delivered inside the ad response payload at load time — it's never exposed as a property on IconAd in your code.
  - A try_fallback_for_deep_link routine — the SDK first tries to open the destination as a deep link into an installed app.
  - Code using PackageManager.queryIntentActivities + Intent(Intent.ACTION_VIEW, uri) — used to check whether any installed app can actually handle that deep link.
  iconAd.callToAction is just a String asset from the ad response (e.g. "Install", "Shop Now"). This line only sets the label on your Button. It has zero effect on click behavior — a plain TextView/Button doesn't know about
  ads.

  Where the click behavior is actually wired up
  Two other lines in displayIconAd() do the real work:
  - iconAdView.callToActionView = iconAdViewBinding.adCallToAction (line 124) — registers that specific Button as the official CTA view.
  - iconAdView.registerIconAd(iconAd) (line 140) — hands the ad object to the SDK.

  I decompiled IconAdView.registerIconAd(); it does this:
  getNativeAdViewContainer().a(ad.internalNativeAd)  // internal ew1.a(InternalNativeAd)
  So the app never attaches an OnClickListener itself — the SDK's internal native-ad-view-container walks every registered asset view (CTA button, headline, icon, stars, and the outer container) and attaches its own click
  listeners to all of them, pointing back at Google's internal InternalNativeAd. Your Button carries no destination URL — it's just a hook the SDK grabbed.

  What happens when the button is tapped (from the obfuscated internals)
  I grepped the decompiled classes for click/URL-handling code and found the actual mechanism:
  - A class handling ?adurl= / &adurl query params — this is Google's classic click-macro pattern: the SDK fires a request to a Google ad-server click/tracking URL that has the real advertiser destination embedded as an adurl=
    parameter. That URL was delivered inside the ad response payload at load time — it's never exposed as a property on IconAd in your code.
  - A try_fallback_for_deep_link routine — the SDK first tries to open the destination as a deep link into an installed app.
  - Code using PackageManager.queryIntentActivities + Intent(Intent.ACTION_VIEW, uri) — used to check whether any installed app can actually handle that deep link.
  - A market://details?id=<package> fallback — if no app can handle the deep link (or it's a straightforward install ad), it opens the Play Store listing for the advertised app instead. Otherwise it just opens the resolved
    http(s) URL in a browser/custom tab.

  Along the way it fires onAdClicked() (your callback at IconAdFragment.kt:107) and reports the click back to Google's servers for attribution/billing, which is also what eventually can trigger onAdPaid().

  Key takeaway: the click-through URL is intentionally opaque to your app — it's resolved and executed entirely inside the closed-source SDK, using data from the ad response, specifically so publishers (or malware) can't read
  or tamper with it. You can influence which view is clickable, but not what URL it goes to or how it's opened.
  */

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

                Log.i("TAG35", "Icon ad failed to load, cause: ${adError.message}")

            }

            override fun onAdLoaded(ad: IconAd) {
                super.onAdLoaded(ad)

                Log.i("TAG35", "Icon ad -> $ad")

                iconAd.value?.destroy()
                iconAd.value = ad

                ad.adEventCallback = object : IconAdEventCallback {

                    override fun onAdShowedFullScreenContent() {
                        super.onAdShowedFullScreenContent()
                    }

                    override fun onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent()
                    }

                    override fun onAdFailedToShowFullScreenContent(fullScreenContentError: FullScreenContentError) {
                        super.onAdFailedToShowFullScreenContent(fullScreenContentError)
                    }

                    override fun onAdImpression() {
                        super.onAdImpression()
                    }

                    override fun onAdClicked() {
                        super.onAdClicked()
                    }

                    override fun onAdPaid(value: AdValue) {
                        super.onAdPaid(value)
                    }
                }

            }
        })


    }

}

@Composable
private fun IconAdHeadlines(
    modifier: Modifier = Modifier,
    iconAd: IconAd,
    iconAdView: IconAdView
) {
    AndroidView(factory = { context ->

        ComposeView(context).apply {

            setContent {
                Row(
                    modifier = modifier
                        .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                        .padding(end = 2.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(space = 2.dp)
                ) {
                    HeightLinkedLayout(
                        reference = {
                            Text(
                                iconAd.headline ?: "",
                                fontSize = 11.sp,
                                color = Color.DarkGray,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier
                                    .padding(start = 34.dp)
                                    .padding(end = 8.dp)
                                    .padding(bottom = 2.dp)
                            )
                        },
                        linked = {
                            Box(
                                modifier = Modifier
                                    .background(color = Color.Yellow, shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp))
                            ) {
                                Text(
                                    text = "Ad",
                                    style = TextStyle(
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    ),
                                    modifier = Modifier
                                        .align(alignment = Alignment.Center)
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    )
                }
            }
        }.also {
            iconAdView.headlineView = it
        }
    })
}

@Composable
private fun IconAdIconView(
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

@Composable
private fun AdIconRatingView(
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

@Composable
private fun AdIconCallToActionView(
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
                setTextColor(android.graphics.Color.WHITE)
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

//@Preview
//@Composable
//fun AdHeadlinesItem(
//    @PreviewParameter(HeadlineProvider::class) headlines: String?,
//    modifier: Modifier = Modifier
//) {
//    Row(
//        modifier = modifier
//            .background(color = Color.White)
//            .padding(end = 2.dp),
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.spacedBy(space = 2.dp)
//    ) {
//        HeightLinkedLayout(
//            reference = {
//                Text(
//                    headlines ?: "",
//                    fontSize = 11.sp,
//                    color = Color.LightGray,
//                    modifier = Modifier
//                        .padding(start = 26.dp)
//                        .padding(bottom = 2.dp)
//                )
//            },
//            linked = {
//                Box(
//                    modifier = Modifier
//                        .background(color = Color.Yellow)
//                ) {
//                    Text(
//                        text = "Ad",
//                        style = TextStyle(
//                            fontSize = 10.sp,
//                            fontWeight = FontWeight.Bold,
//                            color = Color.Black
//                        ),
//                        modifier = Modifier
//                            .align(alignment = Alignment.Center)
//                            .padding(horizontal = 4.dp, vertical = 4.dp)
//                    )
//                }
//            }
//        )
//    }
//}

@Composable
private fun HeightLinkedLayout(
    modifier: Modifier = Modifier,
    reference: @Composable () -> Unit,
    linked: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        contents = listOf(reference, linked)
    ) { (refMeasurables, linkedMeasurables), constraints ->
        val refPlaceable = refMeasurables.first().measure(constraints)
        val linkedPlaceable = linkedMeasurables.first().measure(
            constraints.copy(
                minHeight = refPlaceable.height,
                maxHeight = refPlaceable.height
            )
        )
        val width = maxOf(refPlaceable.width, linkedPlaceable.width)
        layout(width, refPlaceable.height) {
            refPlaceable.placeRelative(0, 0)
            linkedPlaceable.placeRelative(0, 0)
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

@Preview
@Composable
fun Test() {
    Row(
        modifier = Modifier
            .background(color = Color.White)
            .padding(end = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space = 2.dp)
    ) {

        Box(
            modifier = Modifier
                .size(15.dp)
                .background(color = Color.Yellow)
        ) {

            Text(
                text = "Ad",
                fontSize = 12.sp,
                modifier = Modifier.align(alignment = Alignment.Center)
            )
        }

        Text("Headlines", fontSize = 12.sp, color = Color.LightGray)
    }
}