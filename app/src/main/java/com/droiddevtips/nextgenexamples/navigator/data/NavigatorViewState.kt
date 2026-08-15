package com.droiddevtips.nextgenexamples.navigator.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Navigator view state data model
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Parcelize
data class NavigatorViewState(val selectedItem: Screen? = null): Parcelable
