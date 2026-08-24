package com.droiddevtips.nextgenexamples.screen.interstitialAds

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.droiddevtips.appwindowsizeandorientationdetector.Device
import com.droiddevtips.appwindowsizeandorientationdetector.deviceDetectorCurrentWindowSize
import com.droiddevtips.nextgenexamples.navigator.data.Screen
import com.droiddevtips.nextgenexamples.screen.interstitialAds.data.InterstitialAdsExampleViewModelAction
import com.droiddevtips.nextgenexamples.screen.interstitialAds.data.InterstitialAdsRoute
import com.droiddevtips.nextgenexamples.screen.interstitialAds.detail.InterstitialAdArticleDetail
import com.droiddevtips.nextgenexamples.screen.interstitialAds.grid.InterstitialAdsGridExample
import com.droiddevtips.nextgenexamples.screen.interstitialAds.ui.InterstitialAdsExampleViewModel
import com.droiddevtips.nextgenexamples.screen.interstitialAds.ui.InterstitialAdsExampleViewModelFactory
import com.droiddevtips.nextgenexamples.screen.interstitialAds.ui.InterstitialLauncher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * The interstitial ads example composable, hosting the 'list' (grid) and 'detail' routes
 * of the interstitial ads example nav host.
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
@Composable
fun InterstitialAdsExample(screen: Screen, modifier: Modifier = Modifier) {

    val windowSize = deviceDetectorCurrentWindowSize()
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val isListRoute = currentBackStackEntry?.destination?.hasRoute<InterstitialAdsRoute.List>() ?: true
    val scope = rememberCoroutineScope()
    val activity = LocalActivity.current

    val viewModel: InterstitialAdsExampleViewModel =
        viewModel(factory = InterstitialAdsExampleViewModelFactory())
    val viewState = viewModel.viewState.collectAsStateWithLifecycle()

    Scaffold(modifier = modifier, topBar = {
        if (windowSize.device is Device.Mobile && isListRoute) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    space = 8.dp,
                    alignment = Alignment.CenterHorizontally
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painter = painterResource(id = screen.icon),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )

                Text(text = screen.title, fontWeight = FontWeight.Bold)

            }
        }
    }) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = InterstitialAdsRoute.List,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            composable<InterstitialAdsRoute.List> {
                InterstitialAdsGridExample(
                    viewState = viewState.value,
                    onArticleClick = { article ->
                        InterstitialLauncher.launchInterstitial(activity = activity) {
                            scope.launch(Dispatchers.Main) {
                                navController.navigate(InterstitialAdsRoute.Detail(articleKey = article.key))
                            }
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable<InterstitialAdsRoute.Detail> { backStackEntry ->
                val route: InterstitialAdsRoute.Detail = backStackEntry.toRoute()
                val article = viewState.value.articles.firstOrNull { it.key == route.articleKey }

                InterstitialAdArticleDetail(
                    article = article,
                    onBackClick = { navController.popBackStack() },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.performAction(action = InterstitialAdsExampleViewModelAction.DestroyAllBannerAds)
        }
    }
}