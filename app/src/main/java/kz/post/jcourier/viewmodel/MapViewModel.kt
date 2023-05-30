package kz.post.jcourier.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kz.post.jcourier.data.location.SendCoordinationUseCase
import kz.post.jcourier.location.model.TrackingLocation
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

data class MapState(
    var lastSentLocation: MutableState<TrackingLocation> = mutableStateOf(
        TrackingLocation(
            1,
            71.0,
            51.8
        )
    ),
)

@HiltViewModel
class MapViewModel @Inject constructor(
    private val sendCoordinationUseCase: SendCoordinationUseCase,
) : ViewModel(), LifecycleObserver {

    var uiState by mutableStateOf(MapState())

    private val isLocationSendStarted = AtomicBoolean(false)

    fun onLocationPermissionGranted() {
        if (isLocationSendStarted.get()) return
        isLocationSendStarted.set(true)
        sendCoordinationUseCase.start()

        viewModelScope.launch {
            while (isActive) {
                sendCoordinationUseCase.invoke()
                delay(1_000)
            }
        }
        uiState.lastSentLocation = sendCoordinationUseCase.lastSentLocation
    }

    override fun onCleared() {
        super.onCleared()
        isLocationSendStarted.set(false)
        sendCoordinationUseCase.stop()
    }
}