package com.droiddevtips.nextgenexamples.navigator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ListPaneView(
    itemList: List<ListItem>,
    modifier: Modifier = Modifier,
    onItemClicked: (Route) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(itemList) { item ->
            DemoAdListItem(
                item = item,
                modifier = Modifier.fillMaxWidth(),
                onItemClicked = onItemClicked
            )
        }
    }
}