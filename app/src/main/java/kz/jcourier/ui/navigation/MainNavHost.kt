package kz.jcourier.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.Job
import kz.jcourier.ui.component.DrawerScreens
import kz.jcourier.ui.screens.ActiveOrders
import kz.jcourier.ui.screens.Map
import kz.jcourier.ui.screens.Settings
import kz.jcourier.viewmodel.LoginViewModel

@Composable
fun MainNavHost(
    navController: NavHostController,
    openDrawer: () -> Job,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    NavHost(
        navController = navController,
        startDestination = DrawerScreens.ActiveOrders.route
    )
    {
        composable(DrawerScreens.ActiveOrders.route) {
            ActiveOrders(
                navController,
                openDrawer = {
                    openDrawer()
                },
            )
        }
        composable(DrawerScreens.Map.route) {
            Map(
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
//                    Settings(
//                        navController
//                    )
        }
        composable(DrawerScreens.Settings.route) {
            Settings(
                navController,
            )
        }
        composable(DrawerScreens.Exit.route) {
            loginViewModel.logOut()
        }
    }
}