package kz.post.jcourier.ui.tasks.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kz.post.jcourier.R
import kz.post.jcourier.utils.boldSpanStyles

@Composable
fun TextView(
    text: String,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    startPaddign: Dp = 16.dp,
    endPaddign: Dp = 16.dp,
    topPaddign: Dp = 16.dp,
    bottomPaddign: Dp = 16.dp,
    fontWeight: FontWeight = FontWeight.Normal
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = text,
            style = style,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    PaddingValues(
                        start = startPaddign,
                        end = endPaddign,
                        top = topPaddign,
                        bottom = bottomPaddign
                    )
                )
                .wrapContentWidth(Alignment.Start),
            fontWeight = fontWeight
        )
    }
}


@Composable
fun TextViewAnnotated(
    title: Int,
    text: String,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TextView(
            stringResource(title),
            MaterialTheme.typography.labelLarge,
            topPaddign = 8.dp,
            bottomPaddign = 0.dp,
            fontWeight = FontWeight.Bold
        )
        TextView(
            text,
            bottomPaddign = 8.dp,
            topPaddign = 4.dp
        )
    }
}