package com.droiddevtips.nextgenexamples.navigator.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NavigatorViewState(val selectedItem: Screen? = null): Parcelable
