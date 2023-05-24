package kz.jcourier.location

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import kz.jcourier.utils.isGpsEnabled

class GpsLocationReceiver : BroadcastReceiver() {

    var listener: GpsLocationReceiverListener? = null

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == LocationManager.PROVIDERS_CHANGED_ACTION) {
            listener?.onGpsStatusChanged(context.isGpsEnabled())
        }
    }
}

interface GpsLocationReceiverListener {
    fun onGpsStatusChanged(isEnabled: Boolean)
}