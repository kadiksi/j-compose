package kz.post.jcourier.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kz.post.jcourier.R
import kz.post.jcourier.data.model.task.Task
import kz.post.jcourier.ui.navigation.navigate
import kz.post.jcourier.utils.boldSpanStyles

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskCard(
    navController: NavHostController,
    task: Task
) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .width(IntrinsicSize.Max), onClick = {
        navController.navigate(
            DrawerScreens.Settings.route,
            "task" to task
        )
    }
    ) {
        Row {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, top = 8.dp)
                    .weight(1f),
                color = Color.Red,
                text = "№ ${task.orderId.toString()} status: ${task.status}"
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
                        id = R.string.a_point,
                        task.addressFrom?.address.toString()
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
                        task.addressTo?.address.toString()
                    ), spanStyles = boldSpanStyles()
                )
            )
        }
    }
}