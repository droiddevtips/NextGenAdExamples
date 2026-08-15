package com.droiddevtips.nextgenexamples.googleAdsConsentManager

import android.app.Activity
import android.content.Context
import com.google.android.ump.ConsentDebugSettings
import com.google.android.ump.ConsentInformation
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.UserMessagingPlatform

/**
 * Manages user consent for personalized advertising in accordance with the
 * Google User Messaging Platform (UMP) SDK, handling GDPR, CCPA, and other
 * regional privacy regulations.
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
object GoogleAdsConsentManager {

    private var appContext: Context? = null
    private var consentInformation: ConsentInformation? = null

    fun init(context: Context) {
        appContext = context
        consentInformation = UserMessagingPlatform.getConsentInformation(context)
    }

    fun canRequestAds(): Boolean = consentInformation?.canRequestAds() ?: false

    fun gatherConsent(
        activity: Activity,
        listener: OnConsentGatheringCompleteListener
    ) {

        // GMA(BG) 4: Use RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("336FC3E40E0973941AFD72BAA204403F")) to get test ads on this device.
        val debugSettings =
            ConsentDebugSettings.Builder(activity)
                .addTestDeviceHashedId("336FC3E40E0973941AFD72BAA204403F")
                .build()

        val params =
            ConsentRequestParameters.Builder().setConsentDebugSettings(debugSettings).build()

        consentInformation?.requestConsentInfoUpdate(
            activity,
            params,
            {
                showConsentFormIfRequired(activity = activity, listener = listener)
            },
            { consentError ->
                listener.consentGatheringComplete(consentError)
            }
        )
    }

    private fun showConsentFormIfRequired(
        activity: Activity,
        listener: OnConsentGatheringCompleteListener
    ) {
        UserMessagingPlatform.loadAndShowConsentFormIfRequired(activity) { formError ->
            listener.consentGatheringComplete(error = formError)
        }
    }
}