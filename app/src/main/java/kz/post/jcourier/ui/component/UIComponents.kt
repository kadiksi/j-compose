package kz.post.jcourier.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import kz.post.jcourier.R
import kz.post.jcourier.data.model.task.TaskStatus
import kz.post.jcourier.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title: String = "", buttonIcon: ImageVector, onButtonClicked: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = title
            )
        },
        navigationIcon = {
            IconButton(onClick = { onButtonClicked() }) {
                Icon(buttonIcon, contentDescription = "")
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarWithActions(
    title: String = "",
    backArrowIcon: ImageVector,
    onBackClicked: () -> Unit,
    onCallClicked: () -> Unit,
    onChooseFileClicked: () -> Unit,
    taskViewModel: TaskViewModel
) {
    val context = LocalContext.current
    val task by remember {
        taskViewModel.uiState.task
    }

    TopAppBar(
        title = {
            Text(
                text = title
            )
        },
        navigationIcon = {
            IconButton(onClick = { onBackClicked() }) {
                Icon(backArrowIcon, contentDescription = "")
            }
        },
        actions = {
            IconButton(onClick = { onCallClicked() }) {
                Icon(Icons.Filled.Call, contentDescription = stringResource(id = R.string.call))
            }
            if(task.actions.contains(TaskStatus.PICK_UP) ||
                task.actions.contains(TaskStatus.COMPLETE))
            IconButton(onClick = {
                onChooseFileClicked()
            }) {
                Icon(
                    painterResource(id = R.drawable.baseline_insert_drive_file_24),
                    contentDescription = stringResource(id = R.string.update_files)
                )
            }
        }
    )
}