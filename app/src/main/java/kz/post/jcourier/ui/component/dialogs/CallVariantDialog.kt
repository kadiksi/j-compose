package kz.post.jcourier.ui.component.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kz.post.jcourier.R
import kz.post.jcourier.data.model.task.CallDto


@Composable
fun CallVariantDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (taskId: Long, direction: CallDto) -> Unit,
    text: String = stringResource(id = R.string.sms),
    taskId: Long
) {
    if (show) {
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = onDismiss) { Text(text = stringResource(id = R.string.cancel)) }
            },
            text = {
                Column {
                    Text(text)
                    Button(
                        onClick = { onConfirm.invoke(taskId, CallDto.RECIPIENT) },
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth()
                    ) {
                        Text(stringResource(id = R.string.client))
                    }
                    Button(
                        onClick = { onConfirm.invoke(taskId, CallDto.SENDER) },
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth()
                    ) {
                        Text(stringResource(id = R.string.merchant))
                    }
                }
            },
        )
    }
}