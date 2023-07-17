package kz.post.jcourier.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kz.post.jcourier.R
import kz.post.jcourier.data.model.shift.Shift
import kz.post.jcourier.ui.component.dialogs.ErrorAlertDialog
import kz.post.jcourier.ui.component.switcher.CustomSwitch
import kz.post.jcourier.viewmodel.StatusViewModel

sealed class DrawerScreens(val title: Int, val route: String) {
    object ActiveOrders : DrawerScreens(R.string.active_orders, "active_orders")
    object Map : DrawerScreens(R.string.map, "map")
    object Notifications : DrawerScreens(R.string.notifications, "notifications")
    object Statistic : DrawerScreens(R.string.archive_orders, "statistic")
    object Settings : DrawerScreens(R.string.settings, "settings")
    object Exit : DrawerScreens(R.string.exit, "exit")
}

private val screens = listOf(
    DrawerScreens.ActiveOrders,
    DrawerScreens.Map,
    DrawerScreens.Notifications,
    DrawerScreens.Statistic,
    DrawerScreens.Settings,
    DrawerScreens.Exit
)

@Composable
fun Drawer(
    modifier: Modifier = Modifier,
    onDestinationClicked: (route: String) -> Unit,
    shiftViewModel: StatusViewModel
) {
    val courierModel = shiftViewModel.uiState.shift.value
    val isError by shiftViewModel.uiState.isError

    Column(
        modifier
            .fillMaxSize()
            .padding(start = 16.dp, top = 24.dp)
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(courierModel.info ?: "", modifier = Modifier.padding(2.dp))
                    Divider()
                    var isShift = courierModel.status == Shift.ON_SHIFT
                    CustomSwitch(checked = isShift) {
                        if (isShift) {
                            shiftViewModel.setShift(Shift.FREE)
                        } else {
                            shiftViewModel.setShift(Shift.ON_SHIFT)
                        }
                        isShift = it
                    }
                }
            }
        }
        screens.forEach { screen ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable {
                        onDestinationClicked(screen.route)
                    }) {
                Text(
                    text = stringResource(id = screen.title),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
        ErrorAlertDialog(
            show = isError.isError,
            onDismiss = shiftViewModel::onDialogConfirm,
            onConfirm = shiftViewModel::onDialogConfirm,
            text = isError.text
        )
    }
}