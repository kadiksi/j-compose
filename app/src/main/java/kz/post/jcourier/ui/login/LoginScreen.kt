package kz.post.jcourier.ui.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kz.post.jcourier.R
import kz.post.jcourier.ui.component.dialogs.ErrorAlertDialog
import kz.post.jcourier.ui.containers.PhoneMaskTransformation
import kz.post.jcourier.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel()
) {
    var emailText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    val isError by viewModel.uiState.isError

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        OutlinedTextField(
            value = emailText,
            onValueChange = {
                if (it.length > 10) {
                    return@OutlinedTextField
                }
                emailText = it
            },
            label = { Text("Phone") },
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
            label = { Text("Password") },
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
            onClick = { viewModel.login(emailText, passwordText) },
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
        ) {
            Text(stringResource(id = R.string.login))
        }
        ErrorAlertDialog(
            show = isError,
            onDismiss = viewModel::onDialogDismiss,
            onConfirm = viewModel::onDialogConfirm,
            stringResource(id = R.string.login_error)
        )
    }
}