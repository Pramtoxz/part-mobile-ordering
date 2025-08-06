package ahm.parts.ordering.ui.home.home.partnumber.searchpart

import ahm.parts.ordering.R
import ahm.parts.ordering.api.ApiResponse
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dashboard.partnumber.PartNumberFilter
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseViewModel
import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import javax.inject.Inject


class SearchPartViewModel @Inject constructor(private val context : Application) : BaseViewModel(){

    val partNumberSearchs = MutableLiveData<List<PartNumberFilter>>()

    fun hitPartNumberSearch(similarity : String,partNumber : String,partDescription : String,itemGroup : String,motorType : String,shorting : String,idDealer : String) {

        apiResponse.postValue(ApiResponse().responseLoading(R.id.btnSearch,flagLoadDialog = true))

        compositeDisposable.add(
            apiService.getPartSearch(similarity,partNumber,partDescription,itemGroup,motorType,shorting,idDealer)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        val partData = responseJson.obj(Constants.REMOTE.OBJ_DATA).array(Constants.REMOTE.ARR_DATA).toList<PartNumberFilter>()
                        partNumberSearchs.value = partData

                        apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.btnSearch,flagLoadDialog = true))

                    } else {
                        apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.btnSearch,flagLoadDialog = true))
                    }
                }, {
                    apiResponse.postValue(ApiResponse().responseError(it,R.id.btnSearch,flagLoadDialog = true))
                })
        )
    }

}