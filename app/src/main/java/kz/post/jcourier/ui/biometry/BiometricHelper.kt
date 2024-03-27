package kz.post.jcourier.ui.biometry


import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.ERROR_LOCKOUT
import androidx.biometric.BiometricPrompt.ERROR_NEGATIVE_BUTTON
import androidx.biometric.BiometricPrompt.ERROR_USER_CANCELED
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import kz.post.jcourier.R
import java.util.concurrent.Executors

class BiometricHelper {

    private var biometricManager: BiometricManager? = null
    private var biometricPrompt: BiometricPrompt? = null
    private var biometricPromptInfo: BiometricPrompt.PromptInfo? = null

    private val executor = Executors.newSingleThreadExecutor()
    private var authenticationCallback: BiometricPrompt.AuthenticationCallback? = null

    private var callback: ((BiometricState) -> Unit)? = null

    constructor(activity: FragmentActivity) {
        biometricManager = BiometricManager.from(activity)
        authenticationCallback = createBiometricListener(activity)
        biometricPrompt = BiometricPrompt(activity, executor, authenticationCallback ?: return)
        biometricPromptInfo = createPromptInfo(activity)
    }

    constructor(fragment: Fragment) {
        biometricManager = BiometricManager.from(fragment.requireContext())
        authenticationCallback = createBiometricListener(fragment.activity ?: return)
        biometricPrompt = BiometricPrompt(fragment, executor, authenticationCallback ?: return)
        biometricPromptInfo = createPromptInfo(fragment.requireContext())
    }

    fun isBiometricEnabled(): Boolean =
        biometricManager?.canAuthenticate() != BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE
                && biometricManager?.canAuthenticate() != BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE

    fun setTouchIdCallback(callback: (BiometricState) -> Unit) {
        this.callback = callback
    }

    fun showTouchId(context: Context) {
        when (biometricManager?.canAuthenticate()) {
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE,
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> return
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> showEnrollDialog(context)
            BiometricManager.BIOMETRIC_SUCCESS -> {
                biometricPrompt?.authenticate(biometricPromptInfo ?: return)
            }
        }
    }

    private fun createBiometricListener(activity: FragmentActivity) =
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                if (errorCode == ERROR_NEGATIVE_BUTTON ||
                    errorCode == ERROR_USER_CANCELED
                ) {
                    biometricPrompt?.cancelAuthentication()
                }
                if (errorCode == ERROR_LOCKOUT) {
                    activity.runOnUiThread { callback?.invoke(BiometricState.TooManyAttempts) }
                }
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                activity.runOnUiThread { callback?.invoke(BiometricState.Success) }
            }
        }

    private fun createPromptInfo(context: Context) = BiometricPrompt.PromptInfo.Builder()
        .setTitle(context.getString(R.string.touch_id))
        .setSubtitle(context.getString(R.string.fingerprint_touch_scanner))
        .setDescription(context.getString(R.string.fingerprint_prompt_description))
        .setNegativeButtonText(context.getString(R.string.cancel))
        .build()

    private fun showEnrollDialog(context: Context) {
        AlertDialog.Builder(context)
            .setTitle(R.string.login_with_touch_id)
            .setMessage(R.string.touch_id_enroll)
            .setPositiveButton(R.string.settings) { _, _ ->
                callback?.invoke(BiometricState.EnrollTouchId)
            }
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}

sealed class BiometricState {
    object Success : BiometricState()
    object EnrollTouchId : BiometricState()
    object TooManyAttempts : BiometricState()
}