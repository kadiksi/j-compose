package kz.post.jcourier.ui.canceledtasks

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import kz.post.jcourier.R
import kz.post.jcourier.ui.component.CanceledNotificationCard
import kz.post.jcourier.ui.component.TopBar
import kz.post.jcourier.utils.paginator.ListState
import kz.post.jcourier.viewmodel.CanceledTasksViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun canceledTasks(
    navController: NavHostController,
    openDrawer: () -> Unit,
    canceledTaskViewModel: CanceledTasksViewModel,
) {
    val taskList = canceledTaskViewModel.canceledTaskList
    val isRefreshing = canceledTaskViewModel.uiState.isRefreshing.value
    val swipeRefreshState = rememberPullRefreshState(isRefreshing, { canceledTaskViewModel.refresh() })
    val lazyColumnListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val shouldStartPaginate = remember {
        derivedStateOf {
            canceledTaskViewModel.canPaginate && (lazyColumnListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -9) >= (lazyColumnListState.layoutInfo.totalItemsCount - 6)
        }
    }

    LaunchedEffect(key1 = shouldStartPaginate.value) {
        if (shouldStartPaginate.value && canceledTaskViewModel.listState == ListState.IDLE)
            canceledTaskViewModel.getCanceledNotificationList()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            title = stringResource(id = R.string.canceled_task),
            buttonIcon = Icons.Filled.Menu,
            onButtonClicked = { openDrawer() }
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(swipeRefreshState)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyColumn(
                    Modifier.weight(1f),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
                    state = lazyColumnListState,
                ) {
                    items(
                        items = taskList,
                        key = { it.taskId }
                    ) {
                        CanceledNotificationCard(navController, it)
                    }
                    item (
                        key = canceledTaskViewModel.listState,
                    ) {
                        when(canceledTaskViewModel.listState) {
                            ListState.LOADING -> {
                                Column(
                                    modifier = Modifier
                                        .fillParentMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                ) {
                                    Text(
                                        modifier = Modifier
                                            .padding(8.dp),
                                        text = "Refresh Loading"
                                    )

                                    CircularProgressIndicator(color = Color.Black)
                                }
                            }
                            ListState.PAGINATING -> {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                ) {
                                    Text(text = "Pagination Loading")

                                    CircularProgressIndicator(color = Color.Black)
                                }
                            }
                            ListState.PAGINATION_EXHAUST -> {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 6.dp, vertical = 16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                ) {
                                    Icon(imageVector = Icons.Rounded.Face, contentDescription = "")

                                    Text(text = "Nothing left.")

                                    TextButton(
                                        modifier = Modifier
                                            .padding(top = 8.dp),
                                        elevation = ButtonDefaults.elevation(0.dp),
                                        onClick = {
                                            coroutineScope.launch {
                                                lazyColumnListState.animateScrollToItem(0)
                                            }
                                        },
                                        content = {
                                            Row(
                                                horizontalArrangement = Arrangement.Center,
                                                verticalAlignment = Alignment.CenterVertically,
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Rounded.KeyboardArrowUp,
                                                    contentDescription = ""
                                                )

                                                Text(text = "Back to Top")

                                                Icon(
                                                    imageVector = Icons.Rounded.KeyboardArrowUp,
                                                    contentDescription = ""
                                                )
                                            }
                                        }
                                    )
                                }
                            }
                            else -> {}
                        }
                    }
                }
            }
            PullRefreshIndicator(
                isRefreshing,
                swipeRefreshState,
                Modifier.align(Alignment.TopCenter)
            )
        }
    }
}