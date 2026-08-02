package com.droiddevtips.nextgenexamples

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.droiddevtips.nextgenexamples.googleAdsConsentManager.GoogleAdsConsentManager
import com.droiddevtips.nextgenexamples.googleAdsConsentManager.OnConsentGatheringCompleteListener
import com.droiddevtips.nextgenexamples.navigator.ui.Navigator
import com.droiddevtips.nextgenexamples.navigator.ui.NavigatorViewModel
import com.droiddevtips.nextgenexamples.navigator.ui.NavigatorViewModelFactory
import com.droiddevtips.nextgenexamples.navigator.data.Screen
import com.droiddevtips.nextgenexamples.ui.theme.DroidDevTipsTheme
import com.google.android.ump.FormError

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DroidDevTipsTheme {

                val items = Screen::class.sealedSubclasses.mapNotNull { it.objectInstance }.filterNot { it is Screen.EmptyScreen || it is Screen.NoItemSelected }

                val navigatorViewModel: NavigatorViewModel =
                    viewModel(factory = NavigatorViewModelFactory())
                val viewState = navigatorViewModel.viewState.collectAsStateWithLifecycle()
                Navigator(
                    viewState = viewState,
                    items = items,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.background),
                    navigatorViewAction = navigatorViewModel::performAction
                )
            }
        }
        loadConsentFormIfRequired()
    }

    private fun loadConsentFormIfRequired() {
        GoogleAdsConsentManager.gatherConsent(activity = this, listener = object: OnConsentGatheringCompleteListener {
            override fun consentGatheringComplete(error: FormError?) {

                error?.let {
                    Log.e("TAG15","${it.errorCode}: ${it.message}")
                }

                Log.i("TAG15","Can request ads -> ${GoogleAdsConsentManager.canRequestAds()}")
            }
        })
    }

}