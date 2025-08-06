package ahm.parts.ordering.ui.home.order.tracking

import ahm.parts.ordering.R
import ahm.parts.ordering.api.ApiResponse
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.order.TrackingFilter
import ahm.parts.ordering.data.model.home.order.TrackingOrder
import ahm.parts.ordering.data.model.home.order.TrackingOrderDetail
import ahm.parts.ordering.data.model.home.order.TrackingOrderParam
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseViewModel
import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.File
import javax.inject.Inject

class TrackingOrderViewModel @Inject constructor(private val context : Application) : BaseViewModel(){

    val trackingOrders = MutableLiveData<List<TrackingOrder>>()

    fun hitTrackingOrder(msDealedId : String,search : String,sorting: String,trackingParam : TrackingOrderParam) {

        apiResponse.postValue(ApiResponse().responseLoading(R.id.rvTrackingOrder,R.id.swipeRefresh,isHideView = true))


        compositeDisposable.add(
            apiService.getTrackingOrder(msDealedId,search,sorting,trackingParam.partNumber,trackingParam.partDescription,trackingParam.month,trackingParam.noOrder,trackingParam.statusBo,trackingParam.statusInvoice,trackingParam.statusShipping)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        val data = responseJson.array(Constants.REMOTE.ARR_DATA).toList<TrackingOrder>()
                        trackingOrders.postValue(data)

                        apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.rvTrackingOrder,R.id.swipeRefresh,isHideView = true))

                        if(data.isEmpty()){
                            apiResponse.postValue(ApiResponse().responseEmpty(apiMessage,R.id.rvTrackingOrder,R.id.swipeRefresh,isHideView = true))
                        }

                    } else {
                        apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.rvTrackingOrder,R.id.swipeRefresh,isHideView = true))
                    }
                }, {
                    apiResponse.postValue(ApiResponse().responseError(it,R.id.rvTrackingOrder,R.id.swipeRefresh,isHideView = true))
                })
        )
    }


    val trackingOrderDetails = MutableLiveData<TrackingOrderDetail>()

    fun hitTrackingOrderDetail(trackingId : String) {

        apiResponse.postValue(ApiResponse().responseLoading(R.id.rvInvoice,flagLoadView = R.id.lLoadingView,isHideView = true))

        compositeDisposable.add(
            apiService.getTrackingOrderDetail(trackingId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        val data = responseJson.obj(Constants.REMOTE.OBJ_DATA).getObject<TrackingOrderDetail>()
                        trackingOrderDetails.postValue(data)

                        apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.rvInvoice,flagLoadView = R.id.lLoadingView,isHideView = true))

                    } else {
                        apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.rvInvoice,flagLoadView = R.id.lLoadingView,isHideView = true))
                    }
                }, {
                    apiResponse.postValue(ApiResponse().responseError(it,R.id.rvInvoice,flagLoadView = R.id.lLoadingView,isHideView = true))
                })
        )
    }

}