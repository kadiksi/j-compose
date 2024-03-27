package kz.post.jcourier.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import kz.post.jcourier.R
import kz.post.jcourier.firebase.IsNotification
import kz.post.jcourier.ui.component.Drawer
import kz.post.jcourier.ui.component.DrawerScreens
import kz.post.jcourier.ui.navigation.MainNavHost
import kz.post.jcourier.ui.navigation.navigate
import kz.post.jcourier.viewmodel.CanceledTasksViewModel
import kz.post.jcourier.viewmodel.LoginViewModel
import kz.post.jcourier.viewmodel.StatusViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    startLocation: Unit,
    shiftViewModel: StatusViewModel,
    loginViewModel: LoginViewModel,
    canceledTaskViewModel: CanceledTasksViewModel,
    navController: NavHostController
) {

    LaunchedEffect(true) {
        shiftViewModel.getCourierShift()
    }

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
        val sheetState = rememberModalBottomSheetState()
        if (loginViewModel.isNotification.value.isNotification) {
            ModalBottomSheet(
                onDismissRequest = {
                    loginViewModel.isNotification.value = IsNotification(false)
                },
                sheetState = sheetState
            ) {
                // Sheet content
                Column {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, top = 8.dp),
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge,
                        text = "№ ${loginViewModel.isNotification.value.id}"
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        text = loginViewModel.isNotification.value.title.toString()
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        text = loginViewModel.isNotification.value.body.toString()
                    )
                    Button(modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp),
                        onClick = {
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    navController.navigate(
                                        DrawerScreens.TaskInfo.route,
                                        "taskId" to loginViewModel.isNotification.value.id
                                    )
                                    loginViewModel.isNotification.value = IsNotification(false)
                                }
                            }
                        }) {
                        loginViewModel.isNotification.value.title?.let {
                            Text(
                                text = stringResource(
                                    id = R.string.open
                                )
                            )
                        }
                    }
                }
            }
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