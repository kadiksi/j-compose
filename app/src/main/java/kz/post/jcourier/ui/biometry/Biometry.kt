package kz.post.jcourier.ui.biometry

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import timber.log.Timber

@Composable
fun biometry(): BiometricPrompt {
    val context = LocalContext.current
    val biometricManager = remember { BiometricManager.from(context) }
    val executor = remember { ContextCompat.getMainExecutor(context) }

    val isBiometricAvailable = remember {
        biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
    }
    when (isBiometricAvailable) {
        BiometricManager.BIOMETRIC_SUCCESS -> {
            Timber.e("biometry Biometric features are available")
        }

        BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
            Timber.e("biometry No biometric features available on this device")
        }

        BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
            Timber.e("biometry Biometric features are currently unavailable.")
        }

        BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED -> {
            Timber.e("biometry Biometric features available but a security vulnerability has been discovered")
        }

        BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> {
            Timber.e("biometry Biometric features are currently unavailable because the specified options are incompatible with the current Android version..")
        }

        BiometricManager.BIOMETRIC_STATUS_UNKNOWN -> {
            Timber.e("biometry Unable to determine whether the user can authenticate using biometrics")
        }

        BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
            Timber.e("biometry The user can't authenticate because no biometric or device credential is enrolled.")
        }
    }
    val biometricPrompt = BiometricPrompt(
        context as FragmentActivity,
        executor,
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Timber.e("onAuthenticationError")
            }

            @RequiresApi(Build.VERSION_CODES.R)
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Timber.e("onAuthenticationSucceeded")
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Timber.e("onAuthenticationFailed")
            }
        }
    )
    return biometricPrompt
}