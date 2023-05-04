package kz.jcourier.ui.component

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import kz.jcourier.R

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
