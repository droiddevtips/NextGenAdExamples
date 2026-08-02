package com.droiddevtips.nextgenexamples.ads.adLoader

import android.content.Context
import android.util.Log
import com.google.android.libraries.ads.mobile.sdk.banner.AdSize
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAd
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAdPreloader
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAdRequest
import com.google.android.libraries.ads.mobile.sdk.common.LoadAdError
import com.google.android.libraries.ads.mobile.sdk.common.PreloadCallback
import com.google.android.libraries.ads.mobile.sdk.common.PreloadConfiguration
import com.google.android.libraries.ads.mobile.sdk.common.ResponseInfo
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentLinkedQueue

object AdLoaderImplementation: AdLoader {

    // BannerAdPreloader keeps one shared, continuously-refilling ad pool per
    // preloadId, and BannerAdPreloader.start(...) must only ever be called
    // once per adUnit - it's an ongoing background session, not a one-shot
    // fetch. Every banner slot requesting the same adUnit while that session
    // is already running queues up here and is served in turn as onAdPreloaded
    // fires again, instead of each slot starting (and silently superseding)
    // its own session.
    private val cachedBannerAd = ConcurrentHashMap<String, BannerAd>()
    private val pendingListeners = ConcurrentHashMap<String, ConcurrentLinkedQueue<(BannerAd?) -> Unit>>()
    private val startedPreloadIds = ConcurrentHashMap.newKeySet<String>()

    override fun loadBannerAd(context: Context, adUnit: String, result: (BannerAd?) -> Unit) {

        val bannerAdPoll = BannerAdPreloader.pollAd(preloadId = adUnit)
        val cached = cachedBannerAd[adUnit]

        Log.i("TAG15", "Banner ad poll -> $bannerAdPoll, cached -> $cached")

        if (bannerAdPoll != null || cached != null) {
            result(bannerAdPoll ?: cached)
        }

        // The pool for this adUnit is empty right now (either we served the
        // caller from the stale cache above, or there was nothing to serve at
        // all) - make sure a preload session is running so it/we get topped up.
        if (bannerAdPoll == null) {
            val listener: ((BannerAd?) -> Unit)? = if (cached == null) result else null
            queueAndEnsurePreloadStarted(context = context, adUnit = adUnit, listener = listener)
        }
    }

    private fun queueAndEnsurePreloadStarted(
        context: Context,
        adUnit: String,
        listener: ((BannerAd?) -> Unit)?
    ) {
        listener?.let { pendingListeners.getOrPut(adUnit) { ConcurrentLinkedQueue() }.add(it) }

        if (!startedPreloadIds.add(adUnit)) {
            // A preload session is already running for this ad unit; this
            // caller (if it queued a listener above) will be served by that
            // session's callback below.
            return
        }

        preLoadBannerAd(context = context, adUnit = adUnit)
    }

    private fun preLoadBannerAd(context: Context, adUnit: String) {

        val preloadCallback = object: PreloadCallback {

            override fun onAdFailedToPreload(preloadId: String, adError: LoadAdError) {
                super.onAdFailedToPreload(preloadId, adError)
                Log.i("TAG15","Banner ad preload failed to load, cause: ${adError.message}")
                pendingListeners[preloadId]?.poll()?.invoke(null)
            }

            override fun onAdsExhausted(preloadId: String) {
                super.onAdsExhausted(preloadId)
                // onAdsExhausted is triggered when the final available preloaded
                // ad has been consumed or expired, meaning the local ad cache for
                // that configuration is completely empty
                Log.i("TAG15","Final available preloaded ad has been consumed for ID: $preloadId")
            }

            override fun onAdPreloaded(preloadId: String, responseInfo: ResponseInfo) {
                super.onAdPreloaded(preloadId, responseInfo)
                Log.i("TAG15","Ad preloaded for ID: $preloadId ")

                // Nobody is waiting for an ad right now - leave it in the pool
                // instead of draining it; the next loadBannerAd(...) call will
                // pick it up directly via BannerAdPreloader.pollAd.
                val listener = pendingListeners[preloadId]?.poll() ?: return

                val bannerAd = BannerAdPreloader.pollAd(preloadId = preloadId)
                Log.i("TAG15","New banner ad poll: $bannerAd ")
                bannerAd?.let { cachedBannerAd[preloadId] = it }
                listener(bannerAd)
            }
        }

        val adSize = AdSize.getCurrentOrientationInlineAdaptiveBannerAdSize(context, 320)
        val adRequest = BannerAdRequest.Builder(adUnitId = adUnit, adSize).build()
        val preload = PreloadConfiguration(adRequest)
        BannerAdPreloader.start(preloadId = adUnit, preloadConfiguration = preload, preloadCallback = preloadCallback)
    }

}