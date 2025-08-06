package ahm.parts.ordering.ui.home.dealer.salesmanvisit.visit

import ahm.parts.ordering.R
import ahm.parts.ordering.api.ApiResponse
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dealer.salemanvisit.RencanaVisit
import ahm.parts.ordering.data.model.home.dealer.salemanvisit.VisitAdd
import ahm.parts.ordering.data.model.home.dashboard.partnumber.kodedealer.AllDealer
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseViewModel
import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import javax.inject.Inject

class VisitViewModel @Inject constructor(private val context : Application) : BaseViewModel(){

    val rencanaVisits = MutableLiveData<List<RencanaVisit>>()

    fun hitRencanaVisit(msDealedId : String) {

        apiResponse.postValue(ApiResponse().responseLoading(R.id.rvVisit,R.id.swipeRefresh,isHideView = true))

        compositeDisposable.add(
            apiService.getRencanaVisit(msDealedId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        val data = responseJson.array(Constants.REMOTE.ARR_DATA).toList<RencanaVisit>()
                        rencanaVisits.postValue(data)

                        apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.rvVisit,R.id.swipeRefresh,isHideView = true))

                    } else {
                        apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.rvVisit,R.id.swipeRefresh,isHideView = true))
                    }
                }, {
                    apiResponse.postValue(ApiResponse().responseError(it,R.id.rvVisit,R.id.swipeRefresh,isHideView = true))
                })
        )
    }

    val visitAdd = MutableLiveData<VisitAdd>()

    fun hitVisitAdd(latitude : String,longitude : String,kodeDealer : AllDealer,rencanaVisit : RencanaVisit,flagLoading : Boolean) {

        apiResponse.postValue(ApiResponse().responseLoading(R.id.btnNext,flagLoadDialog = flagLoading))

        compositeDisposable.add(
            apiService.addSalesVisit(latitude,longitude,"${kodeDealer.id}",rencanaVisit.date)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({

                    Log.e("responVisit",it)

                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    when (apiStatus) {
                        Constants.REMOTE.API_STATUS_SUCCESS -> {

                            val data = responseJson.obj(Constants.REMOTE.OBJ_DATA).getObject<VisitAdd>()
                            data.status = 1
                            visitAdd.postValue(data)

                            apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.btnNext,flagLoadDialog = flagLoading))

                        }
                        0 -> {
                            val data = responseJson.obj(Constants.REMOTE.OBJ_DATA).getObject<VisitAdd>()
                            data.status = 0
                            visitAdd.postValue(data)

                            apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.btnNext,flagLoadDialog = flagLoading))
                        }
                        else -> {
                            apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.btnNext,flagLoadDialog = flagLoading))
                        }
                    }
                }, {
                    apiResponse.postValue(ApiResponse().responseError(it,R.id.btnNext,flagLoadDialog = flagLoading))
                })
        )
    }


    fun hitCheckin(codeVisit : String) {

        apiResponse.postValue(ApiResponse().responseLoading(R.id.btnCheckin,flagLoadDialog = true))

        compositeDisposable.add(
            apiService.salesVisitCheckin(codeVisit)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({

                    Log.e("responCheckin",it)

                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.btnCheckin,flagLoadDialog = true))

                    } else {
                        apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.btnCheckin,flagLoadDialog = true))
                    }
                }, {
                    apiResponse.postValue(ApiResponse().responseError(it,R.id.btnCheckin,flagLoadDialog = true))
                })
        )
    }


}