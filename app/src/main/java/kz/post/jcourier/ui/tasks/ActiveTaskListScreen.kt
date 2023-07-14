package kz.post.jcourier.ui.tasks

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kz.post.jcourier.R
import kz.post.jcourier.ui.component.TaskCard
import kz.post.jcourier.ui.component.TopBar
import kz.post.jcourier.viewmodel.HomeViewModel
import kz.post.jcourier.viewmodel.LoginViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun activeTaskList(
    navController: NavHostController,
    openDrawer: () -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel(),
    loginViewModel: LoginViewModel = hiltViewModel(),
) {
    val taskList = homeViewModel.uiState.taskList.value
    val isRefreshing = homeViewModel.uiState.isRefreshing.value
    val swipeRefreshState = rememberPullRefreshState(isRefreshing, { homeViewModel.refresh() })
    if (!homeViewModel.isLogin.value.isLogin) {
        loginViewModel.logOut()
    }
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(title = stringResource(id = R.string.active_orders),
            buttonIcon = Icons.Filled.Menu,
            onButtonClicked = { openDrawer() })
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
                ) {
                    items(items = taskList, itemContent = {
                        TaskCard(navController, it)
                    })
                }
            }
            if (taskList.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .pullRefresh(swipeRefreshState),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier.size(186.dp),
                        painter = painterResource(R.drawable.baseline_not_interested_24),
                        contentDescription = "Content description for visually impaired"
                    )
                    Text(
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.End,
                        text = stringResource(id = R.string.no_task)
                    )
                }
            }
            PullRefreshIndicator(
                isRefreshing, swipeRefreshState, Modifier.align(Alignment.TopCenter)
            )
        }
    }
}