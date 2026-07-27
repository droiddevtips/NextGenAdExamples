package com.droiddevtips.nextgenexamples.screen.nativeAdExample

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun NativeAdExample(modifier: Modifier = Modifier) {

    Box(modifier = modifier) {

        Text(text = "Native ad example", modifier = Modifier.align(alignment = Alignment.Center))

    }


}