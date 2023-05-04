package kz.jcourier.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kz.jcourier.ui.component.SimpleAlertDialog
import kz.jcourier.ui.containers.PhoneMaskTransformation
import kz.jcourier.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    show: Boolean,
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
                        imageVector = if (passwordVisibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
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
            Text("Log In")
        }
        SimpleAlertDialog(
            show = isError,
            onDismiss = viewModel::onDialogDismiss,
            onConfirm = viewModel::onDialogConfirm
        )
    }
}