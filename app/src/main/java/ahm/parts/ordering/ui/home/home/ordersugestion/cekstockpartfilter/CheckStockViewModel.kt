package ahm.parts.ordering.ui.home.home.ordersugestion.cekstockpartfilter

import ahm.parts.ordering.R
import ahm.parts.ordering.api.ApiResponse
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dashboard.partnumber.PartNumberFilter
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseViewModel
import android.app.Application
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import javax.inject.Inject


class CheckStockViewModel @Inject constructor(private val context : Application) : BaseViewModel(){

    val stockFilters = MutableLiveData<List<PartNumberFilter>>()

    fun hitStockFilter(similarity : String,partNumber : String,partDescription : String,itemGroup : String,motorType : String,shorting : String) {

        apiResponse.postValue(ApiResponse().responseLoading(R.id.rvPartNumberFilter))

        compositeDisposable.add(
            apiService.getStockSearch(similarity,partNumber,partDescription,itemGroup,motorType,shorting)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        val partData = responseJson.obj(Constants.REMOTE.OBJ_DATA).array(Constants.REMOTE.ARR_DATA).toList<PartNumberFilter>()
                        stockFilters.value = partData

                        apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.rvPartNumberFilter))

                    } else {
                        apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.rvPartNumberFilter))
                    }
                }, {
                    apiResponse.postValue(ApiResponse().responseError(it,R.id.rvPartNumberFilter))
                })
        )
    }

}