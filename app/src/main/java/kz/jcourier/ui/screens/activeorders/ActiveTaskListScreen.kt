package kz.jcourier.ui.screens.activeorders

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kz.jcourier.R
import kz.jcourier.ui.component.TaskCard
import kz.jcourier.ui.component.TopBar
import kz.jcourier.viewmodel.HomeViewModel

@Composable
fun activeTaskList(
    navController : NavHostController,
    openDrawer: () -> Unit,
    homeViewModel: HomeViewModel
) {
    val taskList = homeViewModel.uiState.taskList.value

    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            title = stringResource(id = R.string.active_orders),
            buttonIcon = Icons.Filled.Menu,
            onButtonClicked = { openDrawer() }
        )
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
    }
}