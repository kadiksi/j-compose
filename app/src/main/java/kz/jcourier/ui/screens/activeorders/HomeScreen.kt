package kz.jcourier.ui.screens.activeorders

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import kz.jcourier.ui.component.Drawer
import kz.jcourier.ui.component.DrawerScreens
import kz.jcourier.ui.screens.ActiveOrders
import kz.jcourier.ui.screens.Map
import kz.jcourier.ui.screens.Settings
import kz.jcourier.viewmodel.HomeViewModel
import kz.jcourier.viewmodel.LoginViewModel

@Composable
fun HomeScreen(
    loginViewModel: LoginViewModel = viewModel(),
    homeViewModel: HomeViewModel = viewModel()
) {
    val navController = rememberNavController()
    Surface {
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        val openDrawer = {
            scope.launch {
                drawerState.open()
            }
        }
        ModalNavigationDrawer(
            drawerState = drawerState,
            gesturesEnabled = drawerState.isOpen,
            drawerContent = {
                ModalDrawerSheet {
                    Text("Drawer title", modifier = Modifier.padding(16.dp))
                    Divider()
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
            }
        ) {
            NavHost(
                navController = navController,
                startDestination = DrawerScreens.ActiveOrders.route
            ) {
                composable(DrawerScreens.ActiveOrders.route) {
                    ActiveOrders(
                        openDrawer = {
                            openDrawer()
                        },
                        homeViewModel = homeViewModel
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
//                        viewModel.logOut()
                    Settings(
                        navController
                    )
                }
                composable(DrawerScreens.Statistic.route) {
                    Settings(
                        navController
                    )
                }
                composable(DrawerScreens.Settings.route) {
                    Settings(
                        navController
                    )
                }
                composable(DrawerScreens.Exit.route) {
                    loginViewModel.logOut()
                }
            }
        }
    }

}