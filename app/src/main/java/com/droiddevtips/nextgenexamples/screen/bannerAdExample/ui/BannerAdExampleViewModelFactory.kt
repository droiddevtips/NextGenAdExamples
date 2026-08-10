package com.droiddevtips.nextgenexamples.screen.bannerAdExample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.droiddevtips.nextgenexamples.ads.data.preloader.AdLoaderImpl

class BannerAdExampleViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

        if (modelClass.isAssignableFrom(BannerAdExampleViewModel::class.java)) {
            return BannerAdExampleViewModel(adLoader = AdLoaderImpl) as T
        }

        throw IllegalArgumentException("Not ${BannerAdExampleViewModel::class.simpleName} class")
    }
}