package kz.jcourier.ui.screens.activeorders

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kz.jcourier.R
import kz.jcourier.ui.component.TaskCard
import kz.jcourier.ui.component.TopBar
import kz.jcourier.viewmodel.TaskViewModel


@Composable
fun task(
    navController: NavController,
    taskViewModel: TaskViewModel = hiltViewModel()
) {
    val task = taskViewModel.uiState.task.value

    Column(modifier = Modifier.fillMaxSize()) {
        task.id?.let {
            TopBar(
                title = stringResource(R.string.task_id, it),
                buttonIcon = Icons.Filled.ArrowBack,
                onButtonClicked = { navController.popBackStack() }
            )
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            task.addressFrom?.address?.let {
                TextView(
                    stringResource(R.string.from_address, it),
                )
            }
            task.addressTo?.address?.let {
                TextView(
                    stringResource(R.string.to_address, it),
                )
            }
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
            TextView(
                stringResource(R.string.product_list),
                MaterialTheme.typography.titleLarge
            )
            LazyColumn(
                Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
            ) {
                items(
                    items = task.product,
                    itemContent = {
                        TextView(it.toString())
                    }
                )
            }

            TextView(
                stringResource(R.string.history),
                MaterialTheme.typography.titleLarge
            )
            LazyColumn(
                Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
            ) {
                items(
                    items = task.histories,
                    itemContent = {
                        TextView(it)
                    }
                )
            }
        }
    }
}

@Composable
fun TextView(text: String, style: TextStyle = MaterialTheme.typography.bodyMedium) {
    Text(
        text = text,
        style = style,
        textAlign = TextAlign.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .wrapContentWidth(Alignment.Start)
    )
}