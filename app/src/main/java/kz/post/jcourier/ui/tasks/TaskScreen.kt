package kz.post.jcourier.ui.tasks

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kz.post.jcourier.R
import kz.post.jcourier.ui.component.TopBarWithActions
import kz.post.jcourier.ui.tasks.components.*
import kz.post.jcourier.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun task(
    navController: NavHostController, taskViewModel: TaskViewModel = hiltViewModel()
) {
    val task = taskViewModel.uiState.task.value
    val isError by taskViewModel.uiState.isError
    val isLoading by taskViewModel.uiState.isLoading
    val isSmsDialog by taskViewModel.uiState.isSmsDialog
    val isCancelReasonDialog by taskViewModel.uiState.isCancelReasonDialog
    val isCallVariantsDialog by taskViewModel.uiState.isCallVariantDialog
    val isChooseFileDialog by taskViewModel.uiState.isChooseFileDialog
    val isRefreshing = taskViewModel.uiState.isRefreshing.value
    val swipeRefreshState = rememberPullRefreshState(isRefreshing, {
        taskViewModel.getTask()
    })

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(swipeRefreshState)
    ) {
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
            TopBarWithActions(
                title = stringResource(R.string.task_id, orderId!!),
                backArrowIcon = Icons.Filled.ArrowBack,
                callIcon = Icons.Filled.Call,
                onBackClicked = { navController.popBackStack() },
                onCallClicked = { taskViewModel.showCallVariantDialog() },
                onChooseFileClicked = { taskViewModel.showChooseFileDialog() },
                taskViewModel = taskViewModel
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .pullRefresh(swipeRefreshState),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                TaskSenderAddress(task)
                ViewDivider()
                TaskClientView(task)
                TaskFileView(taskViewModel)
                ProductViews(task)
                TaskOptionButtons(taskViewModel, task)

                TaskDialogs(
                    taskViewModel,
                    isError,
                    task,
                    isSmsDialog,
                    isLoading,
                    isCancelReasonDialog,
                    isCallVariantsDialog,
                    isChooseFileDialog,
                    task.cancellationReasons,
                    navController,
                )
            }
        }
    }
}
