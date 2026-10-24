@file:OptIn(ExperimentalApi::class)

package com.droiddevtips.nextgenexamples.screen.pipAds.ui

import android.widget.Toast
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.droiddevtips.nextgenexamples.core.ObserveEvents
import com.droiddevtips.nextgenexamples.screen.pipAds.data.PipViewModelAction
import com.droiddevtips.nextgenexamples.screen.pipAds.data.UIEvent
import com.google.android.libraries.ads.mobile.sdk.common.ExperimentalApi

/**
 * The picture-in-picture ad example screen, composed of a collapsing 'Header', scrollable 'Body',
 * 'Toolbar' and animated 'Title'. It forwards the lifecycle events to the 'PipAdExampleViewModel'
 * and observes its 'UIEvents' to show the picture-in-picture ad or toast messages.
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
@Composable
fun PipAdExample(modifier: Modifier = Modifier) {


    val viewModel: PipAdExampleViewModel =
        viewModel(factory = PipAdExampleViewModelFactory())
    val lifecycleOwner = LocalLifecycleOwner.current

    val headerHeight = 275.dp
    val toolbarHeight = 56.dp
    val scrollState: ScrollState = rememberScrollState(0)
    val context = LocalContext.current
    val activity = LocalActivity.current

    Box(modifier = modifier) {
        Header(headerHeight = headerHeight, scroll = scrollState)
        Body(headerHeight = headerHeight, scroll = scrollState)
        Toolbar(toolbarHeight = toolbarHeight, headerHeight = headerHeight, scroll = scrollState)
        Title(scroll = scrollState, headerHeight = headerHeight, toolbarHeight = toolbarHeight)
    }

    ObserveEvents(viewModel.viewEvent) { event ->
        when(event) {
            UIEvent.ShowPipAd -> activity?.let { _activity ->
                viewModel.performAction(action = PipViewModelAction.ShowAd(activity = _activity))
            }
            is UIEvent.ShowToast -> {
                Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    DisposableEffect(lifecycleOwner) {

        val observer = LifecycleEventObserver { _, event ->
            viewModel.performAction(action = PipViewModelAction.LifeCycle(event = event))
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            viewModel.performAction(action = PipViewModelAction.OnCleared)
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}