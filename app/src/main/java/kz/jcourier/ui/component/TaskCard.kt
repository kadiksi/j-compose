package kz.jcourier.ui.component

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
import androidx.navigation.compose.rememberNavController
import kz.jcourier.data.model.task.Task
import kz.jcourier.ui.containers.ChatActivity
import kz.jcourier.ui.navigation.navigate

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
        navController.navigate(DrawerScreens.Settings.route,
        "task" to task)
//        context.startActivity(Intent(context, ChatActivity::class.java))
    }
    ) {
        Row {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .weight(1f),
                text = task.id.toString()
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