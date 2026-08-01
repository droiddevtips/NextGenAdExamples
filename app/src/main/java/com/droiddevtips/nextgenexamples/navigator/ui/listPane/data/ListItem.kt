package com.droiddevtips.nextgenexamples.navigator.ui.listPane.data

import android.os.Parcelable
import com.droiddevtips.nextgenexamples.navigator.data.Screen
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListItem(
    val icon: Int,
    val screen: Screen,
    val title: String,
    val subtitle: String
) : Parcelable