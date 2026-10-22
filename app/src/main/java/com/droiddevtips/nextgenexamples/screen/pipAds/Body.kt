package com.droiddevtips.nextgenexamples.screen.pipAds

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.droiddevtips.nextgenexamples.core.AppColor
import com.droiddevtips.nextgenexamples.core.AppString

@Composable
fun Body(
    headerHeight: Dp,
    scroll: ScrollState,
    modifier: Modifier = Modifier
) {

    val textColor = if (isSystemInDarkTheme()) Color.White else Color.Black

    Column(
        modifier = modifier
            .padding(horizontal = 8.dp)
            .verticalScroll(scroll)
    ) {

        Spacer(modifier = Modifier.height(headerHeight))

        Introduction(textID = AppString.machu_introduction, textColor = textColor)

        Header(textID = AppString.machu_header1, textColor = textColor)
        Description(textID = AppString.machu_header1_description, textColor = textColor)

        Header(textID = AppString.machu_header2, textColor = textColor)
        Description(textID = AppString.machu_header2_description, textColor = textColor)

        Header(textID = AppString.machu_header3, textColor = textColor)
        Description(textID = AppString.machu_header3_description, textColor = textColor)

        Header(textID = AppString.machu_header4, textColor = textColor)
        Description(textID = AppString.machu_header4_description, textColor = textColor)

        Spacer(modifier = Modifier.height(50.dp))

    }


}

@Composable
private fun Introduction(textID: Int, textColor: Color, modifier: Modifier = Modifier) {
    val introduction = stringResource(id = textID)
    val firstChar = introduction.take(1)
    val remainingIntroductionText = introduction.drop(1)

    val firstCharacterTextColor =
        if (isSystemInDarkTheme()) Color.White else colorResource(id = AppColor.dark_grey_4e)
    val annotatedString = buildAnnotatedString {

        withStyle(
            style = SpanStyle(
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = firstCharacterTextColor
            )
        ) {
            append(firstChar)
        }

        append(remainingIntroductionText)
    }

    Text(text = annotatedString, color = textColor, modifier = modifier)
}

@Composable
private fun Header(textID: Int, textColor: Color, modifier: Modifier = Modifier) {
    Text(
        text = stringResource(id = textID),
        fontWeight = FontWeight.Bold,
        modifier = modifier,
        fontSize = 18.sp,
        color = textColor,
        lineHeight = 30.sp
    )
}

@Composable
private fun Description(textID: Int, textColor: Color, modifier: Modifier = Modifier) {
    Text(text = stringResource(id = textID), color = textColor, modifier = modifier)
}