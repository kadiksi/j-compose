package kz.post.jcourier.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.Job
import kz.post.jcourier.ui.archive.homeArchive
import kz.post.jcourier.ui.component.DrawerScreens
import kz.post.jcourier.ui.ActiveOrders
import kz.post.jcourier.ui.Map
import kz.post.jcourier.ui.tasks.task
import kz.post.jcourier.viewmodel.LoginViewModel

@Composable
fun MainNavHost(
    navController: NavHostController,
    openDrawer: () -> Job,
    loginViewModel: LoginViewModel = hiltViewModel(),
    startLocation: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = DrawerScreens.ActiveOrders.route
    )
    {
        composable(DrawerScreens.ActiveOrders.route) {
            startLocation.invoke()
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
        composable(DrawerScreens.Notifications.route) {
//                    Settings(
//                        navController
//                    )
        }
        composable(DrawerScreens.Statistic.route) {
            homeArchive(
                navController,
                openDrawer = {
                    openDrawer()
                }
            )
        }
        composable(DrawerScreens.Settings.route) {
            task(
                navController,
            )
        }
        composable(DrawerScreens.Exit.route) {
            loginViewModel.logOut()
        }
    }
}