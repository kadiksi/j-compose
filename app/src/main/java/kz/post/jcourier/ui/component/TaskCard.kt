package kz.post.jcourier.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kz.post.jcourier.R
import kz.post.jcourier.data.model.task.Task
import kz.post.jcourier.data.model.task.TaskStatus
import kz.post.jcourier.ui.navigation.navigate
import kz.post.jcourier.utils.boldSpanStyles
import kz.post.jcourier.utils.toDateTimeFormat
import kz.post.jcourier.utils.toTimeFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskCard(
    navController: NavHostController,
    task: Task
) {
    val statusList = stringArrayResource(id = R.array.statuses)

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .width(IntrinsicSize.Max), onClick = {
        navController.navigate(
            DrawerScreens.TaskInfo.route,
            "taskId" to task.id
        )
    }
    ) {
        Row {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, top = 8.dp)
                    .weight(1f),
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge,
                text = "№ ${task.orderId.toString()} : ${getStatus(statusList, task.status)}"
            )
        }
        Row {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .weight(1f),
                text = task.contactFrom?.companyName.toString()
            )
        }
        Row {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .weight(1f),
                text = AnnotatedString(
                    text = stringResource(
                        id = R.string.a_point, task.getAddress(task.addressFrom)
                    ), spanStyles = boldSpanStyles()
                )
            )
        }

        Row {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .weight(1f),
                text = AnnotatedString(
                    text = stringResource(
                        id = R.string.b_point,
                        task.getAddress(task.addressTo)
                    ), spanStyles = boldSpanStyles()
                )
            )
        }

        task.contactTo?.dateFrom?.let {
            Row {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
                        .weight(1f),
                    color = Color.Red,
                    text = toDateTimeFormat(it) + "-" + toTimeFormat(task.contactTo?.dateTo!!),
                )
            }
        }
    }
}

fun getStatus(statusList: Array<String>, status: TaskStatus?): String {
    return when (status) {
        TaskStatus.NEW -> statusList[0]
        TaskStatus.ON_WAY -> statusList[1]
        TaskStatus.PICK_UP -> statusList[2]
        TaskStatus.DELIVER -> statusList[3]
        TaskStatus.CONFIRM -> statusList[4]
        else -> ""
    }
}
