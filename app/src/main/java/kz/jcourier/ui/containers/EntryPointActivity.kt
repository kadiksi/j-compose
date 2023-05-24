package kz.jcourier.ui.containers

import android.content.IntentFilter
import android.location.LocationManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import kz.jcourier.app.theme.JTheme
import kz.jcourier.location.GpsLocationReceiver
import kz.jcourier.location.GpsLocationReceiverListener
import kz.jcourier.location.LocationPermissionLauncherFactory
import kz.jcourier.ui.screens.activeorders.HomeScreen
import kz.jcourier.ui.screens.LoginScreen
import kz.jcourier.utils.hasPermissionAccessFineLocation
import kz.jcourier.utils.registerPermissionsActivityResult
import kz.jcourier.utils.requestLocationPermission
import kz.jcourier.viewmodel.LoginViewModel
import kz.jcourier.viewmodel.MapViewModel

@AndroidEntryPoint
class EntryPointActivity : ComponentActivity(), LocationPermissionLauncherFactory,
    GpsLocationReceiverListener {
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var mapViewModel: MapViewModel
    override val locationPermissionLauncher =
        registerPermissionsActivityResult { startListenLocationChange() }
    private val gpsLocationReceiver by lazy { GpsLocationReceiver().also { it.listener = this } }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        mapViewModel = ViewModelProvider(this)[MapViewModel::class.java]

        val isAuthorised by loginViewModel.uiState.isAuthorised

        setContent {
            JTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    if (isAuthorised) {
                        HomeScreen { startListenLocationChange() }
                    } else {
                        LoginScreen()
                    }
                }
            }
        }
    }

    override fun onGpsStatusChanged(isEnabled: Boolean) {
        if (hasPermissionAccessFineLocation() && isEnabled) {
            mapViewModel.onLocationPermissionGranted()
        }
    }

    private fun startListenLocationChange() {
        requestLocationPermission(
            launcher = locationPermissionLauncher,
            settingsAppRequestCode = REQUEST_CODE_SETTINGS,
        ) {
            mapViewModel.onLocationPermissionGranted()
        }
    }

    override fun onStart() {
        super.onStart()
        registerReceiver(
            gpsLocationReceiver,
            IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
        )
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(gpsLocationReceiver)
    }

    companion object {
        private const val REQUEST_CODE_SETTINGS = 124
    }
}