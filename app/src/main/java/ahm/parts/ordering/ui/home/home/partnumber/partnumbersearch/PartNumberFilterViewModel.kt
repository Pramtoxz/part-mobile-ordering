package ahm.parts.ordering.ui.home.home.partnumber.partnumbersearch

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


class PartNumberFilterViewModel @Inject constructor(private val context : Application) : BaseViewModel(){

    val partNumberSearchs = MutableLiveData<List<PartNumberFilter>>()

    fun hitPartNumberSearch(similarity : String,partNumber : String,partDescription : String,itemGroup : String,motorType : String,shorting : String,idDealer : String) {

        apiResponse.postValue(ApiResponse().responseLoading(R.id.rvPartNumberFilter,R.id.swipeRefresh,isHideView = true))

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

                        apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.rvPartNumberFilter,R.id.swipeRefresh,isHideView = true))

                        if(partData.isEmpty()){
                            apiResponse.postValue(ApiResponse().responseEmpty(apiMessage,R.id.rvPartNumberFilter,R.id.swipeRefresh,isHideView = true))
                        }

                    } else {
                        apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.rvPartNumberFilter,R.id.swipeRefresh,isHideView = true))
                    }
                }, {
                    apiResponse.postValue(ApiResponse().responseError(it,R.id.rvPartNumberFilter,R.id.swipeRefresh,isHideView = true))
                })
        )
    }


    fun hitAddToCart(partJson : String,dealerId: String) {

        apiResponse.postValue(ApiResponse().responseLoading(R.id.btnChart,flagLoadDialog = true))

        compositeDisposable.add(
            apiService.partNumberAddToCartJson(partJson,dealerId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({

                    Log.e("responseAddChart",it)
                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.btnChart,flagLoadDialog = true))

                    } else {
                        apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.btnChart,flagLoadDialog = true))
                    }
                }, {
                    apiResponse.postValue(ApiResponse().responseError(it,R.id.btnChart,flagLoadDialog = true))
                })
        )
    }

    fun hitDeleteCart(idPart : String,dealerId: String) {

        compositeDisposable.add(
            apiService.partNumberRemoveCart(idPart,dealerId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                }, {
                })
        )
    }

    fun hitAddCart(idPart : String,dealerId: String) {
        compositeDisposable.add(
            apiService.partNumberAddToCart(idPart,dealerId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                }, {
                })
        )
    }

    fun hitAddFavorite(idPart : String,isLove : Int,dealerId : String) {

        apiResponse.postValue(ApiResponse().responseLoading(R.id.btnLike))

        compositeDisposable.add(
            apiService.addFavorite(idPart,isLove,dealerId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({

                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.btnLike))

                    } else {
                        apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.btnLike))
                    }
                }, {
                    apiResponse.postValue(ApiResponse().responseError(it,R.id.btnLike))
                })
        )
    }

}