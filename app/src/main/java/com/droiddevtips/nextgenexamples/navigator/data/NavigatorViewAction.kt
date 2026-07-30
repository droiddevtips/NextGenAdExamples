package com.droiddevtips.nextgenexamples.navigator.data

import com.droiddevtips.nextgenexamples.navigator.ui.detailPane.data.Route

sealed interface NavigatorViewAction {
    data class SetSelectedItem(val route: Route): NavigatorViewAction
}