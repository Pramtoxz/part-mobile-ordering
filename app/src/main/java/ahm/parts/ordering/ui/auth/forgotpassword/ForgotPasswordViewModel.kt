package ahm.parts.ordering.ui.auth.forgotpassword

import ahm.parts.ordering.R
import ahm.parts.ordering.api.ApiResponse
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.MessageState
import ahm.parts.ordering.helper.array
import ahm.parts.ordering.helper.int
import ahm.parts.ordering.ui.base.BaseViewModel
import android.app.Application
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import javax.inject.Inject


class ForgotPasswordViewModel @Inject constructor(private val context: Application) : BaseViewModel() {

    private var strEmail = ""

    /**
     * Validasi forgot password sebelum mendapatkan token
     *
     * @param userName user name yang dimasukkan saat forgot password
     * @param pass password yang dimasukkan saat forgot password
     */
    fun forgotValidation(email: String) {
        if (email.isEmpty()) {
            val message = context.getString(R.string.login_lbl_alert_form)
            messageState.value = MessageState(message)
            return
        }
        strEmail = email
        apiResponse.value = ApiResponse().responseLoading()
        session.saveAccessToken(null)
        session.saveSid(null)
        getOauthToken()
    }

    /**
     * Untuk mendapatkan token sebelum mengakses semua API
     * Result Token nantinya dikirim pada header param Authorization tiap request API
     *
     */
    private fun getOauthToken() {
        compositeDisposable.add(
            apiService.oauthToken()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({

                    Log.e("responseJsonToken",it)

                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {
                        val jsonObjectData = responseJson.getJSONObject(Constants.REMOTE.OBJ_DATA)
                        session.saveAccessToken(jsonObjectData.getString("token"))
                        forgotAuth(strEmail)
                    } else {
                        apiResponse.value = ApiResponse().responseWrong(apiMessage)
                    }
                }, {
                    apiResponse.value = ApiResponse().responseError(it)
                })
        )
    }

    /**
     * Melakukan otentikasi forgot password dengan server side
     *
     * @param userName user name yang dimasukkan saat forgot password
     * @param pass password name yang dimasukkan saat forgot password
     */
    private fun forgotAuth(email: String) {

        compositeDisposable.add(
            apiService.forgotPassword(email)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val responseJson = JSONObject(it)

                    Log.e("responseJsonForgot",it)

                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {
                        apiResponse.value = ApiResponse().responseSuccess(apiMessage)
                    } else {
                        session.saveAccessToken(null)
                        apiResponse.value = ApiResponse().responseWrong(apiMessage)
                    }
                }, {
                    val resError = ApiResponse().responseError(it)
                    if (resError.isTokenExpired) session.saveAccessToken(null)
                    apiResponse.value = resError
                })
        )
    }

}