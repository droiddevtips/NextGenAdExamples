package com.droiddevtips.nextgenexamples

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.droiddevtips.nextgenexamples.navigator.data.demoItems
import com.droiddevtips.nextgenexamples.navigator.ui.Navigator
import com.droiddevtips.nextgenexamples.navigator.ui.NavigatorViewModel
import com.droiddevtips.nextgenexamples.navigator.ui.NavigatorViewModelFactory
import com.droiddevtips.nextgenexamples.ui.theme.DroidDevTipsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DroidDevTipsTheme {
                val navigatorViewModel: NavigatorViewModel =
                    viewModel(factory = NavigatorViewModelFactory())
                val viewState = navigatorViewModel.viewState.collectAsStateWithLifecycle()
                Navigator(
                    viewState = viewState,
                    items = demoItems,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.background),
                    navigatorViewAction = navigatorViewModel::performAction
                )
            }
        }
    }
}