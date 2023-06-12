package kz.post.jcourier.ui.component.dialogs

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import kz.post.jcourier.R

@Composable
fun ErrorAlertDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    text: String = stringResource(id = R.string.error)
) {
    if (show) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = onConfirm)
                { Text(text = "OK") }
            },
            text = { Text(text = text) },
        )
    }
}