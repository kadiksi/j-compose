package kz.post.jcourier.ui.tasks.components

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
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
