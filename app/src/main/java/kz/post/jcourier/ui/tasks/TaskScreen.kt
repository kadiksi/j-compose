package kz.post.jcourier.ui.tasks

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kz.post.jcourier.R
import kz.post.jcourier.ui.component.TopBar
import kz.post.jcourier.ui.tasks.components.*
import kz.post.jcourier.viewmodel.TaskViewModel

@Composable
fun task(
    navController: NavController, taskViewModel: TaskViewModel = hiltViewModel()
) {
    val task = taskViewModel.uiState.task.value
    val isError by taskViewModel.uiState.isError
    val isSmsDialog by taskViewModel.uiState.isSmsDialog
    val isCancelReasonDialog by taskViewModel.uiState.isCancelReasonDialog
    val isCallVariantsDialog by taskViewModel.uiState.isCallVariantDialog

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        val taskId = if(task.id != null) {
            task.id
        } else {
            ""
        }
        TopBar(title = stringResource(R.string.task_id, taskId!!),
            buttonIcon = Icons.Filled.ArrowBack,
            onButtonClicked = { navController.popBackStack() })
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            TaskAddress(task)
            ViewDivider()
            TaskSenderView(task)
            TaskFileView()
            TextView(
                stringResource(R.string.product_list), MaterialTheme.typography.titleLarge
            )
            LazyColumn(
                Modifier.weight(5f),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
            ) {
                task.product?.let {
                    items(items = it, itemContent = { task ->
                        task.name?.let { name -> TextView(name) }
                    })
                }
            }
            task.status?.let {
                TaskOptionButtons(taskViewModel, task, it)
            }

            TextView(
                stringResource(R.string.history), MaterialTheme.typography.titleLarge
            )
            LazyColumn(
                Modifier.weight(10f),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
            ) {
                task.histories?.let {
                    items(items = it, itemContent = { task ->
                        TextView(task.action + " " + task.createdDate)
                    })
                }
            }
        }
        TaskDialogs(
            taskViewModel,
            isError,
            task,
            isSmsDialog,
            isCancelReasonDialog,
            isCallVariantsDialog
        )
    }
}
