package ahm.parts.ordering.ui.auth.login

import ahm.parts.ordering.R
import ahm.parts.ordering.api.ApiResponse
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.MessageState
import ahm.parts.ordering.data.room.user.User
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.receiver.MyFirebaseMessagingService
import ahm.parts.ordering.ui.base.BaseViewModel
import android.app.Application
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import javax.inject.Inject


class LoginViewModel @Inject constructor(private val context: Application) : BaseViewModel() {

    private var strUserName = ""
    private var strPassword = ""

    /**
     * mendapatkan token fcm
     */
    fun updateFcmTokenId() {
        MyFirebaseMessagingService.getTokenFCM(session)
    }

    /**
     * Validasi login sebelum mendapatkan token
     *
     * @param userName user name yang dimasukkan saat login
     * @param pass password yang dimasukkan saat login
     */
    fun loginValidation(userName: String, pass: String) {
        if (userName.isEmpty() || pass.isEmpty()) {
            val message = context.getString(R.string.login_lbl_alert_form)
            messageState.value = MessageState(message)
            return
        }
        strUserName = userName
        strPassword = pass
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
                        loginAuth(strUserName, strPassword)
                    } else {
                        apiResponse.value = ApiResponse().responseWrong(apiMessage)
                    }
                }, {
                    apiResponse.value = ApiResponse().responseError(it)
                })
        )
    }

    /**
     * Melakukan otentikasi login dengan server side
     *
     * @param userName user name yang dimasukkan saat login
     * @param pass password name yang dimasukkan saat login
     */
    private fun loginAuth(userName: String, pass: String) {

        Log.e("fcmid",session.fcmId)

        compositeDisposable.add(
            apiService.login(userName, pass, session.fcmId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val responseJson = JSONObject(it)

                    Log.e("responseJsonLogin",it)

                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {
                        val jsonObjectData = responseJson.obj(Constants.REMOTE.OBJ_DATA)
                        val user = gson.fromJson(jsonObjectData.toString(), User::class.java).copy(idUser = 1)
                        session.saveUserId(userName)

                        session.passwordUser = AESEncrypt(pass)
                        session.idRole = user.id_role
//                        session.saveSid(jsonObjectData.getString("session_id"))
                        userRepository.insert(user)
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