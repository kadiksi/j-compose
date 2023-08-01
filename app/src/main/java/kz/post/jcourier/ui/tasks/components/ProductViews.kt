package kz.post.jcourier.ui.tasks.components

import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kz.post.jcourier.R
import kz.post.jcourier.data.model.task.Task

@Composable
fun ProductViews(task: Task) {
    if (task.product?.isNotEmpty() == true) {
        TextView(
            stringResource(R.string.product_list), MaterialTheme.typography.titleLarge
        )
        task.product?.forEach {
            it.name?.let { it1 ->
                TextView(
                    it1,
                    MaterialTheme.typography.labelLarge,
                    topPaddign = 0.dp,
                    bottomPaddign = 0.dp,
                    fontWeight = FontWeight.Bold
                )
                TextView(
                    "${stringResource(id = R.string.quantity)}: ${it.quantity}",
                    MaterialTheme.typography.labelMedium,
                    topPaddign = 0.dp,
                    bottomPaddign = 0.dp,
                    startPaddign = 24.dp
                )
                TextView(
                    "${stringResource(id = R.string.size)}: ${it.toSizeInfo()}",
                    MaterialTheme.typography.labelMedium,
                    topPaddign = 0.dp,
                    bottomPaddign = 0.dp,
                    startPaddign = 24.dp
                )
                TextView(
                    "${stringResource(id = R.string.price)}: ${it.price}",
                    MaterialTheme.typography.labelMedium,
                    topPaddign = 0.dp,
                    bottomPaddign = 0.dp,
                    startPaddign = 24.dp
                )
                TextView(
                    "${stringResource(id = R.string.weight)}: ${it.weight}",
                    MaterialTheme.typography.labelMedium,
                    topPaddign = 0.dp,
                    bottomPaddign = 0.dp,
                    startPaddign = 24.dp
                )
            }
        }
    }
}