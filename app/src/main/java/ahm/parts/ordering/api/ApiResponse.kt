package ahm.parts.ordering.api

import ahm.parts.ordering.data.constant.Constants
import android.util.Log
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.HttpException

open class ApiResponse(

    /**
     * Digunakan untuk variable dari apiStatus api
     *
     */
    @Expose
    @SerializedName("status")
    var status: ApiStatus? = ApiStatus.SUCCESS,

    /**
     * Digunakan untuk variable dari apiMessage api
     *
     */
    @Expose
    @SerializedName("message")
    var message: JSONArray = JSONArray(),

    /**
     * Digunakan untuk variable jika token expired
     *
     */
    @Expose
    @SerializedName("is_token_expired")
    var isTokenExpired: Boolean = false,

    /**
     * Digunakan untuk variable saat tidak ada koneksi / server error
     *
     */
    @Expose
    @SerializedName("throwable")
    var throwableBody: String? = null,

    /**
     * Digunakan untuk variable pengesetan view sebagai flag
     *
     */
    var flagView: Int? = null,

    /**
     * Digunakan untuk variable pengesetan view sebagai SwipeRefresh
     *
     */
    var flagLoadingRefresh: Int? = null,

    /**
     * Digunakan untuk variable pengesetan view sebagai flag Loading custom view
     *
     */
    var flagLoadingView: Int? = null,

    /**
     * Digunakan untuk variable pengesetan boolean yang menandakan menggunakan loading dialog atau tidak
     *
     */
    var flagLoadingDialog: Boolean = false,

    /**
     * Digunakan untuk variable pengesetan Boolean untuk hide show dari flag view yang disetkan,Default Boolean false
     *
     */
    var isHide: Boolean = false,

    /**
     * Digunakan untuk variable saat tidak ada koneksi / server error
     *
     */
    var throwableHttp: Throwable? = null

) {
    fun responseError(it: Throwable,flag: Int? = null,flagLoadRefresh : Int? = null,flagLoadView : Int? = null,flagLoadDialog : Boolean? = false,isHideView : Boolean? = false): ApiResponse {

        throwableHttp = it
        flagView = flag
        flagLoadingRefresh = flagLoadRefresh
        flagLoadingView = flagLoadView
        flagLoadingDialog = flagLoadDialog!!
        isHide = isHideView!!

        status = ApiStatus.ERROR

        try {
            val error = it as HttpException
            val errorBody = error.response().errorBody()?.string()
            val responseJson = JSONObject(errorBody)
            val apiMessage = responseJson.getJSONArray(Constants.REMOTE.API_MESSAGE)
            message = apiMessage

            if (error.code() == 401)

                Log.e("throwableMessage401",message[0].toString())

                if (message[0].toString().equals("The token is expired!", true) ||
                    message[0].toString().equals("The token is invalid!", true)
                )


             {
                resetSessionToken()
            }

        } catch (e: Exception) {
            Log.e("throwable",""+it.message)
            throwableBody = it.localizedMessage
        }

//        throwableBody = it.localizedMessage

        return this
    }


    fun responseWrong(apiMessage: JSONArray, flag: Int? = null,flagLoadRefresh : Int? = null,flagLoadView : Int? = null,flagLoadDialog : Boolean? = false,isHideView : Boolean? = false): ApiResponse {
        flagView = flag
        flagLoadingRefresh = flagLoadRefresh
        flagLoadingView = flagLoadView
        flagLoadingDialog = flagLoadDialog!!
        isHide = isHideView!!
        status = ApiStatus.WRONG
        message = apiMessage
        return this
    }

    fun responseSuccess(apiMessage: JSONArray, flag: Int? = null,flagLoadRefresh : Int? = null,flagLoadView : Int? = null,flagLoadDialog : Boolean? = false,isHideView : Boolean? = false): ApiResponse {
        flagView = flag
        flagLoadingRefresh = flagLoadRefresh
        flagLoadingView = flagLoadView
        flagLoadingDialog = flagLoadDialog!!
        isHide = isHideView!!
        status = ApiStatus.SUCCESS
        message = apiMessage
        return this
    }

    fun responseEmpty(apiMessage: JSONArray, flag: Int? = null,flagLoadRefresh : Int? = null,flagLoadView : Int? = null,flagLoadDialog : Boolean? = false,isHideView : Boolean? = false): ApiResponse {
        flagView = flag
        flagLoadingRefresh = flagLoadRefresh
        flagLoadingView = flagLoadView
        flagLoadingDialog = flagLoadDialog!!
        isHide = isHideView!!
        status = ApiStatus.EMPTY
        message = apiMessage
        return this
    }

    fun responseLoading(flag: Int? = null,flagLoadRefresh : Int? = null,flagLoadView : Int? = null,flagLoadDialog : Boolean? = false,isHideView : Boolean? = false): ApiResponse {
        flagView = flag
        flagLoadingRefresh = flagLoadRefresh
        flagLoadingView = flagLoadView
        flagLoadingDialog = flagLoadDialog!!
        isHide = isHideView!!
        status = ApiStatus.LOADING
        return this
    }

    private fun resetSessionToken() {
        isTokenExpired = true
    }

}