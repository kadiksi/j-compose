package kz.post.jcourier.ui.tasks.components

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kz.post.jcourier.R
import kz.post.jcourier.viewmodel.TaskViewModel

@Composable
fun TaskFileView(taskViewModel: TaskViewModel) {
    val selectedImageUris by remember {
        taskViewModel.uiState.fileList
    }
    Column {
        if (selectedImageUris.isNotEmpty()) {
            LazyRow(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top,
                modifier = Modifier.padding(16.dp)
            ) {
                itemsIndexed(selectedImageUris) { index, uri ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        AsyncImage(
                            model = uri,
                            contentDescription = null,
                            modifier = Modifier
                                .size(86.dp)
                                .padding(4.dp),
                            contentScale = ContentScale.Crop
                        )
                        IconButton(
                            onClick = {
                                taskViewModel.onRemoveImageFile(index)
                            },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                        ) {
                            Icon(
                                Icons.Filled.Delete,
                                contentDescription = stringResource(id = R.string.delate),
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .size(18.dp),
                                tint = Color.Red
                            )
                        }
                    }
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
