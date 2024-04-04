package kz.post.jcourier.ui.component.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kz.post.jcourier.R
import kz.post.jcourier.data.model.task.Cancellation
import kz.post.jcourier.data.model.task.CancellationType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CancelReasonAlertDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (taskId: Long, type: Cancellation, reason: String, cancelReasonOther: String?) -> Unit,
    text: String,
    taskId: Long,
    cancellations: ArrayList<CancellationType>?,
) {
    if (show && !cancellations.isNullOrEmpty()) {
        var expandedTypes by remember { mutableStateOf(false) }

        var selectedTypeText by remember { mutableStateOf("") }
        var selectedTypeIndex by remember { mutableStateOf(0) }

        var showReasons by remember { mutableStateOf(false) }

        var expandedReason by remember { mutableStateOf(false) }
        var cancellationReason by remember { mutableStateOf(cancellations[0].cancellationReasons) }


        var selectedReasonText by remember { mutableStateOf("") }
        var selectedReasonIndex by remember { mutableStateOf(0) }

        var inputText by remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = {
                    onConfirm.invoke(
                        taskId,
                        cancellations[selectedTypeIndex].cancellationType,
                        cancellationReason!![selectedReasonIndex].cancellationReason,
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
                        .height(200.dp)
                ) {
                    Text(text)
                    Divider(
                        color = Color.Gray,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                    )
                    ExposedDropdownMenuBox(
                        expanded = expandedTypes,
                        onExpandedChange = {
                            expandedTypes = !expandedTypes
                        }
                    ) {
                        TextField(
                            value = selectedTypeText,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedTypes) },
                            modifier = Modifier.menuAnchor()
                        )

                        ExposedDropdownMenu(
                            expanded = expandedTypes,
                            onDismissRequest = { expandedTypes = false }
                        ) {
                            cancellations.forEach { item ->
                                DropdownMenuItem(
                                    text = { Text(text = item.description) },
                                    onClick = {
                                        selectedTypeText = item.description
                                        selectedTypeIndex = cancellations.indexOf(item)
                                        expandedTypes = false
                                        showReasons = true
                                        cancellationReason = item.cancellationReasons
                                        selectedReasonText = ""
                                    }
                                )
                            }
                        }
                    }
                    if (showReasons) {
                        Divider(
                            color = Color.White,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                        )
                        ExposedDropdownMenuBox(
                            expanded = expandedReason,
                            onExpandedChange = {
                                expandedReason = !expandedReason
                            }
                        ) {
                            TextField(
                                value = selectedReasonText,
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedReason) },
                                modifier = Modifier.menuAnchor()
                            )

                            ExposedDropdownMenu(
                                expanded = expandedReason,
                                onDismissRequest = { expandedReason = false }
                            ) {
                                cancellationReason?.forEach { item ->
                                    DropdownMenuItem(
                                        text = { Text(text = item.description) },
                                        onClick = {
                                            selectedReasonText = item.description
                                            selectedReasonIndex = cancellationReason!!.indexOf(item)
                                            expandedReason = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            },
        )
    }
}