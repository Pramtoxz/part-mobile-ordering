package ahm.parts.ordering.ui.home.order.order

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

class OrderViewModel @Inject constructor(private val context : Application) : BaseViewModel(){

    val trackingOrders = MutableLiveData<Boolean>()

    fun hitTrackingOrder(msDealedId : String,trackingParam: TrackingOrderParam) {

        Log.e("msDealerIdParamOrder",msDealedId)
        Log.e("trackingParams",trackingParam.getString())

        apiResponse.postValue(ApiResponse().responseLoading(R.id.btnTrackingOrder,flagLoadDialog = true))

        compositeDisposable.add(
            apiService.getTrackingOrder(msDealedId,"","",trackingParam.partNumber,trackingParam.partDescription,trackingParam.month,trackingParam.noOrder,trackingParam.statusBo,trackingParam.statusInvoice,trackingParam.statusShipping)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        trackingOrders.postValue(true)

                        apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.btnTrackingOrder,flagLoadDialog = true))

                    } else {
                        apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.btnTrackingOrder,flagLoadDialog = true))
                    }
                }, {
                    apiResponse.postValue(ApiResponse().responseError(it,R.id.btnTrackingOrder,flagLoadDialog = true))
                })
        )
    }

}