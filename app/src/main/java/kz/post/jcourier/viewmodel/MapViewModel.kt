package kz.post.jcourier.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kz.post.jcourier.common.NetworkResult
import kz.post.jcourier.data.model.task.Task
import kz.post.jcourier.data.repository.TaskRepository
import javax.inject.Inject

data class MapState(
    var taskList: MutableState<List<Task>> = mutableStateOf(emptyList()),
    var isError: MutableState<Boolean> = mutableStateOf(false),
    val cameraPositionState: MutableState<CameraPosition> = mutableStateOf(
        CameraPosition.fromLatLngZoom(
            LatLng(
                51.1,
                71.2342
            ), 4.8f
        )
    )
)

@HiltViewModel
class MapViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
) : ViewModel(), LifecycleObserver {

    var uiState by mutableStateOf(MapState())

    init {
        getTaskList()
    }

    private fun getTaskList() = viewModelScope.launch {
        when (val result = taskRepository.getTaskList()) {
            is NetworkResult.Success -> {
                result.data.let {
                    if (it.isEmpty())
                        return@launch
                    uiState.taskList.value = it
                    getValidPoint(it)?.let { position ->
                        uiState.cameraPositionState.value = position
                    }
                }
            }
            is NetworkResult.Error -> {
                uiState.isError.value = true
            }
            else -> {
                uiState.isError.value = true
            }
        }
    }

    private fun getValidPoint(tasks: List<Task>): CameraPosition? {
        tasks.forEach {
            if (it.addressTo?.point?.latitude != null) {
                return CameraPosition.fromLatLngZoom(
                    LatLng(
                        it.addressTo?.point?.latitude!!.toDouble(),
                        it.addressTo?.point?.longitude!!
                    ), 5.8f
                )
            }
        }
        return null
    }
}