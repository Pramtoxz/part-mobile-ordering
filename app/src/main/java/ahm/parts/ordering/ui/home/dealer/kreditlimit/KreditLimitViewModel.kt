package ahm.parts.ordering.ui.home.dealer.kreditlimit

import ahm.parts.ordering.R
import ahm.parts.ordering.api.ApiResponse
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dealer.salemanvisit.RencanaVisit
import ahm.parts.ordering.data.model.home.dealer.salemanvisit.VisitAdd
import ahm.parts.ordering.data.model.home.dashboard.partnumber.kodedealer.AllDealer
import ahm.parts.ordering.data.model.home.dealer.kreditlimit.KreditJatuhTempo
import ahm.parts.ordering.data.model.home.dealer.kreditlimit.KreditLimit
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseViewModel
import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import javax.inject.Inject

class KreditLimitViewModel @Inject constructor(private val context : Application) : BaseViewModel(){

    val kreditLimit = MutableLiveData<KreditLimit>()

    fun hitCheckKreditLimit(msDealedId : String) {

        apiResponse.postValue(ApiResponse().responseLoading(R.id.btnCheckCredit))

        compositeDisposable.add(
            apiService.kreditCheckLimit(msDealedId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        val data = responseJson.obj(Constants.REMOTE.OBJ_DATA).getObject<KreditLimit>()
                        kreditLimit.value = data

                        apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.btnCheckCredit))

                    } else {
                        apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.btnCheckCredit))
                    }
                }, {
                    apiResponse.postValue(ApiResponse().responseError(it,R.id.btnCheckCredit))
                })
        )
    }

    val kreditJatuhTempo = MutableLiveData<KreditJatuhTempo>()

    fun hitCheckKreditJatuhTempo(msDealedId : String) {

        apiResponse.postValue(ApiResponse().responseLoading(R.id.btnCheckCredit))

        compositeDisposable.add(
            apiService.kreditJatuhTempo(msDealedId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        val data = responseJson.obj(Constants.REMOTE.OBJ_DATA).getObject<KreditJatuhTempo>()
                        kreditJatuhTempo.value = data

                        apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.btnCheckCredit))


                    } else {
                        apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.btnCheckCredit))
                    }
                }, {
                    apiResponse.postValue(ApiResponse().responseError(it,R.id.btnCheckCredit))
                })
        )
    }

    val kreditLimits = MutableLiveData<List<KreditLimit>>()

    fun hitCheckKreditLimitList(msDealerJson : String) {

        apiResponse.postValue(ApiResponse().responseLoading(R.id.rvKreditLimit,R.id.swipeRefresh,isHideView = true))

        compositeDisposable.add(
            apiService.kreditLimit(msDealerJson)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        val data = responseJson.array(Constants.REMOTE.ARR_DATA).toList<KreditLimit>()
                        kreditLimits.postValue(data)

                        apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.rvKreditLimit,R.id.swipeRefresh,isHideView = true))

                        if(data.isEmpty()){
                            apiResponse.postValue(ApiResponse().responseEmpty(apiMessage,R.id.rvKreditLimit,R.id.swipeRefresh,isHideView = true))
                        }

                    } else {
                        apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.rvKreditLimit,R.id.swipeRefresh,isHideView = true))
                    }
                }, {
                    apiResponse.postValue(ApiResponse().responseError(it,R.id.rvKreditLimit,R.id.swipeRefresh,isHideView = true))
                })
        )
    }

    val kreditJatuhTempos = MutableLiveData<List<KreditJatuhTempo>>()

    fun hitKreditJatuhTempoList(msDealerJson : String) {

        apiResponse.postValue(ApiResponse().responseLoading(R.id.rvKreditLimit,R.id.swipeRefresh,isHideView = true))

        compositeDisposable.add(
            apiService.kreditJatuhTempoList(msDealerJson)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        val data = responseJson.array(Constants.REMOTE.ARR_DATA).toList<KreditJatuhTempo>()
                        kreditJatuhTempos.postValue(data)

                        apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.rvKreditLimit,R.id.swipeRefresh,isHideView = true))

                        if(data.isEmpty()){
                            apiResponse.postValue(ApiResponse().responseEmpty(apiMessage,R.id.rvKreditLimit,R.id.swipeRefresh,isHideView = true))
                        }

                    } else {
                        apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.rvKreditLimit,R.id.swipeRefresh,isHideView = true))
                    }
                }, {
                    apiResponse.postValue(ApiResponse().responseError(it,R.id.rvKreditLimit,R.id.swipeRefresh,isHideView = true))
                })
        )
    }

}