package kz.post.jcourier.ui.tasks.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import kz.post.jcourier.R
import kz.post.jcourier.data.model.task.Task
import kz.post.jcourier.ui.component.dialogs.CallVariantDialog
import kz.post.jcourier.ui.component.dialogs.CancelReasonAlertDialog
import kz.post.jcourier.ui.component.dialogs.ErrorAlertDialog
import kz.post.jcourier.ui.component.dialogs.InputTextAlertDialog
import kz.post.jcourier.viewmodel.ErrorModel
import kz.post.jcourier.viewmodel.TaskViewModel

@Composable
fun TaskDialogs(
    taskViewModel: TaskViewModel,
    isError: ErrorModel,
    task: Task,
    isSmsDialog: Boolean,
    isCancelReasonDialog: Boolean,
    isCallVariantsDialog: Boolean
) {
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