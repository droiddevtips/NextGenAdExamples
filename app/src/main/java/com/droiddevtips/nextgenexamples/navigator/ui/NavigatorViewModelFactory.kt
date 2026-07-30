@file:Suppress("UNCHECKED_CAST")

package com.droiddevtips.nextgenexamples.navigator.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras

class NavigatorViewModelFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

        if (modelClass.isAssignableFrom(NavigatorViewModel::class.java)) {
            return NavigatorViewModel() as T
        }

        throw IllegalArgumentException("Not ${NavigatorViewModel::class.simpleName} class")
    }
}