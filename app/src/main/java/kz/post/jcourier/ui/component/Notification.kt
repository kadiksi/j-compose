package kz.post.jcourier.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kz.post.jcourier.R

@Composable
fun SimpleAlertDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    text: String = stringResource(id = R.string.error)
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
            text = { Text(text = text) },
//            text = { Text(text = "Should I continue with the requested action?") }
        )
    }
}


@Composable
fun SimpleInputTextAlertDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (taskId: Int, sms: String) -> Unit,
    text: String = stringResource(id = R.string.sms),
    taskId :Int
) {
    var inputText by remember { mutableStateOf("") }
    if (show) {
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = { onConfirm.invoke(taskId , inputText) })
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
