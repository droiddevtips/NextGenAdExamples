package com.droiddevtips.nextgenexamples.screen.swipeable_ad.data

data class SwipeableAdViewState(
    val userScrollEnabled: Boolean = true,
    val showLoadingScreen: Boolean = true,
    val items: List<GalleryItem> = emptyList()
)