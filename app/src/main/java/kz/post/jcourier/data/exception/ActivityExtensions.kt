package kz.post.jcourier.data.exception

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import kz.post.jcourier.R

fun FragmentActivity.showErrorDialog(
    message: Int,
    onButtonClicked: (() -> Unit)? = null
): AlertDialog? {
    if (isFinishing) return null

    return showSimpleDialog(
        title = getString(R.string.error),
        message = getString(message),
        onButtonClicked = onButtonClicked
    )
}

fun FragmentActivity.showSimpleDialog(
    title: String? = null,
    message: String? = null,
    onButtonClicked: (() -> Unit)? = null
): AlertDialog? {
    if (isFinishing) return null

    return AlertDialog.Builder(this)
        .also { dialog ->
            title?.let { dialog.setTitle(it.toString()) }
            message?.let { dialog.setMessage(it.toString()) }
        }
        .setPositiveButton(R.string.ok, null)
        .setCancelable(false)
        .setOnDismissListener { onButtonClicked?.invoke() }
        .show()
}

fun FragmentActivity.activityForResult(onResult: (Intent?) -> Unit): ActivityResultLauncher<Intent> {
    return registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val data = result?.data ?: return@registerForActivityResult
        onResult.invoke(data)
    }
}