package ahm.parts.ordering.ui.home.profile

import ahm.parts.ordering.R
import ahm.parts.ordering.api.ApiResponse
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.notification.Notification
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseViewModel
import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import javax.inject.Inject

class ProfileViewModel @Inject constructor(private val context : Application) : BaseViewModel(){

    val changePassword = MutableLiveData<String>()

    fun hitChangePassword(password : String) {

        apiResponse.postValue(ApiResponse().responseLoading(flagLoadDialog = true))

        compositeDisposable.add(
            apiService.changePassword(password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        changePassword.postValue(apiMessage[0].toString())

                        apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,flagLoadDialog = true))

                    } else {
                        apiResponse.postValue(ApiResponse().responseWrong(apiMessage,flagLoadDialog = true))
                    }
                }, {
                    apiResponse.postValue(ApiResponse().responseError(it,flagLoadDialog = true))
                })
        )
    }
}