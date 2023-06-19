package kz.post.jcourier.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kz.post.jcourier.data.location.SendCoordinationUseCase
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val sendCoordinationUseCase: SendCoordinationUseCase,
) : ViewModel(), LifecycleObserver {

    private val isLocationSendStarted = AtomicBoolean(false)

    fun onLocationPermissionGranted() {
        if (isLocationSendStarted.get()) return
        isLocationSendStarted.set(true)
        sendCoordinationUseCase.start()

        viewModelScope.launch {
            while (isActive) {
                delay(50_000)
                sendCoordinationUseCase.invoke()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        isLocationSendStarted.set(false)
        sendCoordinationUseCase.stop()
    }
}