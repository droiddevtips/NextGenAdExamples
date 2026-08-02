package com.droiddevtips.nextgenexamples.googleAdsConsentManager

import android.app.Activity
import android.content.Context
import com.google.android.ump.ConsentDebugSettings
import com.google.android.ump.ConsentForm.OnConsentFormDismissedListener
import com.google.android.ump.ConsentInformation
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.UserMessagingPlatform

object GoogleAdsConsentManager {

    private var appContext: Context? = null
    private var consentInformation: ConsentInformation? = null

    fun init(context: Context) {
        appContext = context
        consentInformation = UserMessagingPlatform.getConsentInformation(context)
    }

    fun canRequestAds(): Boolean = consentInformation?.canRequestAds() ?: false

    fun isPrivacyOptionsRequired(): Boolean =
        consentInformation?.privacyOptionsRequirementStatus == ConsentInformation.PrivacyOptionsRequirementStatus.REQUIRED

    fun gatherConsent(
        activity: Activity,
        listener: OnConsentGatheringCompleteListener
    ) {

        // GMA(BG) 4: Use RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("336FC3E40E0973941AFD72BAA204403F")) to get test ads on this device.
        val debugSettings =
            ConsentDebugSettings.Builder(activity)
                // .setDebugGeography(ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_EEA)
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

    private fun showPrivacyOptionsForm(
        activity: Activity,
        onConsentFormDismissedListener: OnConsentFormDismissedListener
    ) {
        UserMessagingPlatform.showPrivacyOptionsForm(activity, onConsentFormDismissedListener)
    }
}