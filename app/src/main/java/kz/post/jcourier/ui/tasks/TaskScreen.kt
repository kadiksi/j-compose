package kz.post.jcourier.ui.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kz.post.jcourier.R
import kz.post.jcourier.data.model.task.TaskStatus
import kz.post.jcourier.ui.component.dialogs.ErrorAlertDialog
import kz.post.jcourier.ui.component.TopBar
import kz.post.jcourier.ui.component.dialogs.CallVariantDialog
import kz.post.jcourier.ui.component.dialogs.CancelReasonAlertDialog
import kz.post.jcourier.ui.component.dialogs.InputTextAlertDialog
import kz.post.jcourier.viewmodel.TaskViewModel

@Composable
fun task(
    navController: NavController, taskViewModel: TaskViewModel = hiltViewModel()
) {
    val task = taskViewModel.uiState.task.value
    val isError by taskViewModel.uiState.isError
    val isSmsDialog by taskViewModel.uiState.isSmsDialog
    val isCancelReasonDialog by taskViewModel.uiState.isCancelReasonDialog
    val isCallVariantsDialog by taskViewModel.uiState.isCallVariantDialog

    Column(modifier = Modifier.fillMaxSize()) {
        task.id?.let {
            TopBar(title = stringResource(R.string.task_id, it),
                buttonIcon = Icons.Filled.ArrowBack,
                onButtonClicked = { navController.popBackStack() })
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color.LightGray,
                        shape = RoundedCornerShape(4.dp)
                    )
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    task.addressFrom?.address?.let {
                        TextView(
                            stringResource(R.string.from_address, it),
                            bottomPaddign = 8.dp
                        )
                    }
                    task.addressTo?.address?.let {
                        TextView(
                            stringResource(R.string.to_address, it),
                            topPaddign = 8.dp
                        )
                    }
                }
            }
            Divider(
                modifier = Modifier
                    .size(16.dp),
                color = Color.White
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color.LightGray,
                        // rounded corner to match with the OutlinedTextField
                        shape = RoundedCornerShape(4.dp)
                    )
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    task.contactFrom?.name?.let {
                        TextView(
                            stringResource(R.string.from_contact, it),
                        )
                    }
                    task.contactTo?.name?.let {
                        TextView(
                            stringResource(R.string.to_contact, it),
                        )
                    }
                }
            }
            TextView(
                stringResource(R.string.product_list), MaterialTheme.typography.titleLarge
            )
            LazyColumn(
                Modifier.weight(5f),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
            ) {
                items(items = task.product, itemContent = {
                    it.name?.let { it1 -> TextView(it1) }
                })
            }
            task.status?.let {
                MyButton(
                    stringResource(id = R.string.start), visibility = it == TaskStatus.ASSIGNED
                ) {
                    taskViewModel.startTask(task.id!!)
                }
                MyButton(
                    stringResource(id = R.string.take), visibility = it == TaskStatus.ON_WAY
                ) {
                    taskViewModel.pickupTask(task.id!!)
                }
                MyButton(
                    stringResource(id = R.string.delivered), visibility = it == TaskStatus.PICK_UP
                ) {
                    taskViewModel.deliverTask(task.id!!)
                }
                MyButton(
                    stringResource(id = R.string.confirm), visibility = it == TaskStatus.DELIVER
                ) {
                    taskViewModel.confirmTask(task.id!!)
                }
                MyButton(
                    stringResource(id = R.string.сomplete), visibility = it == TaskStatus.CONFIRM
                ) {
                    taskViewModel.showSmsDialog()
                }
                MyButton(
                    stringResource(id = R.string.cancel_task),
                    visibility = it != TaskStatus.FINISHED
                ) {
                    taskViewModel.showCancelReasonDialog()
                }
                MyButton(
                    stringResource(id = R.string.call), visibility = it != TaskStatus.FINISHED
                ) {
                    taskViewModel.showCallVariantDialog()
                }
            }

            TextView(
                stringResource(R.string.history), MaterialTheme.typography.titleLarge
            )
            LazyColumn(
                Modifier.weight(10f),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
            ) {
                items(items = task.histories, itemContent = {
                    TextView(it.action + " " + it.createdDate)
                })
            }
        }
        ErrorAlertDialog(
            show = isError.isError,
            onDismiss = taskViewModel::onDialogConfirm,
            onConfirm = taskViewModel::onDialogConfirm,
            text = isError.text
        )
        task.id?.let {
            InputTextAlertDialog(
                show = isSmsDialog,
                onDismiss = taskViewModel::dismissSmsDialog,
                onConfirm = taskViewModel::onConfirmWithSmsDialog,
                taskId = it
            )
            CancelReasonAlertDialog(
                show = isCancelReasonDialog,
                onDismiss = taskViewModel::hideCancelReasonDialog,
                onConfirm = taskViewModel::onCancelTaskDialog,
                taskId = it,
                text = stringResource(id = R.string.cancel_task)
            )
            CallVariantDialog(
                show = isCallVariantsDialog,
                onDismiss = taskViewModel::hideCallVariantDialog,
                onConfirm = taskViewModel::onCallVariantDialog,
                taskId = it,
                text = ""
            )
        }
    }
}

@Composable
fun TextView(
    text: String,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    startPaddign: Dp = 16.dp,
    endPaddign: Dp = 16.dp,
    topPaddign: Dp = 16.dp,
    bottomPaddign: Dp = 16.dp
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = text,
            style = style,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    PaddingValues(
                        start = startPaddign,
                        end = endPaddign,
                        top = topPaddign,
                        bottom = bottomPaddign
                    )
                )
                .wrapContentWidth(Alignment.Start)
        )
    }
}

@Composable
fun MyButton(text: String, visibility: Boolean = true, onClick: () -> Unit) {
    if (visibility) {
        Button(
            onClick = onClick,
            modifier = Modifier.padding(16.dp),
        ) {
//            Icon(
//                painterResource(icon),
//                contentDescription = "send",
//                modifier = Modifier.size(ButtonDefaults.IconSize)
//            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(text = text, style = MaterialTheme.typography.labelLarge)
        }
    }
}