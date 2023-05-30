package kz.post.jcourier.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import kz.post.jcourier.ui.map.homeMap
import kz.post.jcourier.ui.screens.activeorders.activeTaskList

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