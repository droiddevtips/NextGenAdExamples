@file:Suppress("UNCHECKED_CAST")

package com.droiddevtips.nextgenexamples.screen.bannerAdExample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras

class BannerAdListViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

        if (modelClass.isAssignableFrom(BannerAdListViewModel::class.java)) {
            return BannerAdListViewModel() as T
        }

        throw IllegalArgumentException("Not ${BannerAdListViewModel::class.simpleName} class")
    }
}