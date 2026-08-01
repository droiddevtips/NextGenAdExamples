package com.droiddevtips.nextgenexamples.screen.bannerAdExample.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droiddevtips.nextgenexamples.core.Drawable
import com.droiddevtips.nextgenexamples.screen.bannerAdExample.data.BannerAdExampleDisplayItem
import com.droiddevtips.nextgenexamples.screen.bannerAdExample.data.BannerAdListViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

class BannerAdListViewModel : ViewModel() {

    private val _viewState = MutableStateFlow(BannerAdListViewState())
    val viewState: StateFlow<BannerAdListViewState>
        get() = _viewState.asStateFlow().onStart {
            Log.i("TAG12","onStart called!")
            loadBannerAdListItems()
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), BannerAdListViewState())

    fun loadBannerAdListItems() {

        //  = withContext(Dispatchers.IO)

        Log.i("TAG12","Load banner ad list items called!")

        val itemList = ArrayList<BannerAdExampleDisplayItem>()

        (1 until 100).forEach { itemNumber ->

            itemList.apply {

                add(BannerAdExampleDisplayItem.Article(
                    _key = itemNumber, // Article unique key
                    icon = Drawable.ads_icon,
                    title = "Article $itemNumber",
                    description = "This is a short summary for article $itemNumber"
                ))

                if (itemNumber % 10 == 0) {
                    add(BannerAdExampleDisplayItem.AdView(
                        _key = 2026+itemNumber, // create a unique key for banner ad view
                        adUnit = "Test"
                    ))
                }
            }
        }

        _viewState.update {
            it.copy(articles = itemList)
        }
    }
}