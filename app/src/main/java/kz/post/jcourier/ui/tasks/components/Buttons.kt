package kz.post.jcourier.ui.tasks.components

import android.opengl.Visibility
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kz.post.jcourier.R
import kz.post.jcourier.data.model.task.Task
import kz.post.jcourier.data.model.task.TaskStatus
import kz.post.jcourier.utils.toDateTimeFormat
import kz.post.jcourier.utils.toTimeFormat
import kz.post.jcourier.viewmodel.TaskViewModel

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

@Composable
fun TaskOptionButtons(taskViewModel: TaskViewModel, task: Task) {
    val context = LocalContext.current
    MyButton(
        stringResource(id = R.string.start), visibility = task.actions.contains(TaskStatus.ON_WAY)
    ) {
        taskViewModel.setStatus(task.id!!, TaskStatus.ON_WAY)
    }
    MyButton(
        stringResource(id = R.string.take), visibility = task.actions.contains(TaskStatus.PICK_UP)
    ) {
        taskViewModel.uploadPickUpFiles(task.id!!, context)
    }
    MyButton(
        stringResource(id = R.string.delivered),
        visibility = task.actions.contains(TaskStatus.DELIVER)
    ) {
        taskViewModel.setStatus(task.id!!, TaskStatus.DELIVER)
    }
    if(task.actions.contains(TaskStatus.CONFIRM) && task.finalRoute == true) {
        if(taskViewModel.uiState.timer.value.canSendSms){
            MyButton(
                stringResource(id = R.string.confirm),
            ) {
                taskViewModel.setStatus(task.id!!, TaskStatus.CONFIRM)
            }
        } else {
            Row {
                if (!taskViewModel.uiState.timer.value.canSendSms){
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, top = 8.dp, bottom = 8.dp)
                            .weight(1f),
                        color = Color.Red,
                        style = MaterialTheme.typography.bodyLarge,
                        text = stringResource(id = R.string.retry_send, taskViewModel.uiState.timer.value.time)
                    )
                }
            }
        }
    }
    MyButton(
        stringResource(id = R.string.complete),
        visibility = task.actions.contains(TaskStatus.COMPLETE)
    ) {
        if(task.finalRoute == true)
            taskViewModel.showSmsDialog()
        else
            taskViewModel.completeWithFiles(task.id!!, null)
    }
    MyButton(
        stringResource(id = R.string.cancel_task),
        visibility =
        task.cancellationTypes?.isNotEmpty() == true
                && task.actions.contains(TaskStatus.CANCEL)
    ) {
        taskViewModel.showCancelReasonDialog()
    }
}