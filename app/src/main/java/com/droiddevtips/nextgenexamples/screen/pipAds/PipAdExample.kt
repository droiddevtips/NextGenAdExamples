package com.droiddevtips.nextgenexamples.screen.pipAds

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PipAdExample(modifier: Modifier = Modifier) {

    val headerHeight = 275.dp
    val toolbarHeight = 56.dp
    val scrollState: ScrollState = rememberScrollState(0)

    Box(modifier = modifier) {
        Header(headerHeight = headerHeight, scroll = scrollState)
        Body(headerHeight = headerHeight, scroll = scrollState)
        Toolbar(toolbarHeight = toolbarHeight, headerHeight = headerHeight, scroll = scrollState)
        Title(scroll = scrollState, headerHeight = headerHeight, toolbarHeight = toolbarHeight)
    }
}