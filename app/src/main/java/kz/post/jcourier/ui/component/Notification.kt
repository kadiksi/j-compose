package kz.post.jcourier.ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import kz.post.jcourier.R

@Composable
fun SimpleAlertDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (show) {
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = onConfirm)
                { Text(text = "OK") }
            },
//            dismissButton = {
//                TextButton(onClick = onDismiss)
//                { Text(text = "Cancel") }
//            },
            text = { Text(text = stringResource(id = R.string.login_error)) },
//            text = { Text(text = "Should I continue with the requested action?") }
        )
    }
}
