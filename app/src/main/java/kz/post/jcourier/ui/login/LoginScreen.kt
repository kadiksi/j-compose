package kz.post.jcourier.ui.login

import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kz.post.jcourier.EntryPointActivity
import kz.post.jcourier.R
import kz.post.jcourier.data.exception.showErrorDialog
import kz.post.jcourier.ui.biometry.BiometricHelper
import kz.post.jcourier.ui.biometry.BiometricState
import kz.post.jcourier.ui.component.dialogs.ErrorAlertDialog
import kz.post.jcourier.ui.containers.PhoneMaskTransformation
import kz.post.jcourier.viewmodel.LoginViewModel

@Composable
fun LoginScreen(fragmentActivity: EntryPointActivity,
                activityResultLauncher : ActivityResultLauncher<Intent>,
                viewModel: LoginViewModel = viewModel()
) {
    var phone by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    val isError by viewModel.uiState.isError
    val isRefreshToken by viewModel.uiState.isRefreshToken
    val biometricHelper by lazy { BiometricHelper(fragmentActivity) }

    LaunchedEffect(true) {
        if(isRefreshToken) {
            if (biometricHelper.isBiometricEnabled() && viewModel.hasSavedCredentials()) {
                biometricHelper.showTouchId(fragmentActivity)
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        OutlinedTextField(
            value = phone,
            onValueChange = {
                if (it.length > 10) {
                    return@OutlinedTextField
                }
                phone = it
            },
            label = { Text(text = stringResource(id = R.string.phone)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone
            ),

            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = PhoneMaskTransformation()
        )

        OutlinedTextField(
            value = passwordText,
            onValueChange = { passwordText = it },
            label = { Text(text = stringResource(id = R.string.password)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    Icon(
                        imageVector = if (passwordVisibility)
                            ImageVector.vectorResource(R.drawable.baseline_visibility_24) else
                            ImageVector.vectorResource(
                                R.drawable.baseline_visibility_off_24
                            ),
                        contentDescription = if (passwordVisibility) "Hide password" else "Show password"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Button(
            onClick = { viewModel.login(phone, passwordText) },
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
        ) {
            Text(stringResource(id = R.string.login))
        }
        val uriHandler = LocalUriHandler.current
        Text(
            text = stringResource(id = R.string.policy),
            color = Color.Blue,
            modifier = Modifier
                .clickable {
                    uriHandler.openUri("https://jpost.kz/private-policy/")
                }
                .padding(16.dp)
                .fillMaxWidth()
        )
        if(isRefreshToken)
            if(biometricHelper.isBiometricEnabled() && viewModel.hasSavedCredentials()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,

                    ) {
                    Image(
                        modifier = Modifier
                            .size(126.dp)
                            .clickable {
                                biometricHelper.setTouchIdCallback {
                                    when (it) {
                                        BiometricState.Success -> viewModel.onFingerprintRecognized()
                                        BiometricState.EnrollTouchId -> startFingerPrintActivity(
                                            activityResultLauncher
                                        )

                                        BiometricState.TooManyAttempts -> fragmentActivity.showErrorDialog(
                                            R.string.fingerprint_error_lockout
                                        )
                                    }
                                }
                                biometricHelper.showTouchId(fragmentActivity)
                            },
                        painter = painterResource(R.drawable.ic_fingerprint),
                        contentDescription = "Content description for visually impaired"
                    )
                }
            }
        ErrorAlertDialog(
            show = isError,
            onDismiss = viewModel::onDialogDismiss,
            onConfirm = viewModel::onDialogConfirm,
            stringResource(id = R.string.login_error)
        )
    }
}

private fun startFingerPrintActivity(activityResultLauncher: ActivityResultLauncher<Intent>) {

    when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
            activityResultLauncher.launch(Intent(Settings.ACTION_BIOMETRIC_ENROLL))
        }
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
            activityResultLauncher.launch(Intent(Settings.ACTION_SECURITY_SETTINGS))
        }
    }
}