package kz.post.jcourier.ui.map

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.android.gms.maps.model.CameraPosition
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
                    Marker(
                        state = MarkerState(
                            position = LatLng(
                                position.addressTo?.point?.latetude!!,
                                position.addressTo?.point?.longetude!!
                            ),
                        )
                    )
                }
            }
        }
    }
}