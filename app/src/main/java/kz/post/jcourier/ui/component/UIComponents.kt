package kz.post.jcourier.ui.component

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import kz.post.jcourier.R
import kz.post.jcourier.data.model.task.TaskStatus
import kz.post.jcourier.ui.tasks.components.chooseFile
import kz.post.jcourier.utils.fileFromContentUri
import kz.post.jcourier.viewmodel.TaskViewModel
import java.io.File

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
    callIcon: ImageVector,
    onBackClicked: () -> Unit,
    onCallClicked: () -> Unit,
    taskViewModel: TaskViewModel
) {
    var selectedImageUris by remember {
        taskViewModel.uiState.fileList
    }
    val task by remember {
        taskViewModel.uiState.task
    }
    val context = LocalContext.current
    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uris ->
            val newList = arrayListOf<File>()
            uris.forEach {
                newList.add(fileFromContentUri(context, it))
            }
            selectedImageUris = newList

            newList.forEach {
                taskViewModel.onAddImageFile(it)
            }
        }
    )
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission Accepted: Do something
            chooseFile(multiplePhotoPickerLauncher)
        } else {
            chooseFile(multiplePhotoPickerLauncher)
        }
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
                Icon(callIcon, contentDescription = stringResource(id = R.string.call))
            }
            if(task.actions.contains(TaskStatus.PICK_UP) ||
                task.actions.contains(TaskStatus.COMPLETE))
            IconButton(onClick = {
                when (PackageManager.PERMISSION_GRANTED) {
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) -> {
                        chooseFile(multiplePhotoPickerLauncher)
                    }
                    else -> {
                        launcher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                    }
                }
            }) {
                Icon(
                    painterResource(id = R.drawable.baseline_insert_drive_file_24),
                    contentDescription = stringResource(id = R.string.update_files)
                )
            }
        }
    )
}