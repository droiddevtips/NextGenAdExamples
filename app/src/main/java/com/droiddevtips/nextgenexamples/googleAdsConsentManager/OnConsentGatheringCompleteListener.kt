package com.droiddevtips.nextgenexamples.googleAdsConsentManager

import com.google.android.ump.FormError

interface OnConsentGatheringCompleteListener {

    fun consentGatheringComplete(error: FormError?)

}