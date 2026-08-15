package com.droiddevtips.nextgenexamples.googleAdsConsentManager

import com.google.android.ump.FormError

/**
 * Listener invoked when the consent gathering process has completed.
 *
 * This callback fires once the User Messaging Platform (UMP) SDK has finished
 * requesting the latest consent information and, if required, has presented
 * and dismissed a consent form to the user. It signals that the app can now
 * safely check the user's consent status and proceed with SDK initialization
 * (e.g. initializing the Google Mobile Ads SDK) or ad loading.
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
interface OnConsentGatheringCompleteListener {

    fun consentGatheringComplete(error: FormError?)

}