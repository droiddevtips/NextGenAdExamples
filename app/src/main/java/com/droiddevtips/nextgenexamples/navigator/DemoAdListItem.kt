package com.droiddevtips.nextgenexamples.navigator

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DemoAdListItem(
    item: ListItem,
    modifier: Modifier = Modifier,
    onItemClicked: (Route) -> Unit
) {
    Column(
        modifier
            .padding(all = 16.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() }, indication = ripple(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                )
            ) {
                onItemClicked(item.route)
            },
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = item.title, fontSize = 14.sp)
        Text(text = item.subtitle, fontSize = 14.sp)
    }
}