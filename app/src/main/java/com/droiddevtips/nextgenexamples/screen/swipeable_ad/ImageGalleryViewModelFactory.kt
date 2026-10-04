@file:Suppress("UNCHECKED_CAST")

package com.droiddevtips.nextgenexamples.screen.swipeable_ad

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras

/**
 * Factory for creating instances of [ImageGalleryViewModel].
 *
 * Since [ImageGalleryViewModel] requires dependencies (such as [AdManager])
 * that cannot be resolved by the default no-argument [ViewModelProvider.Factory],
 * this factory manually constructs the ViewModel and injects the required
 * dependencies.
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
class ImageGalleryViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

        if (modelClass.isAssignableFrom(ImageGalleryViewModel::class.java)) {
            return ImageGalleryViewModel() as T  // adManager = AppAdManager
        }

        throw IllegalArgumentException("Not ${ImageGalleryViewModel::class.simpleName} class")
    }
}