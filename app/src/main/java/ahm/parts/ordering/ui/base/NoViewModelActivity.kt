package ahm.parts.ordering.ui.base

import ahm.parts.ordering.R
import ahm.parts.ordering.api.ApiResponse
import ahm.parts.ordering.api.ApiStatus
import ahm.parts.ordering.data.model.home.dashboard.partnumber.cart.CartFilter
import ahm.parts.ordering.helper.StringMasker
import ahm.parts.ordering.helper.showToast
import ahm.parts.ordering.ui.dialog.LoadingDialog
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerAppCompatActivity
import org.json.JSONArray

open class NoViewModelActivity : DaggerAppCompatActivity() {

    lateinit var loadingDialog: LoadingDialog

    var isGPSDisble = false
    var isLocLowAccuracy = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadingDialog = LoadingDialog(this)
    }

    fun getScreenSize(): HashMap<String, Int> {
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)

        val hashMap = HashMap<String, Int>()
        hashMap["width"] = size.x
        hashMap["height"] = size.y

        return hashMap
    }

    fun textOf(e: EditText?): String {
        return e?.text.toString().trim()
    }

    fun textOf(e: TextView?): String {
        return e?.text.toString().trim()
    }

    infix fun TextView.text(value: String?) {
        try {
            text = StringMasker().validateEmpty(value)
        } catch (e: Exception) {
        }
    }


    fun isEmptyRequired(vararg editText: EditText?): Boolean {
        for (e in editText) {
            if (textOf(e).isEmpty()) {
                e?.error = getString(R.string.error_fill_empty_field)
                e?.requestFocus()
                return true
            }
        }
        return false
    }

    fun isEmptyRequired(vararg string: String?): Boolean {
        for (e in string) {
            if (e  == "") {
                showToast(getString(R.string.lbl_form_required))
                return true
            }
        }
        return false
    }

    fun isEmptyRequiredSnackbar(vararg editText: EditText): Boolean {

        for (e in editText) {
            if (textOf(e).isEmpty()) {
                snacked(e, getString(R.string.error_empty, e.hint.toString()))
                e.requestFocus()
                return true
            }
        }

        return false
    }


    fun disconnect(error: String) {
        if (error.contains("500")) {
            loadingDialog.setResponse(R.string.error_server)
        } else {
            disconnect()
        }
    }

    private fun disconnect() {
        loadingDialog.setResponse(R.string.error_disconnect)
    }

    fun snacked(view: View, message: Int, duration: Int = Snackbar.LENGTH_SHORT) {
        Snackbar.make(view, message, duration).show()
    }

    fun snacked(view: View, message: String?, duration: Int = Snackbar.LENGTH_SHORT) {
        message?.let { Snackbar.make(view, it, duration).show() }
    }

    fun snacked(view: View, message: ApiResponse) {
        if (message.status == ApiStatus.ERROR) {
            if (!message.message.isNull(0)) {
                snacked(view, message.message)
            } else {
                snacked(view, message.throwableBody)
            }
        } else if (message.status == ApiStatus.WRONG) snacked(view, message.message)
    }

    private fun snacked(view: View, message: JSONArray) {
        for (i in 0 until message.length()) {
            snacked(view, message[i].toString())
        }
    }

    /**
     * fungsi digunakan untuk memvalidasi apakah gps aktive / tidak
     */
    fun isActiveGps(): Boolean {
        if(gpsState()){
            return true
        }else{
            showDialogEnableGPS()
            return false
        }
    }

    open fun gpsState(): Boolean {
        var isGPSon: Boolean
        val locationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGPSEnable =
            locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkAble =
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        var isHighAcuracy: Boolean? = null
        try {
            isHighAcuracy = Settings.Secure.getInt(
                contentResolver,
                Settings.Secure.LOCATION_MODE
            ) == Settings.Secure.LOCATION_MODE_HIGH_ACCURACY
        } catch (e: Settings.SettingNotFoundException) {
            e.printStackTrace()
        }
        try {
            if (!isGPSEnable) {
                isGPSon = false
                isGPSDisble = true
                isLocLowAccuracy = false
            } else if (!isNetworkAble) {
                isGPSon = false
                isGPSDisble = false
                isLocLowAccuracy = false
            } else if (!isHighAcuracy!!) {
                isGPSon = false
                isGPSDisble = false
                isLocLowAccuracy = true
            } else {
                isGPSDisble = false
                isLocLowAccuracy = false
                isGPSon = true
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            if (!isGPSEnable) {
                isGPSon = false
                isGPSDisble = true
                isLocLowAccuracy = false
            } else if (!isNetworkAble) {
                isGPSon = false
                isGPSDisble = false
                isLocLowAccuracy = false
            } else {
                isLocLowAccuracy = false
                isGPSon = true
            }
        }
        return isGPSon
    }

    open fun showDialogEnableGPS() {
        val builder =
            AlertDialog.Builder(
                this
            )
        builder.setTitle("Notification")
        if (isGPSDisble) {
            builder.setMessage("Make sure your GPS is active")
        } else if (isLocLowAccuracy) {
            builder.setMessage("Please make sure the gps settings are in High Accuracy")
        } else {
            builder.setMessage("Make sure your Location is active with gps settings in High Accuracy")
        }
        builder.setPositiveButton(
            "OK"
        ) { dialog, which ->
            val intent =
                Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivityForResult(intent, 1)
            dialog.dismiss()
        }
        builder.show()
    }

}