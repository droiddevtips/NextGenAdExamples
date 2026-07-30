@file:OptIn(ExperimentalMaterial3AdaptiveApi::class)

package com.droiddevtips.nextgenexamples.navigator

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

@Composable
fun Navigator(modifier: Modifier = Modifier) {

    val demoItems = listOf(
        ListItem(
            route = Route.BannerAdExample,
            title = "Banner Ads",
            subtitle = "Compose banner ads example"
        ),
        ListItem(
            route = Route.EmptyScreen,
            title = "Empty screen",
            subtitle = "Compose banner ads example1"
        ),
        ListItem(
            route = Route.IconAd,
            title = "Icon Ads",
            subtitle = "Compose banner ads example2"
        ),
        ListItem(
            route = Route.InterstitialAds,
            title = "Interstitial ads",
            subtitle = "Compose banner ads example3"
        ),
        ListItem(
            route = Route.NativeAdExample,
            title = "Native ads example",
            subtitle = "Compose banner ads example4"
        ),
        ListItem(
            route = Route.RewardedInterstitialAdExample,
            title = "Rewarded Interstitial ads",
            subtitle = "Compose banner ads example5"
        )
    )

    val scope = rememberCoroutineScope()
    val navigator = rememberListDetailPaneScaffoldNavigator<Route>()

    NavigableListDetailPaneScaffold(
        modifier = modifier,
        navigator = navigator,
        listPane = {
            AnimatedPane {
                ListPaneView(
                    itemList = demoItems,
                    modifier = Modifier
                        .statusBarsPadding()
                        .fillMaxHeight()
                        .fillMaxWidth()
                ) { item ->
                    scope.launch {
                        navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, item)
                    }
                }
            }
        },
        detailPane = {
            AnimatedPane {
                val itemRoute = navigator.currentDestination?.contentKey ?: Route.EmptyScreen
//                navController.navigate(route = newsItem)
                DetailView(route = itemRoute, modifier = Modifier.fillMaxSize().statusBarsPadding())


//                ComingSoonPlaceholder(modifier = Modifier
//                    .fillMaxWidth()
//                    .fillMaxHeight())
            }
        }
    )


}