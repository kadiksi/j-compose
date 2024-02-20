package kz.post.jcourier.ui.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.Job
import kz.post.jcourier.R
import kz.post.jcourier.ui.archive.homeArchive
import kz.post.jcourier.ui.component.DrawerScreens
import kz.post.jcourier.ui.ActiveOrders
import kz.post.jcourier.ui.Map
import kz.post.jcourier.ui.canceledtasks.canceledTasks
import kz.post.jcourier.ui.component.filepicker.CameraMainScreen
import kz.post.jcourier.ui.statistics.statisticsArchive
import kz.post.jcourier.ui.tasks.task
import kz.post.jcourier.viewmodel.CanceledTasksViewModel
import kz.post.jcourier.viewmodel.LoginViewModel

@Composable
fun MainNavHost(
    navController: NavHostController,
    openDrawer: () -> Job,
    loginViewModel: LoginViewModel = hiltViewModel(),
    canceledTaskViewModel: CanceledTasksViewModel,
    startLocation: Unit
) {

    NavHost(
        navController = navController,
        startDestination = DrawerScreens.ActiveOrders.route
    )
    {
        composable(DrawerScreens.ActiveOrders.route) {
            startLocation
            ActiveOrders(
                navController,
                openDrawer = {
                    openDrawer()
                },
            )
        }
        composable(DrawerScreens.Map.route) {
            Map(
                navController,
                openDrawer = {
                    openDrawer()
                }
            )
        }
        composable(DrawerScreens.CanceledTasks.route) {
            canceledTasks(
                navController,
                openDrawer = {
                    openDrawer()
                },
                canceledTaskViewModel,
            )
        }
        composable(DrawerScreens.Archive.route) {
            homeArchive(
                navController,
                openDrawer = {
                    openDrawer()
                }
            )
        }
        composable(DrawerScreens.Statistics.route) {
            statisticsArchive(
                openDrawer = {
                    openDrawer()
                }
            )
        }
        composable(DrawerScreens.TaskInfo.route) {
            task(
                navController,
                canceledTaskViewModel = canceledTaskViewModel
            )
        }
        composable(DrawerScreens.AddCameraPhoto.route) {
            CameraMainScreen(navController)
        }
        composable(DrawerScreens.Exit.route) {
            loginViewModel.logOut()
        }
        composable(DrawerScreens.Politics.route) {
            val uriHandler = LocalUriHandler.current
            Text(
                text = stringResource(id = R.string.policy),
                color = Color.Blue,
                modifier = Modifier
                    .clickable {
                        uriHandler.openUri("https://jpost.kz/private-policy/")
                    }
                    .padding(16.dp)
                    .fillMaxWidth()
            )
        }
    }
}