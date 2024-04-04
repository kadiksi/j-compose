package kz.post.jcourier.viewmodel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kz.post.jcourier.data.location.SendCoordinationUseCase
import kz.post.jcourier.data.model.shift.CourierModel
import kz.post.jcourier.data.model.shift.Shift
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val sendCoordinationUseCase: SendCoordinationUseCase,
    val user: MutableState<CourierModel>,
) : ViewModel(), LifecycleObserver {

    private val isLocationSendStarted = AtomicBoolean(false)

    fun onLocationPermissionGranted() {
        if (isLocationSendStarted.get()) return
        isLocationSendStarted.set(true)
        sendCoordinationUseCase.start()

        viewModelScope.launch {
            while (isActive) {
                delay(10000_000)
                if(user.value.status == Shift.ON_SHIFT) {
                    sendCoordinationUseCase.invoke()
                } else {
                    sendCoordinationUseCase.stop()
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        isLocationSendStarted.set(false)
        sendCoordinationUseCase.stop()
    }

    fun startTracking(){

    }
}