package ahm.parts.ordering.ui.home.home.partnumber.cart.skemapembelian

import ahm.parts.ordering.R
import ahm.parts.ordering.api.ApiResponse
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dashboard.partnumber.cart.CartFilter
import ahm.parts.ordering.data.model.home.dashboard.partnumber.cart.skemapembelian.SkemaPembelian
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseViewModel
import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import javax.inject.Inject


class SkemaPembelianViewModel @Inject constructor(private val context : Application) : BaseViewModel(){

    val skemaPembelian = MutableLiveData<SkemaPembelian>()

    fun hitSkemaPembelian(dealerId : String,partId : String,filters : List<CartFilter>) {

        apiResponse.postValue(ApiResponse().responseLoading(R.id.rootContent,flagLoadView = R.id.lLoadingView,isHideView = true))

        val partNumber = filters[0].isCheckInt
        val partDescription = filters[1].isCheckInt
        val het = filters[2].isCheckInt
        val qtyW1 = filters[3].isCheckInt
        val amountW1 = filters[4].isCheckInt
        val qtyW2 = filters[5].isCheckInt
        val amountW2 = filters[6].isCheckInt
        val qtyW3 = filters[7].isCheckInt
        val amountW3 = filters[8].isCheckInt
        val qtyW4 = filters[9].isCheckInt
        val amountW4 = filters[10].isCheckInt
        val qtyAvg = filters[11].isCheckInt
        val amountAvg = filters[12].isCheckInt
        val qtyBack = filters[13].isCheckInt
        val amountBack = filters[14].isCheckInt
        val qtySuggest = filters[15].isCheckInt
        val amountSuggest = filters[16].isCheckInt
        val flagCampaign = filters[17].isCheckInt
        val multipleDus = filters[18].isCheckInt


        compositeDisposable.add(
            apiService.getSkemaPembelian(partId,partNumber,partDescription,het,qtyW1,amountW1,qtyW2,amountW2,qtyW3,amountW3,qtyW4,amountW4,qtyAvg,amountAvg,qtyBack,amountBack,qtySuggest,amountSuggest,flagCampaign,multipleDus,dealerId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({

                    Log.e("skemaPemb",it)

                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        val data = responseJson.obj(Constants.REMOTE.OBJ_DATA).getObject<SkemaPembelian>()
                        skemaPembelian.value = data

                        apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.rootContent,flagLoadView = R.id.lLoadingView,isHideView = true))

                    } else {
                        apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.rootContent,flagLoadView = R.id.lLoadingView,isHideView = true))
                    }
                }, {
                    apiResponse.postValue(ApiResponse().responseError(it,R.id.rootContent,flagLoadView = R.id.lLoadingView,isHideView = true))
                })
        )
    }

    fun hitUpdateQty(idPart : String,qty : String,dealerId: String) {

        apiResponse.postValue(ApiResponse().responseLoading(R.id.btnSetQty,flagLoadDialog = true))

        compositeDisposable.add(
            apiService.partNumberUpdateQty(idPart,qty,dealerId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({

                    Log.e("responseHitQtyUpdate",it)

                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.btnSetQty,flagLoadDialog = true))

                    } else {
                        apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.btnSetQty,flagLoadDialog = true))
                    }
                }, {
                    apiResponse.postValue(ApiResponse().responseError(it,R.id.btnSetQty,flagLoadDialog = true))
                })
        )
    }

}