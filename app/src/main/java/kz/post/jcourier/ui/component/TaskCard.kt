package kz.post.jcourier.ui.component

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kz.post.jcourier.data.model.task.Task
import kz.post.jcourier.ui.navigation.navigate

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
                    .padding(start = 8.dp, top =  8.dp)
                    .weight(1f),
                text = "№ ${task.id.toString()} status: ${task.status}"
            )
        }
        Row {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .weight(1f),
                text = task.addressFrom?.address.toString()
            )
        }
        Row {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .weight(1f),
                text = task.addressTo?.address.toString()
            )
        }
    }
}