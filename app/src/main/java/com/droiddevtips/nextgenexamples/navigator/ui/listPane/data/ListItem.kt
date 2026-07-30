package com.droiddevtips.nextgenexamples.navigator.ui.listPane.data

import android.os.Parcelable
import com.droiddevtips.nextgenexamples.navigator.ui.detailPane.data.Route
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListItem(
    val icon: Int,
    val route: Route,
    val title: String,
    val subtitle: String
) : Parcelable