package ahm.parts.ordering.ui.widget.activity.prospect

import ahm.parts.ordering.api.ApiResponse
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.Prospek
import ahm.parts.ordering.helper.getObject
import ahm.parts.ordering.ui.base.BaseViewModel
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import javax.inject.Inject

class ProspectViewModel  @Inject constructor() : BaseViewModel(){

    val infoProspect = MutableLiveData<Prospek>()
    fun getInfoProspect() {

        apiResponse.postValue(ApiResponse().responseLoading())

        compositeDisposable.add(
            apiService.getInfoProspect()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.getInt(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.getJSONArray(Constants.REMOTE.API_MESSAGE)


                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        val jsonObjectData = responseJson.getJSONArray(Constants.REMOTE.ARR_DATA)


                        val item = jsonObjectData.toString().getObject<Prospek>()
                        infoProspect.value = item

                        apiResponse.postValue(ApiResponse().responseSuccess(apiMessage))
                    } else {
                        apiResponse.postValue(ApiResponse().responseWrong(apiMessage))
                    }
                }, {
                    val resError = ApiResponse().responseError(it)
                    apiResponse.postValue(resError)
                })
        )
    }
}