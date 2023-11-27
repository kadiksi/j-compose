package kz.post.jcourier.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kz.post.jcourier.R
import kz.post.jcourier.app.theme.Orange500
import kz.post.jcourier.data.model.shift.Shift
import kz.post.jcourier.ui.component.dialogs.ErrorAlertDialog
import kz.post.jcourier.ui.component.switcher.CustomSwitch
import kz.post.jcourier.viewmodel.CanceledTasksViewModel
import kz.post.jcourier.viewmodel.StatusViewModel

sealed class DrawerScreens(val title: Int, val route: String) {
    object ActiveOrders : DrawerScreens(R.string.active_orders, "active_orders")
    object Map : DrawerScreens(R.string.map, "map")
    object CanceledTasks : DrawerScreens(R.string.canceled_task, "canceled_task")
    object Statistic : DrawerScreens(R.string.archive_orders, "statistic")
    object TaskInfo : DrawerScreens(R.string.active_orders, "task_info")
    object AddCameraPhoto : DrawerScreens(R.string.camera, "camera_photo")
    object Exit : DrawerScreens(R.string.exit, "exit")
    object Politics : DrawerScreens(R.string.policy, "policy")
}

private val screens = listOf(
    DrawerScreens.ActiveOrders,
    DrawerScreens.Map,
    DrawerScreens.CanceledTasks,
    DrawerScreens.Statistic,
    DrawerScreens.Exit,
    DrawerScreens.Politics
)

@Composable
fun Drawer(
    modifier: Modifier = Modifier,
    onDestinationClicked: (route: String) -> Unit,
    shiftViewModel: StatusViewModel,
    canceledTaskViewModel: CanceledTasksViewModel
) {
    val courierModel = shiftViewModel.uiState.shift.value
    val isError by shiftViewModel.uiState.isError
    val canceledTaskCount = canceledTaskViewModel.uiState.canceledTaskCount

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
                    Text(
                        "${courierModel.firstname} ${courierModel.lastname}",
                        modifier = Modifier.padding(2.dp)
                    )
                    Divider()
                    Text("${courierModel.phone}", modifier = Modifier.padding(2.dp))
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
                    .padding(8.dp)
                    .clickable {
                        onDestinationClicked(screen.route)
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    text = stringResource(id = screen.title),
                    style = MaterialTheme.typography.bodyLarge
                )
                if (screen.route == DrawerScreens.CanceledTasks.route) {
                    Text(
                        modifier = Modifier
                            .background(Orange500, RoundedCornerShape(46.dp))
                            .padding(8.dp),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge,
                        text = canceledTaskCount.value.toString()
                    )
                }
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