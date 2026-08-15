package com.droiddevtips.nextgenexamples.screen.bannerAdExample.grid

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.droiddevtips.nextgenexamples.ads.data.manager.AppAdManager
import com.droiddevtips.nextgenexamples.ads.ui.BannerAdView
import com.droiddevtips.nextgenexamples.core.Drawable
import com.droiddevtips.nextgenexamples.screen.bannerAdExample.data.BannerAdExampleDisplayItem
import com.droiddevtips.nextgenexamples.ui.theme.DroidDevTipsTheme

/**
 * Displays the items of the grid example within the vertical lazy 'LazyVerticalGrid'.
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
@Composable
fun BannerAdGridListItem(
    item: BannerAdExampleDisplayItem,
    modifier: Modifier = Modifier
) {

    val isPreviewMode = LocalInspectionMode.current

    when (item) {
        is BannerAdExampleDisplayItem.AdView -> {
            BannerAdView(
                item = item,
                adManager = AppAdManager,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            )
        }

        is BannerAdExampleDisplayItem.Article -> {

            Card(modifier = modifier) {

                Image(
                    painter = painterResource(id = item.icon),
                    contentDescription = null, modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .padding(horizontal = 8.dp)
                        .padding(top = 16.dp)
                        .padding(bottom = 8.dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        item.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.inversePrimary
                    )
                    Text(
                        item.description,
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.inversePrimary,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(
                    modifier = Modifier
                        .height(
                            30.dp
                        )
                        .then(
                            if (isPreviewMode) {
                                Modifier
                            } else {
                                Modifier.weight(1f)
                            }
                        )
                )
            }
        }
    }
}

@Preview(name = "Light", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewBannerAdGridListItem() {

    DroidDevTipsTheme {
        BannerAdGridListItem(
            item = BannerAdExampleDisplayItem.Article(
                _key = 123,
                icon = Drawable.banner_ads,
                title = "Test title",
                description = "Test description"
            ), modifier = Modifier.width(128.dp)
        )
    }
}