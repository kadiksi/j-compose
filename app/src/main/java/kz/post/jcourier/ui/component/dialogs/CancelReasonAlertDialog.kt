package kz.post.jcourier.ui.component.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kz.post.jcourier.R
import kz.post.jcourier.data.model.task.CancelReason


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CancelReasonAlertDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (taskId: Long, reason: String, cancelReasonOther: String?) -> Unit,
    text: String,
    taskId: Long,
    cancellationReasonsList: ArrayList<CancelReason>,
) {
    if (show) {
//        var showChooseError by remember { mutableStateOf(false) }
        var expanded by remember { mutableStateOf(false) }
        val cancellationReasons = cancellationReasonsList.map { it.description }

        var selectedText by remember { mutableStateOf(cancellationReasons[0]) }
        var selectedIndex by remember { mutableStateOf(0) }

        var inputText by remember { mutableStateOf("") }
        val textFieldColors = TextFieldDefaults.outlinedTextFieldColors(
            disabledBorderColor = if (inputText.isEmpty()) Color.Red else Color.Gray,
        )
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = {
//                    if (selectedIndex == 0) {
////                        showChooseError = true
//                        return@TextButton
//                    }
//                    if (selectedIndex == 4 && inputText.trim().length < 3) {
//                        return@TextButton
//                    }
                    onConfirm.invoke(
                        taskId,
                        cancellationReasonsList[cancellationReasons.indexOf(selectedText)].reason,
                        inputText
                    )
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
//                                        showChooseError = selectedIndex == 0
                                    }
                                )
                            }
                        }
                    }
//                    if (showChooseError) {
//                        Text(
//                            color = Color.Red,
//                            text = stringResource(id = R.string.choose_reason)
//                        )
//                    }
//                    if (selectedText == cancellationReasons.last()) {
//                        OutlinedTextField(
//                            colors = textFieldColors,
//                            isError = inputText.isEmpty(),
//                            value = inputText,
//                            onValueChange = { inputText = it },
//                            placeholder = {
//                                Text(stringResource(id = R.string.enter_cancellation_reason))
//                            },
//                        )
//                    }
                }
            },
        )
    }
}