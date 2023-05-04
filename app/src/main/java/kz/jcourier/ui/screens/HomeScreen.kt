package kz.jcourier.ui.screens

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import kz.jcourier.ui.component.Drawer
import kz.jcourier.ui.component.DrawerScreens
import kz.jcourier.viewmodel.HomeViewModel
import kz.jcourier.viewmodel.LoginViewModel

@Composable
fun HomeScreen(
    show: Boolean,
    viewModel: LoginViewModel = viewModel(),
    homeViewModel: HomeViewModel
) {
    if (show) {
        val navController = rememberNavController()
        Surface(color = MaterialTheme.colors.background) {
            val drawerState = rememberDrawerState(DrawerValue.Closed)
            val scope = rememberCoroutineScope()
            val openDrawer = {
                scope.launch {
                    drawerState.open()
                }
            }
            ModalDrawer(
                drawerState = drawerState,
                gesturesEnabled = drawerState.isOpen,
                drawerContent = {
                    Drawer(
                        onDestinationClicked = { route ->
                            scope.launch {
                                drawerState.close()
                            }
                            navController.navigate(route) {
                                "home"
                                launchSingleTop = true
                            }
                        }
                    )
                }
            ) {
                NavHost(
                    navController = navController,
                    startDestination = DrawerScreens.Home.route
                ) {
                    composable(DrawerScreens.Home.route) {
                        Home(
                            openDrawer = {
                                openDrawer()
                            },
                            homeViewModel = homeViewModel
                        )
                    }
                    composable(DrawerScreens.Account.route) {
                        Account(
                            openDrawer = {
                                openDrawer()
                            }
                        )
                    }
                    composable(DrawerScreens.Help.route) {
                        viewModel.logOut()
                    }
                }
            }
        }
    }
}