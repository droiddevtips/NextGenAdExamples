package com.droiddevtips.nextgenexamples.screen.bannerAdExample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droiddevtips.nextgenexamples.ads.domain.AdLoader
import com.droiddevtips.nextgenexamples.ads.domain.model.AdUnit
import com.droiddevtips.nextgenexamples.core.Drawable
import com.droiddevtips.nextgenexamples.screen.bannerAdExample.data.BannerAdExampleDisplayItem
import com.droiddevtips.nextgenexamples.screen.bannerAdExample.data.BannerAdExampleViewModelAction
import com.droiddevtips.nextgenexamples.screen.bannerAdExample.data.BannerAdExampleViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.seconds

class BannerAdExampleViewModel(
    private val adLoader: AdLoader
) : ViewModel() {

    private var bannerAdListCacheKeys = ArrayList<AdUnit>()
    private val _viewState = MutableStateFlow(BannerAdExampleViewState())
    val viewState: StateFlow<BannerAdExampleViewState> = _viewState.asStateFlow().onStart {
        loadBannerAdListItems()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000L), BannerAdExampleViewState())

    suspend fun loadBannerAdListItems() = withContext(Dispatchers.IO) {

        val itemList = ArrayList<BannerAdExampleDisplayItem>()

        (1..100).forEach { itemNumber ->

            itemList.apply {

                add(
                    BannerAdExampleDisplayItem.Article(
                        _key = itemNumber, // Article unique key
                        icon = Drawable.ads_icon,
                        title = "Article $itemNumber",
                        description = "This is a short summary for article $itemNumber"
                    )
                )

                if (itemNumber % 10 == 0) {
                    val bannerAdUnit = AdUnit.BannerAd(_key = "${2026 + itemNumber}")
                    cacheAdUnit(adUnit = bannerAdUnit)
                    add(
                        BannerAdExampleDisplayItem.AdView(
                            _adUnit = bannerAdUnit
                        )
                    )
                }
            }
        }

        withContext(Dispatchers.Main) {
            _viewState.update {
                it.copy(articles = itemList)
            }
            delay(2.seconds)
            _viewState.update {
                it.copy(isLoading = false)
            }
        }
    }

    fun performAction(action: BannerAdExampleViewModelAction) {
        when(action) {
            BannerAdExampleViewModelAction.DestroyAllBannerAds -> {
                clearBannerAdCache()
            }
        }
    }

    private fun cacheAdUnit(adUnit: AdUnit) {
        bannerAdListCacheKeys.add(adUnit)
        adLoader.preLoadBannerAd(
            adUnit = adUnit
        )
    }

    private fun clearBannerAdCache() {
        adLoader.removeAllCacheBannerAds(bannerAdListCacheKeys.toList())
        bannerAdListCacheKeys.clear()
    }
}