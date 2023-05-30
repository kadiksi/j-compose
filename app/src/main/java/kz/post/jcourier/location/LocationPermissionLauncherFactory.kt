package kz.post.jcourier.location

import androidx.activity.result.ActivityResultLauncher

interface LocationPermissionLauncherFactory {
    val locationPermissionLauncher: ActivityResultLauncher<Array<String>>
}