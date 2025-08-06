package ahm.parts.ordering.ui.home.home.ordersugestion.ordersugestion

import ahm.parts.ordering.R
import ahm.parts.ordering.api.ApiResponse
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dashboard.ordersuggestion.SuggestOrder
import ahm.parts.ordering.data.model.home.dashboard.partnumber.cart.Cart
import ahm.parts.ordering.data.model.home.dashboard.partnumber.cart.summary.Summary
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseViewModel
import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import javax.inject.Inject


class SuggestOrderViewModel @Inject constructor(private val context : Application) : BaseViewModel(){

    val orderSuggestions = MutableLiveData<List<SuggestOrder>>()

    fun hitOrderSuggestion(dealerId : String) {

        apiResponse.postValue(ApiResponse().responseLoading(R.id.rootContent,R.id.swipeRefresh,isHideView = true))

        compositeDisposable.add(
            apiService.getOrderSuggestion(dealerId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({

                    Log.e("orderSuggestionResponse",it)

                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        val data = responseJson.obj(Constants.REMOTE.OBJ_DATA).array(Constants.REMOTE.OBJ_DATA).toList<SuggestOrder>()
                        orderSuggestions.value = data

                        apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.rootContent,R.id.swipeRefresh,isHideView = true))

                        if(data.isEmpty()){
                            apiResponse.postValue(ApiResponse().responseEmpty(apiMessage,R.id.rootContent,R.id.swipeRefresh,isHideView = true))
                        }

                    } else {
                        apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.rootContent,R.id.swipeRefresh,isHideView = true))
                    }
                }, {
                    apiResponse.postValue(ApiResponse().responseError(it,R.id.rootContent,R.id.swipeRefresh,isHideView = true))
                })
        )
    }

    fun hitUseSugestion(partJson : String,dealerId: String) {

        apiResponse.postValue(ApiResponse().responseLoading(R.id.btnCreateRo,flagLoadDialog = true))

        compositeDisposable.add(
            apiService.partUseSugestion(partJson,dealerId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({

                    Log.e("responseAddChart",it)
                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.btnCreateRo,flagLoadDialog = true))

                    } else {
                        apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.btnCreateRo,flagLoadDialog = true))
                    }
                }, {
                    apiResponse.postValue(ApiResponse().responseError(it,R.id.btnCreateRo,flagLoadDialog = true))
                })
        )
    }

    val summary = MutableLiveData<Summary>()

    fun hitSummary(monthDelivery : String,dealerId : String) {

        apiResponse.postValue(ApiResponse().responseLoading(R.id.btnSubmitOrder))

        Log.e("monthDelivery",monthDelivery)

        compositeDisposable.add(
            apiService.partSummary(monthDelivery,dealerId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({

                    Log.e("responseSubmitOrder",it)
                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        val data = responseJson.obj(Constants.REMOTE.OBJ_DATA).getObject<Summary>()
                        summary.value = data

                        apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.btnSubmitOrder))

                    } else {
                        apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.btnSubmitOrder))
                    }
                }, {
                    apiResponse.postValue(ApiResponse().responseError(it,R.id.btnSubmitOrder))
                })
        )
    }

}