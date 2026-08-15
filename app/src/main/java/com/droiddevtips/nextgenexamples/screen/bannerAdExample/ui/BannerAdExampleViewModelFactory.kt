@file:Suppress("UNCHECKED_CAST")

package com.droiddevtips.nextgenexamples.screen.bannerAdExample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.droiddevtips.nextgenexamples.ads.data.manager.AppAdManager

/**
 * Factory for creating instances of [BannerAdExampleViewModel].
 *
 * Since [BannerAdExampleViewModel] requires dependencies (such as [AdManager])
 * that cannot be resolved by the default no-argument [ViewModelProvider.Factory],
 * this factory manually constructs the ViewModel and injects the required
 * dependencies.
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
class BannerAdExampleViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

        if (modelClass.isAssignableFrom(BannerAdExampleViewModel::class.java)) {
            return BannerAdExampleViewModel(adManager = AppAdManager) as T
        }

        throw IllegalArgumentException("Not ${BannerAdExampleViewModel::class.simpleName} class")
    }
}