package com.droiddevtips.nextgenexamples.screen.interstitialAds.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droiddevtips.nextgenexamples.ads.domain.AdManager
import com.droiddevtips.nextgenexamples.ads.domain.model.AdUnit
import com.droiddevtips.nextgenexamples.core.Drawable
import com.droiddevtips.nextgenexamples.screen.interstitialAds.data.InterstitialAdArticle
import com.droiddevtips.nextgenexamples.screen.interstitialAds.data.InterstitialAdsExampleViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.seconds

/**
 * ViewModel responsible for managing the lifecycle and state of the interstitial ad example
 * displayed within a Composable screen.
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
class InterstitialAdsExampleViewModel(
    private val adManager: AdManager
) : ViewModel() {

    private val _viewState = MutableStateFlow(InterstitialAdsExampleViewState())
    val viewState: StateFlow<InterstitialAdsExampleViewState> = _viewState.asStateFlow().onStart {
        loadArticleListItems()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000L), InterstitialAdsExampleViewState())

    init {
        adManager.preLoadInterstitialAd(adUnit = AdUnit.InterstitialAd)

        viewModelScope.launch {
            adManager.interstitialAdsAvailable.collectLatest { adAvailable ->
                _viewState.update { it.copy(interstitialAvailable = adAvailable) }
            }
        }
    }

    override fun onCleared() {
        adManager.destroyInterstitialAd(AdUnit.InterstitialAd)
    }

    suspend fun loadArticleListItems() = withContext(Dispatchers.IO) {

        val itemList = (1..100).map { itemNumber ->
            dummyArticles.random().copy(key = itemNumber)
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

    private val dummyArticles = listOf(
        InterstitialAdArticle(key = 0, flag = Drawable.france_flag, featureImage = Drawable.paris, title = "The Eiffel Tower", description = "The Eiffel Tower (French: Tour Eiffel) is a wrought-iron lattice tower located on the Champ de Mars in Paris, France. Widely recognized as the ultimate symbol of Paris and a global cultural icon of France, it is one of the most-visited monuments in the world."),
        InterstitialAdArticle(key = 0, flag = Drawable.nl_flag, featureImage = Drawable.amsterdam, title = "Amsterdam", description = "Amsterdam, the capital and most populous city of the Netherlands, is a vibrant metropolis renowned for its artistic heritage, elaborate canal systems, and narrow canal houses with gabled facades. Located in the province of North Holland, the city blends rich history with a progressive, modern lifestyle."),
        InterstitialAdArticle(key = 0, flag = Drawable.belgium_flag, featureImage = Drawable.belgium_atomium, title = "The Atomium", description = "The Atomium is one of Brussels' most iconic landmarks and a masterpiece of mid-century modernist architecture. Originally built as the central pavilion and symbol for the 1958 Brussels World's Fair (Expo 58), it was designed to celebrate scientific progress, engineering skill, and the peaceful use of atomic energy at the dawn of the Atomic Age."),
        InterstitialAdArticle(key = 0, flag = Drawable.italy_flag, featureImage = Drawable.pisa, title = "Pisa", description = "Pisa is a historic city in the Tuscany region of central Italy, globally renowned for its iconic architectural marvels, vibrant culture, and deep academic roots. Though world-famous for its unintentional \"leaning\" landmark, Pisa is a lively medieval university town with a rich maritime history and plenty of cultural charm."),
        InterstitialAdArticle(key = 0, flag = Drawable.aus_flag, featureImage = Drawable.sydney_opera_house, title = "Sydney Opera House", description = "The Sydney Opera House is not just one of the most famous buildings in Sydney but is an icon of all of Australia. This stunning opera house was built between 1959 and 1973 and has become one of the most popular tourist attractions in Australia with an estimated 8 million yearly visitors."),
    )
}