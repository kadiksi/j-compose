package kz.post.jcourier.ui.map

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kz.post.jcourier.R
import kz.post.jcourier.ui.component.TopBar
import kz.post.jcourier.viewmodel.MapViewModel

@Composable
fun homeMap(
    navController: NavHostController,
    openDrawer: () -> Unit,
    mapViewModel: MapViewModel = hiltViewModel(),
) {
    val mapProperties = MapProperties(
        isMyLocationEnabled = true,
    )
    val taskList = mapViewModel.uiState.taskList.value
    val cameraPositionState: CameraPositionState = rememberCameraPositionState {
        position = mapViewModel.uiState.cameraPositionState.value
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            title = stringResource(id = R.string.map),
            buttonIcon = Icons.Filled.Menu,
            onButtonClicked = { openDrawer() }
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = mapProperties,
            ) {
                taskList.forEach { position ->
                    if (position.addressTo?.point?.latitude != null)
                        Marker(
                            state = MarkerState(
                                position = LatLng(
                                    position.addressTo?.point?.latitude!!,
                                    position.addressTo?.point?.longitude!!
                                ),
                            )
                        )
                }
            }
        }
    }
}