package com.droiddevtips.nextgenexamples.screen.bannerAdExample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droiddevtips.nextgenexamples.screen.bannerAdExample.data.BannerAdListViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class BannerAdListViewModel : ViewModel() {

    private val _viewState = MutableStateFlow(BannerAdListViewState())
    val viewState: StateFlow<BannerAdListViewState>
        get() = _viewState.asStateFlow().onStart {
            loadBannerAdListItems()
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(2000L), BannerAdListViewState())

    suspend fun loadBannerAdListItems() {

    }


}