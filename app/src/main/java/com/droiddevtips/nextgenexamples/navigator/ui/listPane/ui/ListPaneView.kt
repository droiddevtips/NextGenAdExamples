package com.droiddevtips.nextgenexamples.navigator.ui.listPane.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.droiddevtips.appwindowsizeandorientationdetector.Device
import com.droiddevtips.appwindowsizeandorientationdetector.deviceDetectorCurrentWindowSize
import com.droiddevtips.nextgenexamples.extensions.borderRight
import com.droiddevtips.nextgenexamples.navigator.data.NavigatorViewState
import com.droiddevtips.nextgenexamples.navigator.ui.detailPane.data.Route
import com.droiddevtips.nextgenexamples.navigator.ui.listPane.data.ListItem

@Composable
fun ListPaneView(
    viewState: State<NavigatorViewState>,
    itemList: List<ListItem>,
    modifier: Modifier = Modifier,
    onItemClicked: (Route) -> Unit
) {
    val windowSize = deviceDetectorCurrentWindowSize()

    LazyColumn(
        modifier = modifier.background(color = MaterialTheme.colorScheme.background).then(
            if (windowSize.device is Device.Tablet) {
                Modifier.borderRight(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.outlineVariant
                )
            } else {
                Modifier
            }
        ),
        contentPadding = PaddingValues(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(itemList) { item ->
            DemoAdListItem(
                viewState = viewState,
                item = item,
                modifier = Modifier.fillMaxWidth(),
                onItemClicked = onItemClicked
            )
        }
    }
}