@file:OptIn(ExperimentalMaterial3Api::class)

package com.droiddevtips.nextgenexamples.screen.interstitialAds.detail

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.droiddevtips.nextgenexamples.core.Drawable
import com.droiddevtips.nextgenexamples.screen.interstitialAds.data.InterstitialAdArticle
import com.droiddevtips.nextgenexamples.ui.theme.DroidDevTipsTheme

/**
 * Displays the details of a single interstitial ad example article.
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
@Composable
fun InterstitialAdArticleDetail(
    article: InterstitialAdArticle?,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
                title = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = article?.title.orEmpty())
                        article?.let {
                            Image(
                                painter = painterResource(id = it.flag),
                                modifier = Modifier.size(width = 40.dp, height = 20.dp),
                                contentDescription = null
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        if (article == null) {
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Article not found", modifier = Modifier.align(alignment = Alignment.Center))
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = painterResource(id = article.featureImage),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
            )

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = article.description,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview(name = "Light", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewInterstitialAdArticleDetail() {
    DroidDevTipsTheme {
        InterstitialAdArticleDetail(
            article = InterstitialAdArticle(
                key = 123,
                featureImage = Drawable.interstitial,
                title = "Test title",
                description = "Test description",
                flag = Drawable.nl_flag
            ),
            onBackClick = {}
        )
    }
}