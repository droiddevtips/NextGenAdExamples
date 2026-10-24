@file:Suppress("UNCHECKED_CAST")

package com.droiddevtips.nextgenexamples.screen.pipAds.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.droiddevtips.nextgenexamples.ads.data.manager.AppAdManager
import com.droiddevtips.nextgenexamples.ads.domain.AdManager
import com.droiddevtips.nextgenexamples.screen.bannerAdExample.ui.BannerAdExampleViewModel

/**
 * The 'ViewModelProvider.Factory' responsible for creating the 'PipAdExampleViewModel'
 * with the provided 'AdManager'.
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
class PipAdExampleViewModelFactory(private val adManager: AdManager = AppAdManager): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

        if (modelClass.isAssignableFrom(PipAdExampleViewModel::class.java)) {
            return PipAdExampleViewModel(adManager = adManager) as T
        }

        throw IllegalArgumentException("Not ${BannerAdExampleViewModel::class.simpleName} class")
    }
}