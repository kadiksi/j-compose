package kz.post.jcourier.ui.tasks.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun TextView(
    text: String,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    startPaddign: Dp = 16.dp,
    endPaddign: Dp = 16.dp,
    topPaddign: Dp = 16.dp,
    bottomPaddign: Dp = 16.dp
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
                .wrapContentWidth(Alignment.Start)
        )
    }
}