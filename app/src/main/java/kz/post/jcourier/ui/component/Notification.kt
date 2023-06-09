package kz.post.jcourier.ui.component

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kz.post.jcourier.R
import kz.post.jcourier.data.model.task.CancelReasonDto

@Composable
fun AlertDialog(
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
fun InputTextAlertDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (taskId: Int, sms: String) -> Unit,
    text: String = stringResource(id = R.string.sms),
    taskId: Int
) {
    var inputText by remember { mutableStateOf("") }
    if (show) {
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = { onConfirm.invoke(taskId, inputText) })
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CancelReasonAlertDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (taskId: Int, selectedIndex: Int, cancelReasonOther: String?) -> Unit,
    text: String,
    taskId: Int
) {
    var showChooseError by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    val cancellationReasons = stringArrayResource(id = R.array.cancellation_reason_list)

    var selectedText by remember { mutableStateOf(cancellationReasons[0]) }
    var selectedIndex by remember { mutableStateOf(0) }

    var inputText by remember { mutableStateOf("") }
    val textFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        disabledBorderColor = if (inputText.isEmpty()) Color.Red else Color.Gray,
    )

    if (show) {
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = {
                    if (selectedIndex == 0) {
                        showChooseError = true
                        return@TextButton
                    }
                    if(selectedIndex == 4 && inputText.trim().length < 3){
                        return@TextButton
                    }
                    onConfirm.invoke(taskId, selectedIndex, inputText)
                })
                { Text(text = stringResource(id = R.string.ok)) }
            },
            dismissButton = {
                TextButton(onClick = onDismiss)
                { Text(text = stringResource(id = R.string.cancel)) }
            },
            text = {
                Column(
                    modifier = Modifier
                        .height(140.dp)
                ) {
                    Text(text)
                    Divider(
                        color = Color.Gray,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                    )
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = {
                            expanded = !expanded
                        }
                    ) {
                        TextField(
                            value = selectedText,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier.menuAnchor()
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            cancellationReasons.forEach { item ->
                                DropdownMenuItem(
                                    text = { Text(text = item) },
                                    onClick = {
                                        selectedText = item
                                        selectedIndex = cancellationReasons.indexOf(item)
                                        expanded = false
                                        showChooseError = selectedIndex == 0
                                    }
                                )
                            }
                        }
                    }
                    if (showChooseError) {
                        Text(
                            color = Color.Red,
                            text = stringResource(id = R.string.choose_reason)
                        )
                    }
                    if (selectedText == cancellationReasons[4]) {
                        OutlinedTextField(
                            colors = textFieldColors,
                            isError = inputText.isEmpty(),
                            value = inputText,
                            onValueChange = { inputText = it },
                            placeholder = {
                                Text(stringResource(id = R.string.enter_cancellation_reason))
                            },
                        )
                    }
                }
            },
        )
    }
}