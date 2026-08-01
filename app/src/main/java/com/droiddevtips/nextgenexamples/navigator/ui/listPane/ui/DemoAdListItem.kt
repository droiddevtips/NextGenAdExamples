package com.droiddevtips.nextgenexamples.navigator.ui.listPane.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.droiddevtips.appwindowsizeandorientationdetector.Device
import com.droiddevtips.appwindowsizeandorientationdetector.deviceDetectorCurrentWindowSize
import com.droiddevtips.nextgenexamples.navigator.data.NavigatorViewState
import com.droiddevtips.nextgenexamples.navigator.data.Screen

@Composable
fun DemoAdListItem(
    viewState: State<NavigatorViewState>,
    item: Screen,
    modifier: Modifier = Modifier,
    onItemClicked: (Screen) -> Unit
) {

    val currentDeviceWindowSize = deviceDetectorCurrentWindowSize()

    Row(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = RoundedCornerShape(8.dp)
            ).then(
                if (currentDeviceWindowSize.device is Device.Tablet && viewState.value.selectedItem == item) {
                    Modifier.border(width = 2.dp, color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(8.dp))
                } else {
                    Modifier
                }
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() }, indication = ripple(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                )
            ) {
                onItemClicked(item)
            }
            .padding(all = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(80.dp)
        ) {
            Image(
                painter = painterResource(item.icon),
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .align(alignment = Alignment.Center)
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = item.title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(
                text = item.description,
                fontSize = 14.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}