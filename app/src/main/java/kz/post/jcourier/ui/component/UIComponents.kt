package kz.post.jcourier.ui.component

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import kz.post.jcourier.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title: String = "", buttonIcon: ImageVector, onButtonClicked: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = title
            )
        },
        navigationIcon = {
            IconButton(onClick = { onButtonClicked() }) {
                Icon(buttonIcon, contentDescription = "")
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarWithActions(
    title: String = "",
    backArrowIcon: ImageVector,
    callIcon: ImageVector,
    cancelIcon: ImageVector,
    onBackClicked: () -> Unit,
    onCallClicked: () -> Unit,
    onCancelClicked: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = title
            )
        },
        navigationIcon = {
            IconButton(onClick = { onBackClicked() }) {
                Icon(backArrowIcon, contentDescription = "")
            }
        },
        actions = {
            IconButton(onClick = { onCallClicked() }) {
                Icon(callIcon, contentDescription = stringResource(id = R.string.call))
            }
            IconButton(onClick = { onCancelClicked() }) {
                Icon(cancelIcon, contentDescription = stringResource(id = R.string.cancel_task))
            }
        }
    )
}