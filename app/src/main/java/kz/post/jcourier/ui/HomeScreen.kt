package kz.post.jcourier.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import kz.post.jcourier.ui.component.Drawer
import kz.post.jcourier.ui.navigation.MainNavHost
import kz.post.jcourier.viewmodel.StatusViewModel

@Composable
fun HomeScreen(
    startLocation: () -> Unit
) {
    val shiftViewModel: StatusViewModel = hiltViewModel()
    val navController = rememberNavController()
    Surface {
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        val openDrawer = {
            scope.launch {
                drawerState.open()
            }
        }
        shiftViewModel.getCourierShift()
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
                        shiftViewModel = shiftViewModel
                    )
                }
            }
        ) {
            MainNavHost(navController, openDrawer, startLocation = startLocation)
        }
    }
}