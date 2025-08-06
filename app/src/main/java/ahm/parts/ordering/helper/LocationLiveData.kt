package ahm.parts.ordering.helper

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Looper
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*


/**
 * Created by Emre Eran on 31.08.2018.
 */
class LocationLiveData private constructor() : LiveData<Location>() {

    lateinit var context: Context
    lateinit var locationRequest: LocationRequest
    var onErrorCallback: OnErrorCallback? = null
    private lateinit var locationClient: FusedLocationProviderClient

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)
            locationResult ?: return
            for (location in locationResult.locations) {
                postValue(location)
            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun onActive() {
        super.onActive()
        locationClient = FusedLocationProviderClient(context)

        // Check permissions
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            locationClient.lastLocation.addOnSuccessListener {
                postValue(it)
            }

            // Check settings
            val locationSettingsRequest = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest).build()

            val settingsClient = LocationServices.getSettingsClient(context)
            settingsClient.checkLocationSettings(locationSettingsRequest).addOnCompleteListener {
                try {
                    it.getResult(ApiException::class.java)
                    locationClient.requestLocationUpdates(
                        locationRequest,
                        locationCallback,
                        Looper.myLooper()
                    )
                } catch (e: ApiException) {
                    onErrorCallback?.onLocationSettingsException(e)
                }
            }
        } else {
            onErrorCallback?.onPermissionsMissing()
        }
    }

    override fun onInactive() {
        super.onInactive()
        locationClient.removeLocationUpdates(locationCallback)
    }

    interface OnErrorCallback {
        /**
         * Called when location setting check fails
         * Status codes include:
         * [LocationSettingsStatusCodes.RESOLUTION_REQUIRED]
         * [LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE]
         */
        fun onLocationSettingsException(e: ApiException)

        fun onPermissionsMissing()
    }

    companion object {
        @JvmOverloads
        @JvmStatic
        fun create(
            context: Context,
            interval: Long? = null,
            fastestInterval: Long? = null,
            priority: Int? = null,
            smallestDisplacement: Float? = null,
            expirationTime: Long? = null,
            maxWaitTime: Long? = null,
            numUpdates: Int? = null,
            onErrorCallback: OnErrorCallback? = null
        ): LocationLiveData {
            val liveData = LocationLiveData()
            val locationRequest = LocationRequest.create()

            interval?.let { locationRequest.interval = it }
            fastestInterval?.let { locationRequest.fastestInterval = it }
            priority?.let { locationRequest.priority = it }
            smallestDisplacement?.let { locationRequest.smallestDisplacement = it }
            expirationTime?.let { locationRequest.expirationTime }
            maxWaitTime?.let { locationRequest.maxWaitTime = it }
            //numUpdates?.let { locationRequest.numUpdates = it }

            liveData.context = context
            liveData.locationRequest = locationRequest
            liveData.onErrorCallback = onErrorCallback

            return liveData
        }

        fun showLocationToMap(mMap: GoogleMap, latLng: LatLng, iconMarker: Int) {
            createMarker(mMap, latLng, iconMarker)
            focusPointingToLocation(mMap, latLng)
        }


        private fun createMarker(mMap: GoogleMap, latLng: LatLng, iconMarker: Int) {
            mMap.addMarker(
                MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(iconMarker))
                    .position(latLng)
            )
        }


        fun focusPointingToIND(mMap: GoogleMap) {
            val position = CameraPosition.Builder()
                .target(LatLng(-6.107019, 107.317104))
                .build()
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(position))
        }

        fun focusPointingToLocation(mMap: GoogleMap, latLng: LatLng) {
            val position = CameraPosition.Builder()
                .target(latLng)
                .zoom(16f)
                .build()
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(position))
        }

        fun getCompleteAddressString(context: Context, latLng: LatLng): String {
            var strAdd = ""
            val geocoder = Geocoder(context, Locale.getDefault())
            try {
                val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
                if (addresses != null) {
                    val returnedAddress = addresses[0]
                    val strReturnedAddress = StringBuilder("")

                    for (i in 0..returnedAddress.maxAddressLineIndex) {
                        strReturnedAddress.append(returnedAddress.getAddressLine(i))
                    }
                    strAdd = strReturnedAddress.toString()
                } else {
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return strAdd
        }
    }

    fun stopLocation() {
        onInactive()
    }
}