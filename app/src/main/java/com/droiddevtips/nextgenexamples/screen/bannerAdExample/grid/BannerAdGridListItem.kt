package com.droiddevtips.nextgenexamples.screen.bannerAdExample.grid

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.droiddevtips.nextgenexamples.core.Drawable
import com.droiddevtips.nextgenexamples.screen.bannerAdExample.data.BannerAdExampleDisplayItem
import com.droiddevtips.nextgenexamples.ui.theme.DroidDevTipsTheme

@Composable
fun BannerAdGridListItem(
    item: BannerAdExampleDisplayItem,
    modifier: Modifier = Modifier
) {

    if (item is BannerAdExampleDisplayItem.Article) {
        Column(
            modifier = modifier
                .padding(all = 8.dp)
                .background(color = Color.Blue),
            verticalArrangement = Arrangement.spacedBy(
                8.dp,
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .size(90.dp)
                    .background(color = Color.Red)
            ) {

            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(item.title, fontSize = 16.sp)
                Text(item.description, fontSize = 10.sp)
            }

            Spacer(modifier = Modifier.height(30
                .dp))
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