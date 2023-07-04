package kz.post.jcourier.ui.component.dialogs

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import kz.post.jcourier.R


@Composable
fun InputTextAlertDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (context: Context, taskId: Long, sms: String) -> Unit,
    text: String = stringResource(id = R.string.sms),
    taskId: Long
) {
    var inputText by remember { mutableStateOf("") }
    if (show) {
        val context = LocalContext.current
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = { onConfirm.invoke(context, taskId, inputText) })
                { Text(text = stringResource(id = R.string.ok)) }
            },
            dismissButton = {
                TextButton(onClick = onDismiss)
                { Text(text = stringResource(id = R.string.cancel)) }
            },
            text = {
                Column {
                    Text(text)
                    TextField(
                        value = inputText,
                        onValueChange = { inputText = it },
                        singleLine = true,
                    )
                }
            },
        )
    }
}