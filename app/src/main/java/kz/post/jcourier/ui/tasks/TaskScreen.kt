package kz.post.jcourier.ui.tasks

import android.text.format.DateUtils
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Close
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
import kz.post.jcourier.ui.component.TopBarWithActions
import kz.post.jcourier.ui.tasks.components.*
import kz.post.jcourier.utils.toeDateTimeFormat
import kz.post.jcourier.viewmodel.TaskViewModel

@Composable
fun task(
    navController: NavController, taskViewModel: TaskViewModel = hiltViewModel()
) {
    val task = taskViewModel.uiState.task.value
    val isError by taskViewModel.uiState.isError
    val isLoading by taskViewModel.uiState.isLoading
    val isSmsDialog by taskViewModel.uiState.isSmsDialog
    val isCancelReasonDialog by taskViewModel.uiState.isCancelReasonDialog
    val isCallVariantsDialog by taskViewModel.uiState.isCallVariantDialog

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        val orderId = if (task.orderId != null) {
            task.orderId
        } else {
            ""
        }
        TopBarWithActions(title = stringResource(R.string.task_id, orderId!!),
            backArrowIcon = Icons.Filled.ArrowBack,
            callIcon = Icons.Filled.Call,
            onBackClicked = { navController.popBackStack() },
            onCallClicked = { taskViewModel.showCallVariantDialog() },
            taskViewModel = taskViewModel
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            TaskAddress(task)
            ViewDivider()
            TaskSenderView(task)
            TaskFileView(taskViewModel)
            if (task.product?.isNotEmpty() == true) {
                TextView(
                    stringResource(R.string.product_list), MaterialTheme.typography.titleLarge
                )
                task.product?.forEach {
                    it.name?.let { it1 ->
                        TextView(
                            it1, MaterialTheme.typography.labelLarge,
                            topPaddign = 0.dp,
                            bottomPaddign = 0.dp
                        )
                    }
                }
            }
            task.status?.let {
                TaskOptionButtons(taskViewModel, task, it)
            }

            if (task.histories?.isNotEmpty() == true) {
                TextView(
                    stringResource(R.string.history), MaterialTheme.typography.titleLarge
                )
                task.histories?.forEach {
                    it.action?.let { it1 ->
                        TextView(
                            it1+" - ${toeDateTimeFormat(it.createdDate)}", MaterialTheme.typography.labelLarge,
                            topPaddign = 0.dp,
                            bottomPaddign = 0.dp
                        )
                    }
                }
            }
        }
        TaskDialogs(
            taskViewModel,
            isError,
            task,
            isSmsDialog,
            isLoading,
            isCancelReasonDialog,
            isCallVariantsDialog,
            task.cancellationReasons
        )
    }
}
