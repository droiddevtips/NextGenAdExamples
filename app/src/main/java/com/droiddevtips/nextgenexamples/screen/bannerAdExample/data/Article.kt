package com.droiddevtips.nextgenexamples.screen.bannerAdExample.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(val featureImage:Int, val title:String, val description:String): Parcelable
