package com.droiddevtips.nextgenexamples.screen.iconAds

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.droiddevtips.appwindowsizeandorientationdetector.Device
import com.droiddevtips.appwindowsizeandorientationdetector.deviceDetectorCurrentWindowSize
import com.droiddevtips.nextgenexamples.core.AppColor
import com.droiddevtips.nextgenexamples.core.AppString
import com.droiddevtips.nextgenexamples.core.Drawable
import com.droiddevtips.nextgenexamples.navigator.data.Screen

/**
 * The icon ads example composable
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
@Composable
fun IconAdsExample(screen: Screen, modifier: Modifier = Modifier) {

    val windowSize = deviceDetectorCurrentWindowSize()
    val description = stringResource(id = AppString.icon_ad_text)

    val firstChar = description.take(1)
    val remainingText = description.drop(1)

    Scaffold(modifier = modifier, topBar = {
        if (windowSize.device is Device.Mobile) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    space = 8.dp,
                    alignment = Alignment.CenterHorizontally
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painter = painterResource(id = screen.icon),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )

                Text(text = screen.title)

            }
        }
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {

            Image(
                painter = painterResource(id = Drawable.google_ad_icon),
                contentDescription = null,
                modifier = Modifier.size(170.dp)
            )

            val annotatedString = buildAnnotatedString {

                withStyle(style = SpanStyle(
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = AppColor.google_ad_blue)
                )) {
                    append(firstChar)
                }

                append(remainingText)
            }

            Text(text = annotatedString, modifier = Modifier.padding(all = 16.dp))
        }
    }
}