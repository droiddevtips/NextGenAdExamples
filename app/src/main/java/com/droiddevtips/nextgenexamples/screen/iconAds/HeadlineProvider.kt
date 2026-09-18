package com.droiddevtips.nextgenexamples.screen.iconAds

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class HeadlineProvider : PreviewParameterProvider<String> {

    override val values: Sequence<String>
        get() = sequenceOf(
            "Headline text"
        )
}
