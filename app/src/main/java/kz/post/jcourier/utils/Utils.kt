package kz.post.jcourier.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.SystemClock
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import kz.post.jcourier.R
import org.w3c.dom.Text

fun String.addCharAtIndex(char: Char, index: Int) =
    StringBuilder(this).apply { insert(index, char) }.toString()

fun Context.hasPermissionAccessFineLocation(): Boolean {
    return ContextCompat.checkSelfPermission(
        this, Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}

fun Context.isGpsEnabled(): Boolean {
    val manager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return manager.isProviderEnabled(LocationManager.GPS_PROVIDER)
}
val locationPermissions = arrayOf(
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.ACCESS_COARSE_LOCATION,
)

fun Activity.openAppSettingsActivity(requestCode: Int) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    intent.data = Uri.fromParts("package", packageName, null)
    this.startActivityForResult(intent, requestCode)
}

fun ComponentActivity.registerPermissionsActivityResult(
    onResult: (Map<String, Boolean>?) -> Unit
): ActivityResultLauncher<Array<String>> {
    return registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
        onResult.invoke(result)
    }
}

inline fun Activity?.requestLocationPermission(
    launcher: ActivityResultLauncher<Array<String>>,
    settingsAppRequestCode: Int,
    onPermissionGranted: () -> Unit,
): AlertDialog? {
    if (this == null || isFinishing) return null

    when {
        hasPermissionAccessFineLocation() -> {
            if (isGpsEnabled()) onPermissionGranted.invoke()
            else return showEnableGpsDialog(this)
        }
        ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) -> {
            return AlertDialog.Builder(this)
                .setTitle("Трубуется доступ к местоположению")
                .setMessage("Перейдите в \"Настройки\", чтобы предоставить доступ к местоположению")
                .setSafePositiveButton(this, R.string.settings) { _, _ ->
                    openAppSettingsActivity(settingsAppRequestCode)
                }
                .show()
        }
        else -> launcher.launch(locationPermissions)
    }

    return null
}

fun Activity.showEnableGpsDialog(
    activity: Activity
): AlertDialog? {
    if (isFinishing) return null

    return AlertDialog.Builder(this)
        .setTitle("GPS не включен")
        .setMessage("Перейдите в \"Настройки\", чтобы включить GPS")
        .setSafePositiveButton(this, R.string.settings) { _, _ ->
            activity.openGpsSettingsActivity()
        }
        .show()
}

fun AlertDialog.Builder.setSafePositiveButton(
    context: Context,
    text: Int,
    onSafeClick: ((DialogInterface, Int) -> Unit)? = null
) = apply {
    setSafeButton(this, onSafeClick, true, context, text)
}

private fun setSafeButton(
    builder: AlertDialog.Builder,
    onSafeClick: ((DialogInterface, Int) -> Unit)? = null,
    isPositive: Boolean,
    context: Context,
    text: Int,
) {
    val safeClickListener = SafeDialogButtonClickListener { dialogInterface, which ->
        onSafeClick?.invoke(dialogInterface, which)
    }
    if (isPositive) {
        builder.setPositiveButton(context.getString(text), safeClickListener)
    } else {
        builder.setNegativeButton(context.getString(text), safeClickListener)
    }
}

class SafeDialogButtonClickListener(
    private var defaultInterval: Int = 1000,
    private val safeOnClick: (DialogInterface, Int) -> Unit
) : DialogInterface.OnClickListener {

    private var lastTime = 0L

    override fun onClick(dialog: DialogInterface, which: Int) {
        if (SystemClock.elapsedRealtime() - lastTime < defaultInterval) {
            return
        }
        lastTime = SystemClock.elapsedRealtime()
        safeOnClick(dialog, which)
    }
}

fun Activity.openGpsSettingsActivity() {
    startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
}