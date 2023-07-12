package kz.post.jcourier.ui.tasks.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kz.post.jcourier.R
import kz.post.jcourier.data.model.task.Task
import kz.post.jcourier.data.model.task.TaskStatus
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
fun TaskOptionButtons(taskViewModel: TaskViewModel, task: Task, taskStatus: TaskStatus) {

    MyButton(
        stringResource(id = R.string.start), visibility = task.actions.contains(TaskStatus.ON_WAY)
    ) {
        taskViewModel.setStatus(task.id!!, TaskStatus.ON_WAY)
    }
    MyButton(
        stringResource(id = R.string.take), visibility = task.actions.contains(TaskStatus.PICK_UP)
    ) {
        taskViewModel.setStatus(task.id!!, TaskStatus.PICK_UP)
    }
    MyButton(
        stringResource(id = R.string.delivered),
        visibility = task.actions.contains(TaskStatus.DELIVER)
    ) {
        taskViewModel.setStatus(task.id!!, TaskStatus.DELIVER)
    }
    MyButton(
        stringResource(id = R.string.confirm),
        visibility = task.actions.contains(TaskStatus.CONFIRM)
    ) {
        taskViewModel.setStatus(task.id!!, TaskStatus.CONFIRM)
    }
    MyButton(
        stringResource(id = R.string.complete),
        visibility = task.actions.contains(TaskStatus.COMPLETE)
    ) {
        taskViewModel.showSmsDialog()
    }
    MyButton(
        stringResource(id = R.string.cancel_task),
        visibility = task.cancellationReasons.isNotEmpty()
    ) {
        taskViewModel.showCancelReasonDialog()
    }
}