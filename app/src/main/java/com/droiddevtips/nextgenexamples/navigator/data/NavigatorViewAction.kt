package com.droiddevtips.nextgenexamples.navigator.data

/**
 * The actions accepted by the navigator view
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
sealed interface NavigatorViewAction {
    data class SetSelectedItem(val screen: Screen): NavigatorViewAction
}