package kz.post.jcourier.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.post.jcourier.R
import kz.post.jcourier.ui.component.switcher.CustomSwitch

sealed class DrawerScreens(val title: Int, val route: String) {
    object ActiveOrders : DrawerScreens(R.string.active_orders, "active_orders")
    object Map : DrawerScreens(R.string.map, "map")
    object Notifications : DrawerScreens(R.string.notifications, "notifications")
    object Statistic : DrawerScreens(R.string.statistic, "statistic")
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
    modifier: Modifier = Modifier, onDestinationClicked: (route: String) -> Unit
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(start = 16.dp, top = 24.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(R.drawable.baseline_error_24),
                contentDescription = "Profile Icon",
                modifier = Modifier.size(64.dp)
            )
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = "name",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                )
                Text(
                    text = "address",
                    style = TextStyle(
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                )

                var isToggle by remember { mutableStateOf(false) }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                ) {
                    CustomSwitch(checked = isToggle) {
                        isToggle = it
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
                    }
            ) {
                Text(
                    text = stringResource(id = screen.title),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}