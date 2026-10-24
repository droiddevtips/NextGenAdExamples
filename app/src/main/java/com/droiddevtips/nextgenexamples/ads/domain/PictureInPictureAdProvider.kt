@file:OptIn(ExperimentalApi::class)

package com.droiddevtips.nextgenexamples.ads.domain

import com.droiddevtips.nextgenexamples.ads.domain.model.AdUnit
import com.google.android.libraries.ads.mobile.sdk.common.ExperimentalApi
import com.google.android.libraries.ads.mobile.sdk.pip.PictureInPictureAd

/**
 * Provides loading of Picture in Picture (PiP) ads using the Google Mobile Ads (GMA) Next-Gen SDK.
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
interface PictureInPictureAdProvider {

    suspend fun loadPictureInPictureAd(adUnit: AdUnit): PictureInPictureAd?

}