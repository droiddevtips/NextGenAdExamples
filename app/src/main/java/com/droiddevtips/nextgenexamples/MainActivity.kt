package com.droiddevtips.nextgenexamples

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import com.droiddevtips.nextgenexamples.navigator.Navigator
import com.droiddevtips.nextgenexamples.ui.theme.DroidDevTipsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DroidDevTipsTheme {
                Navigator(modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.background))
            }
        }
    }
}