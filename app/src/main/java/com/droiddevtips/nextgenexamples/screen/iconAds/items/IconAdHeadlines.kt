package com.droiddevtips.nextgenexamples.screen.iconAds.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.libraries.ads.mobile.sdk.iconad.IconAd
import com.google.android.libraries.ads.mobile.sdk.iconad.IconAdView
import kotlin.collections.component1
import kotlin.collections.component2

@Composable
fun IconAdHeadlines(
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
