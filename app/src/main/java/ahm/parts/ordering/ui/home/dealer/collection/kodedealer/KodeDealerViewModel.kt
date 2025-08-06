package ahm.parts.ordering.ui.home.dealer.collection.kodedealer

import ahm.parts.ordering.R
import ahm.parts.ordering.api.ApiResponse
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dashboard.partnumber.PartNumberFilter
import ahm.parts.ordering.data.model.home.dashboard.partnumber.kodedealer.KodeDealer
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseViewModel
import android.app.Application
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import javax.inject.Inject


class KodeDealerViewModel @Inject constructor(private val context : Application) : BaseViewModel(){

    val kodeDealer = MutableLiveData<KodeDealer>()

    fun hitKodeDealer(search : String) {

        apiResponse.postValue(ApiResponse().responseLoading(R.id.rvKodeDealer,R.id.swipeRefresh))

        compositeDisposable.add(
            apiService.getKodeDealer(search)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        val dealerData = responseJson.obj(Constants.REMOTE.OBJ_DATA).obj(Constants.REMOTE.OBJ_DATA).getObject<KodeDealer>()
                        kodeDealer.value = dealerData

                        apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.rvKodeDealer,R.id.swipeRefresh))

                        if(dealerData.list.isEmpty()){
                            apiResponse.postValue(ApiResponse().responseEmpty(apiMessage,R.id.rvKodeDealer,R.id.swipeRefresh,isHideView = true))
                        }

                    } else {
                        apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.rvKodeDealer,R.id.swipeRefresh))
                    }
                }, {
                    apiResponse.postValue(ApiResponse().responseError(it,R.id.rvKodeDealer,R.id.swipeRefresh))
                })
        )
    }

}