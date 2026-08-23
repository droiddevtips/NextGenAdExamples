package com.droiddevtips.nextgenexamples.screen.interstitialAds

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.droiddevtips.appwindowsizeandorientationdetector.Device
import com.droiddevtips.appwindowsizeandorientationdetector.deviceDetectorCurrentWindowSize
import com.droiddevtips.nextgenexamples.navigator.data.Screen
import com.droiddevtips.nextgenexamples.screen.interstitialAds.grid.InterstitialAdsGridExample
import com.droiddevtips.nextgenexamples.screen.interstitialAds.ui.InterstitialAdsExampleViewModel

/**
 * The interstitial ads example composable
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
@Composable
fun InterstitialAdsExample(screen: Screen, modifier: Modifier = Modifier) {

    val windowSize = deviceDetectorCurrentWindowSize()

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

        val viewModel: InterstitialAdsExampleViewModel = viewModel()
        val viewState = viewModel.viewState.collectAsStateWithLifecycle()

        InterstitialAdsGridExample(
            viewState = viewState.value,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
    }
}
