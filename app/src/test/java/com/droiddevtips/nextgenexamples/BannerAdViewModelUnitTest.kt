@file:OptIn(ExperimentalCoroutinesApi::class)

package com.droiddevtips.nextgenexamples

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.droiddevtips.nextgenexamples.fakes.FakeAdManager
import com.droiddevtips.nextgenexamples.screen.bannerAdExample.data.BannerAdExampleDisplayItem
import com.droiddevtips.nextgenexamples.screen.bannerAdExample.data.BannerAdExampleViewModelAction
import com.droiddevtips.nextgenexamples.screen.bannerAdExample.ui.BannerAdExampleViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * Unit tests for [BannerAdExampleViewModel].
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
class BannerAdViewModelUnitTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial view state is loading with no articles`() {
        val viewModel = BannerAdExampleViewModel(FakeAdManager())

        assertThat(viewModel.viewState.value.isLoading).isTrue()
        assertThat(viewModel.viewState.value.articles).isEmpty()
    }

    @Test
    fun `subscribing loads articles and banner ad slots then stops loading`() = runTest(testDispatcher) {
        val fakeAdManager = FakeAdManager()
        val viewModel = BannerAdExampleViewModel(fakeAdManager)

        viewModel.viewState.test {
            val initial = awaitItem()
            assertThat(initial.isLoading).isTrue()
            assertThat(initial.articles).isEmpty()

            val loaded = awaitItem()
            assertThat(loaded.isLoading).isFalse()
            // 100 articles, with a banner ad slot inserted after every 10th one
            assertThat(loaded.articles).hasSize(110)
            assertThat(loaded.articles.filterIsInstance<BannerAdExampleDisplayItem.AdView>())
                .hasSize(10)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `subscribing pre-loads a banner ad for every ad slot`() = runTest(testDispatcher) {
        val fakeAdManager = FakeAdManager()
        val viewModel = BannerAdExampleViewModel(fakeAdManager)

        viewModel.viewState.test {
            awaitItem() // initial
            awaitItem() // loaded
            cancelAndIgnoreRemainingEvents()
        }

        assertThat(fakeAdManager.preLoadedAdUnits.distinct()).hasSize(10)
    }

    @Test
    fun `destroying all banner ads clears the cached ad units via the ad manager`() = runTest(testDispatcher) {
        val fakeAdManager = FakeAdManager()
        val viewModel = BannerAdExampleViewModel(fakeAdManager)

        viewModel.viewState.test {
            awaitItem() // initial
            awaitItem() // loaded
            cancelAndIgnoreRemainingEvents()
        }

        viewModel.performAction(BannerAdExampleViewModelAction.DestroyAllBannerAds)

        assertThat(fakeAdManager.clearedAdUnitBatches).hasSize(1)
        assertThat(fakeAdManager.clearedAdUnitBatches.first()).hasSize(10)
    }

    @Test
    fun `destroying all banner ads twice only clears the cache actually accumulated`() = runTest(testDispatcher) {
        val fakeAdManager = FakeAdManager()
        val viewModel = BannerAdExampleViewModel(fakeAdManager)

        viewModel.viewState.test {
            awaitItem() // initial
            awaitItem() // loaded
            cancelAndIgnoreRemainingEvents()
        }

        viewModel.performAction(BannerAdExampleViewModelAction.DestroyAllBannerAds)
        viewModel.performAction(BannerAdExampleViewModelAction.DestroyAllBannerAds)

        assertThat(fakeAdManager.clearedAdUnitBatches).hasSize(2)
        assertThat(fakeAdManager.clearedAdUnitBatches[0]).hasSize(10)
        assertThat(fakeAdManager.clearedAdUnitBatches[1]).isEqualTo(emptyList())
    }
}
