package kz.jcourier.ui.screens.activeorders

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kz.jcourier.ui.component.TopBar
import kz.jcourier.viewmodel.HomeViewModel

@Composable
fun activeOrders(
    openDrawer: () -> Unit,
    homeViewModel: HomeViewModel
) {
    homeViewModel.uiState
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            title = "Home",
            buttonIcon = Icons.Filled.Menu,
            onButtonClicked = { openDrawer() }
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            SwipeToRefreshDemo()
        }
    }
}