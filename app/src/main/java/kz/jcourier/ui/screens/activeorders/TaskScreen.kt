package kz.jcourier.ui.screens.activeorders

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kz.jcourier.R
import kz.jcourier.ui.component.TopBar
import kz.jcourier.viewmodel.TaskViewModel

@Composable
fun task(
    navController: NavController, taskViewModel: TaskViewModel = hiltViewModel()
) {
    val task = taskViewModel.uiState.task.value

    Column(modifier = Modifier.fillMaxSize()) {
        task.id?.let {
            TopBar(title = stringResource(R.string.task_id, it),
                buttonIcon = Icons.Filled.ArrowBack,
                onButtonClicked = { navController.popBackStack() })
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color.LightGray,
                        shape = RoundedCornerShape(4.dp)
                    )
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    task.addressFrom?.address?.let {
                        TextView(
                            stringResource(R.string.from_address, it),
                            bottomPaddign = 8.dp
                        )
                    }
                    task.addressTo?.address?.let {
                        TextView(
                            stringResource(R.string.to_address, it),
                            topPaddign = 8.dp
                        )
                    }
                }
            }
            Divider(
                modifier = Modifier
                    .size(16.dp),
                color = Color.White
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color.LightGray,
                        // rounded corner to match with the OutlinedTextField
                        shape = RoundedCornerShape(4.dp)
                    )
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    task.contactFrom?.name?.let {
                        TextView(
                            stringResource(R.string.from_contact, it),
                        )
                    }
                    task.contactTo?.name?.let {
                        TextView(
                            stringResource(R.string.to_contact, it),
                        )
                    }
                }
            }
            TextView(
                stringResource(R.string.product_list), MaterialTheme.typography.titleLarge
            )
            LazyColumn(
                Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
            ) {
                items(items = task.product, itemContent = {
                    TextView(it.toString())
                })
            }

            TextView(
                stringResource(R.string.history), MaterialTheme.typography.titleLarge
            )
            LazyColumn(
                Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
            ) {
                items(items = task.histories, itemContent = {
                    TextView(it.action)
                })
            }
            MyButton(stringResource(id = R.string.start), R.drawable.baseline_arrow_forward_24) {
                taskViewModel.startTask(task.id!!, task.id!!)
            }
        }
    }
}

@Composable
fun TextView(
    text: String,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    startPaddign: Dp = 16.dp,
    endPaddign: Dp = 16.dp,
    topPaddign: Dp = 16.dp,
    bottomPaddign: Dp = 16.dp
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = text,
            style = style,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    PaddingValues(
                        start = startPaddign,
                        end = endPaddign,
                        top = topPaddign,
                        bottom = bottomPaddign
                    )
                )
                .wrapContentWidth(Alignment.Start)
        )
    }
}

@Composable
fun MyButton(text: String, icon: Int, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.padding(16.dp),
    ) {
        Icon(
            painterResource(icon),
            contentDescription = "send",
            modifier = Modifier.size(ButtonDefaults.IconSize)
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text(text = text, style = MaterialTheme.typography.labelLarge)
    }
}