package g3.cpe.fr.journeydiaries.providers

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import com.google.android.gms.maps.LocationSource
import com.google.android.gms.maps.LocationSource.OnLocationChangedListener


class LocationProvider(context: Context) : LocationSource, LocationListener {

    private var listener: OnLocationChangedListener? = null
    private val locationManager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    @SuppressLint("MissingPermission")
    override fun activate(listener: OnLocationChangedListener) {
        this.listener = listener
        val gpsProvider = locationManager.getProvider(LocationManager.GPS_PROVIDER)
        if (gpsProvider != null) {
            locationManager.requestLocationUpdates(gpsProvider.name, 0, 10f, this)
        }

        val networkProvider = locationManager.getProvider(LocationManager.NETWORK_PROVIDER)
        if (networkProvider != null) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, (1000 * 60 * 5).toLong(), 0f, this)
        }
    }

    override fun deactivate() {
        locationManager.removeUpdates(this)
    }

    override fun onLocationChanged(location: Location) {
        if (listener != null) {
            listener!!.onLocationChanged(location)
        }
        Log.i("c", "changed")
    }

    override fun onProviderDisabled(provider: String) {}

    override fun onProviderEnabled(provider: String) {}

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
}