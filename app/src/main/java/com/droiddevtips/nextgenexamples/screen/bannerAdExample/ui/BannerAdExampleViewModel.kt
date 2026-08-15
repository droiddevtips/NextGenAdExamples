package com.droiddevtips.nextgenexamples.screen.bannerAdExample.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droiddevtips.nextgenexamples.ads.domain.AdLoader
import com.droiddevtips.nextgenexamples.ads.domain.model.AdUnit
import com.droiddevtips.nextgenexamples.core.Drawable
import com.droiddevtips.nextgenexamples.screen.bannerAdExample.data.Article
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

                val article = dummyArticles.random()
                add(
                    BannerAdExampleDisplayItem.Article(
                        _key = itemNumber, // Article unique key
                        icon = article.featureImage,
                        title = article.title,
                        description = article.description
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
            Log.i("TAG12","data loaded, state: ${viewState.value}")
        }
    }

    fun performAction(action: BannerAdExampleViewModelAction) {
        when(action) {
            BannerAdExampleViewModelAction.DestroyAllBannerAds -> {
                clearBannerAdCache()
            }
        }
    }

    private val dummyArticles = listOf<Article>(
        Article(featureImage = Drawable.paris, title = "The Eiffel Tower", description = "The Eiffel Tower (French: Tour Eiffel) is a wrought-iron lattice tower located on the Champ de Mars in Paris, France. Widely recognized as the ultimate symbol of Paris and a global cultural icon of France, it is one of the most-visited monuments in the world."),
        Article(featureImage = Drawable.amsterdam, title = "Amsterdam", description = "Amsterdam, the capital and most populous city of the Netherlands, is a vibrant metropolis renowned for its artistic heritage, elaborate canal systems, and narrow canal houses with gabled facades. Located in the province of North Holland, the city blends rich history with a progressive, modern lifestyle."),
        Article(featureImage = Drawable.belgium_atomium, title = "The Atomium", description = "The Atomium is one of Brussels' most iconic landmarks and a masterpiece of mid-century modernist architecture. Originally built as the central pavilion and symbol for the 1958 Brussels World's Fair (Expo 58), it was designed to celebrate scientific progress, engineering skill, and the peaceful use of atomic energy at the dawn of the Atomic Age."),
        Article(featureImage = Drawable.pisa, title = "Pisa", description = "Pisa is a historic city in the Tuscany region of central Italy, globally renowned for its iconic architectural marvels, vibrant culture, and deep academic roots. Though world-famous for its unintentional \"leaning\" landmark, Pisa is a lively medieval university town with a rich maritime history and plenty of cultural charm."),
    )

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