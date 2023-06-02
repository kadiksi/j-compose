package kz.post.jcourier.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import kz.post.jcourier.ui.map.homeMap
import kz.post.jcourier.ui.tasks.activeTaskList

@Composable
fun ActiveOrders(
    navController: NavHostController,
    openDrawer: () -> Unit,
) {
    activeTaskList(navController, openDrawer)
}

@Composable
fun Map(
    navController: NavHostController,
    openDrawer: () -> Unit,
) {
    homeMap(navController, openDrawer)
}