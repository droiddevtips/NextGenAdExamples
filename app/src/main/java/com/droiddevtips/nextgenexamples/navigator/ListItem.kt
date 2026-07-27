package com.droiddevtips.nextgenexamples.navigator

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListItem(
    val route: Route,
    val title: String,
    val subtitle: String
) : Parcelable
