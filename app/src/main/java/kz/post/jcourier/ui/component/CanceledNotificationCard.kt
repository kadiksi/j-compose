package kz.post.jcourier.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kz.post.jcourier.app.theme.Orange500
import kz.post.jcourier.data.model.task.CanceledNotification
import kz.post.jcourier.ui.navigation.navigate
import kz.post.jcourier.utils.boldSpanStyles
import kz.post.jcourier.utils.toDateTimeFormat
import kz.post.jcourier.viewmodel.CanceledTasksViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CanceledNotificationCard(
    navController: NavHostController,
    notification: CanceledNotification,
) {
    val background = if (!notification.isRead) {
        Orange500
    } else {
        Color.LightGray
    }
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .width(IntrinsicSize.Max), onClick = {
        navController.navigate(
            DrawerScreens.TaskInfo.route, "taskId" to notification.attributes.taskId,
            "notificationId" to notification.id,
            "isRead" to notification.isRead
        )
    }) {
        Row {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = background)
                    .padding(start = 8.dp, top = 8.dp)
                    .weight(1f),
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge,
                text = "№ ${notification.attributes.taskId}"
            )
        }
        Row {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Transparent)
                    .padding(8.dp)
                    .weight(1f), text = notification.title.RU
            )
        }
        Row {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(color = Color.Transparent)
                    .weight(1f), text = AnnotatedString(
                    text = notification.message.RU, spanStyles = boldSpanStyles()
                )
            )
        }

        notification.createdDate.let {
            Row {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.Transparent)
                        .padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
                        .weight(1f),
                    color = Color.Red,
                    text = toDateTimeFormat(it),
                )
            }
        }
    }
}
