package kz.post.jcourier.ui.tasks.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
        stringResource(id = R.string.start), visibility = taskStatus == TaskStatus.ASSIGNED
    ) {
        taskViewModel.startTask(task.id!!)
    }
    MyButton(
        stringResource(id = R.string.take), visibility = taskStatus == TaskStatus.ON_WAY
    ) {
        taskViewModel.pickupTask(task.id!!)
    }
    MyButton(
        stringResource(id = R.string.delivered), visibility = taskStatus == TaskStatus.PICK_UP
    ) {
        taskViewModel.deliverTask(task.id!!)
    }
    MyButton(
        stringResource(id = R.string.confirm), visibility = taskStatus == TaskStatus.DELIVER
    ) {
        taskViewModel.confirmTask(task.id!!)
    }
    MyButton(
        stringResource(id = R.string.сomplete), visibility = taskStatus == TaskStatus.CONFIRM
    ) {
        taskViewModel.showSmsDialog()
    }
    MyButton(
        stringResource(id = R.string.cancel_task),
        visibility = taskStatus != TaskStatus.FINISHED
    ) {
        taskViewModel.showCancelReasonDialog()
    }
    MyButton(
        stringResource(id = R.string.call), visibility = taskStatus != TaskStatus.FINISHED
    ) {
        taskViewModel.showCallVariantDialog()
    }
}