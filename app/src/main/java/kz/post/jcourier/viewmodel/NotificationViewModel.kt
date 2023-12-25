package kz.post.jcourier.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.post.jcourier.common.onError
import kz.post.jcourier.common.onSuccess
import kz.post.jcourier.data.model.task.Task
import kz.post.jcourier.data.repository.FirebaseRepository
import javax.inject.Inject

class NotificationViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
) : ViewModel(), LifecycleObserver {

    fun sendToken(token: String) = viewModelScope.launch {
        firebaseRepository.sendToken(token).onSuccess {
            Log.e("Tokend Sended", token)
        }
    }
}