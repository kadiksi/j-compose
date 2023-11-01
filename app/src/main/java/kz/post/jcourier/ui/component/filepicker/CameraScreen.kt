@file:OptIn(ExperimentalPermissionsApi::class)

package kz.post.jcourier.ui.component.filepicker

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kz.post.jcourier.ui.component.filepicker.photocapture.CameraScreen
import kz.post.jcourier.ui.component.filepicker.photocapture.nopermission.NoPermissionScreen

@Composable
fun CameraMainScreen(navController: NavHostController) {

    val cameraPermissionState: PermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)

    MainContent(
        hasPermission = cameraPermissionState.status.isGranted,
        onRequestPermission = cameraPermissionState::launchPermissionRequest,
        navController
    )
}

@Composable
private fun MainContent(
    hasPermission: Boolean,
    onRequestPermission: () -> Unit,
    navController: NavHostController
) {

    if (hasPermission) {
        CameraScreen(navController)
    } else {
        NoPermissionScreen(onRequestPermission)
    }
}
