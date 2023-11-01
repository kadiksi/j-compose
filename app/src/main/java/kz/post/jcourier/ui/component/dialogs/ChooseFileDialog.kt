package kz.post.jcourier.ui.component.dialogs

import android.content.pm.PackageManager
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import kz.post.jcourier.R
import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import kz.post.jcourier.ui.component.DrawerScreens
import kz.post.jcourier.ui.tasks.components.chooseFile
import kz.post.jcourier.utils.fileFromContentUri
import kz.post.jcourier.viewmodel.TaskViewModel
import java.io.File

@Composable
fun ChooseFileDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    taskViewModel: TaskViewModel,
    navController: NavHostController,
) {
    val context = LocalContext.current
    var selectedImageUris by remember {
        taskViewModel.uiState.fileList
    }
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
    if (show) {
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = onDismiss) { Text(text = stringResource(id = R.string.cancel)) }
            },
            text = {
                Column {
                    Button(
                        onClick = {
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
                            taskViewModel.hideChooseFileDialog()
                        },
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth()
                    ) {
                        Text(stringResource(id = R.string.gallery))
                    }
                    Button(
                        onClick = {
                            navController.navigate(
                                DrawerScreens.AddCameraPhoto.route,
                            )
                            taskViewModel.hideChooseFileDialog()
                        },
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth()
                    ) {
                        Text(stringResource(id = R.string.camera))
                    }
                }
            },
        )
    }
}