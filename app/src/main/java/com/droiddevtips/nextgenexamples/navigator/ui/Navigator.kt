@file:OptIn(ExperimentalMaterial3AdaptiveApi::class)

package com.droiddevtips.nextgenexamples.navigator.ui

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
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.droiddevtips.nextgenexamples.navigator.data.NavigatorViewAction
import com.droiddevtips.nextgenexamples.navigator.data.NavigatorViewState
import com.droiddevtips.nextgenexamples.navigator.ui.detailPane.data.Route
import com.droiddevtips.nextgenexamples.navigator.ui.detailPane.ui.DetailView
import com.droiddevtips.nextgenexamples.navigator.ui.listPane.data.ListItem
import com.droiddevtips.nextgenexamples.navigator.ui.listPane.ui.ListPaneView
import kotlinx.coroutines.launch

@Composable
fun Navigator(
    viewState: State<NavigatorViewState>,
    items: List<ListItem>,
    modifier: Modifier = Modifier,
    navigatorViewAction: (NavigatorViewAction) -> Unit
) {

    val scope = rememberCoroutineScope()
    val navigator = rememberListDetailPaneScaffoldNavigator<Route>()

    NavigableListDetailPaneScaffold(
        modifier = modifier,
        navigator = navigator,
        listPane = {
            AnimatedPane {
                ListPaneView(
                    viewState = viewState,
                    itemList = items,
                    modifier = Modifier
                        .statusBarsPadding()
                        .fillMaxHeight()
                        .fillMaxWidth()
                ) { route ->
                    navigatorViewAction(NavigatorViewAction.SetSelectedItem(route = route))
                    scope.launch {
                        navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, route)
                    }
                }
            }
        },
        detailPane = {
            AnimatedPane {
                val itemRoute = navigator.currentDestination?.contentKey ?: Route.EmptyScreen
                DetailView(
                    route = itemRoute, modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                )
            }
        }
    )
}