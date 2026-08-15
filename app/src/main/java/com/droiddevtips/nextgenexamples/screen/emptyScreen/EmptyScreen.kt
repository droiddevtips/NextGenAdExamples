package com.droiddevtips.nextgenexamples.screen.emptyScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.droiddevtips.nextgenexamples.core.AppString
import com.droiddevtips.nextgenexamples.core.Drawable

/**
 * The list-detail detail view empty state composable
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
@Composable
fun EmptyScreen(modifier: Modifier = Modifier) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = Drawable.placeholder_icon),
            modifier = Modifier.size(66.dp),
            contentDescription = null,
        )

        Text(text = stringResource(id = AppString.no_item_selected), color = MaterialTheme.colorScheme.onPrimary)
    }
}