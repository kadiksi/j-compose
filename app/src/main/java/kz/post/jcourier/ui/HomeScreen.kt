package kz.post.jcourier.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import kz.post.jcourier.ui.component.Drawer
import kz.post.jcourier.ui.navigation.MainNavHost
import kz.post.jcourier.viewmodel.CanceledTasksViewModel
import kz.post.jcourier.viewmodel.LoginViewModel
import kz.post.jcourier.viewmodel.StatusViewModel

@Composable
fun HomeScreen(
    startLocation:  Unit,
    shiftViewModel: StatusViewModel,
    loginViewModel: LoginViewModel,
    canceledTaskViewModel: CanceledTasksViewModel,
    navController: NavHostController
) {

    Surface {
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        val openDrawer = {
            scope.launch {
                shiftViewModel.getCourierShift()
                canceledTaskViewModel.refresh()
                drawerState.open()
            }
        }
        if (!shiftViewModel.isLogin.value.isLogin) {
            loginViewModel.logOut()
        }
//        startLocation.invoke()
        ModalNavigationDrawer(
            drawerState = drawerState,
            gesturesEnabled = drawerState.isOpen,
            drawerContent = {
                ModalDrawerSheet {
//                    Text("Drawer title", modifier = Modifier.padding(16.dp))
//                    Divider()
                    Drawer(
                        onDestinationClicked = { route ->
                            scope.launch {
                                drawerState.close()
                            }
                            navController.navigate(route) {
                                "home"
                                launchSingleTop = true
                            }
                        },
                        shiftViewModel = shiftViewModel,
                        canceledTaskViewModel = canceledTaskViewModel
                    )
                }
            }
        ) {
            MainNavHost(
                navController, openDrawer, startLocation = startLocation,
                canceledTaskViewModel = canceledTaskViewModel
            )
        }
    }
}