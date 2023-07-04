package kz.post.jcourier.ui.tasks.components

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.net.toFile
import coil.compose.AsyncImage
import kz.post.jcourier.R
import kz.post.jcourier.data.model.task.Task
import kz.post.jcourier.utils.fileFromContentUri
import kz.post.jcourier.utils.toMultipart
import kz.post.jcourier.viewmodel.TaskViewModel
import java.io.File


@Composable
fun TaskFileView(taskViewModel: TaskViewModel) {
    var selectedImageUris by remember {
        mutableStateOf<List<Uri>>(emptyList())
    }
    val context = LocalContext.current
    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uris ->
            val newList = arrayListOf<Uri>()
            newList.addAll(selectedImageUris)
            newList.addAll(uris)
            selectedImageUris = newList

            newList.forEach {
                taskViewModel.onAddImageFile(fileFromContentUri(context,it))
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
            // Permission Denied: Do something
        }
    }
    Column {
        Button(
            onClick = {
                when (PackageManager.PERMISSION_GRANTED) {
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) -> {
                        // Some works that require permission
                        chooseFile(multiplePhotoPickerLauncher)
                    }
                    else -> {
                        launcher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                    }
                }
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.update_files),
                style = MaterialTheme.typography.labelLarge
            )
        }
        if (selectedImageUris.isNotEmpty()) {
            LazyRow(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top,
                modifier = Modifier.padding(16.dp)
            ) {
                items(selectedImageUris) { uri ->
                    AsyncImage(
                        model = uri,
                        contentDescription = null,
                        modifier = Modifier
                            .size(86.dp)
                            .padding(4.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}

fun chooseFile(multiplePhotoPickerLauncher: ManagedActivityResultLauncher<PickVisualMediaRequest, List<@JvmSuppressWildcards Uri>>) {
    multiplePhotoPickerLauncher.launch(
        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
    )
}
