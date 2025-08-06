package ahm.parts.ordering.helper

import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.constant.Constants.LOCATIONPICKER.RESULT_DATA_KEY
import ahm.parts.ordering.data.constant.Constants.LOCATIONPICKER.SUCCESS_RESULT
import android.app.IntentService
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.os.ResultReceiver
import com.google.android.gms.maps.model.LatLng
import java.io.IOException
import java.util.*

class AddressService : IntentService("AddressService") {

    private var receiver: ResultReceiver? = null

    override fun onHandleIntent(intent: Intent?) {

        intent ?: return
        val geocoder = Geocoder(this, Locale.getDefault())

        var errorMessage = ""

        // Get the location passed to this service through an extra.
        val location =
            intent.getParcelableExtra<LatLng>(Constants.LOCATIONPICKER.LOCATION_DATA_EXTRA)
        receiver = intent.getParcelableExtra(Constants.LOCATIONPICKER.RECEIVER)

        // ...

        var addresses: List<Address> = emptyList()

        try {
            addresses = geocoder.getFromLocation(
                location.latitude,
                location.longitude,
                // In this sample, we get just a single customersAddress.
                1
            )
        } catch (ioException: IOException) {
            // Catch network or other I/O problems.
            errorMessage = "No geocoder available"

        } catch (illegalArgumentException: IllegalArgumentException) {
            // Catch invalid latitude or longitude values.
            errorMessage = "Invalid latitude or longitude used"
        }

        // Handle case where no customersAddress was found.
        if (addresses.isEmpty()) {
            if (errorMessage.isEmpty()) {
                errorMessage = "Sorry, no address found"
            }
            deliverResultToReceiver(Constants.LOCATIONPICKER.FAILURE_RESULT, errorMessage)
        } else {
            val address = addresses[0]
            // Fetch the customersAddress lines using getAddressLine,
            // join them, and send them to the thread.
            val addressFragments = with(address) {
                (0..maxAddressLineIndex).map { getAddressLine(it) }
            }
            deliverResultToReceiver(SUCCESS_RESULT, addressFragments.joinToString(separator = "\n"))
        }

    }

    private fun deliverResultToReceiver(resultCode: Int, message: String) {
        val bundle = Bundle().apply { putString(RESULT_DATA_KEY, message) }
        receiver?.send(resultCode, bundle)
    }
}