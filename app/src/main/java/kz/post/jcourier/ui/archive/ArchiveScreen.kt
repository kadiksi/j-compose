package kz.post.jcourier.ui.archive

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import kz.post.jcourier.R
import kz.post.jcourier.ui.component.TaskCard
import kz.post.jcourier.ui.component.TopBar
import kz.post.jcourier.viewmodel.ArchiveViewModel
import kz.post.jcourier.viewmodel.HomeViewModel
import kz.post.jcourier.viewmodel.LoginViewModel
import kz.post.jcourier.viewmodel.MapViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun homeArchive(
    navController: NavHostController,
    openDrawer: () -> Unit,
    homeViewModel: ArchiveViewModel = hiltViewModel(),
) {
    val taskList = homeViewModel.uiState.taskList.value
    val isRefreshing = homeViewModel.uiState.isRefreshing.value
    val swipeRefreshState = rememberPullRefreshState(isRefreshing, { homeViewModel.refresh() })
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            title = stringResource(id = R.string.archive_orders),
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
                ) {
                    items(
                        items = taskList,
                        itemContent = {
                            TaskCard(navController, it)
                        }
                    )
                }
            }
            PullRefreshIndicator(
                isRefreshing,
                swipeRefreshState,
                Modifier.align(Alignment.TopCenter)
            ) // 3
        }
    }
}