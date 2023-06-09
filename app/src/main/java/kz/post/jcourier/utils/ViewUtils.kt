package kz.post.jcourier.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight

@Composable
fun boldSpanStyles(): List<AnnotatedString.Range<SpanStyle>> {
    return listOf(
        AnnotatedString.Range(
            SpanStyle(fontWeight = FontWeight.Bold), start = 0, end = 2
        )
    )
}