package com.droiddevtips.nextgenexamples.navigator.data

import android.os.Parcelable
import com.droiddevtips.nextgenexamples.navigator.ui.detailPane.data.Route
import kotlinx.parcelize.Parcelize

@Parcelize
data class NavigatorViewState(val selectedItem: Route? = null): Parcelable
