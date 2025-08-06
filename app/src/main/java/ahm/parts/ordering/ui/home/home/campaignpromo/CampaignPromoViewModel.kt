package ahm.parts.ordering.ui.home.home.campaignpromo

import ahm.parts.ordering.R
import ahm.parts.ordering.api.ApiResponse
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.campaignpromo.BrosurePromo
import ahm.parts.ordering.data.model.home.campaignpromo.PartPromo
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseViewModel
import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import javax.inject.Inject


class CampaignPromoViewModel @Inject constructor(private val context : Application) : BaseViewModel(){

    val brosureList = MutableLiveData<List<BrosurePromo>>()
    val brosureListLoadMore = MutableLiveData<List<BrosurePromo>>()

    /**
     * Fungsi untuk menembak api brosure promo
     *
     */
    fun hitBrosure(page : Int,isLoadMore : Boolean) {

        if(!isLoadMore){
            apiResponse.postValue(ApiResponse().responseLoading(R.id.rvBrosur,R.id.srRefresh,isHideView = true))
        }else{
            apiResponse.postValue(ApiResponse().responseLoading(R.id.rvBrosur,flagLoadView = R.id.pbLoadingBottom))
        }

        compositeDisposable.add(
            apiService.getPromoBrosure(page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val responseJson = JSONObject(it)

                    Log.e("responseCampaignPromo",it)

                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        val dataBrosure = responseJson.obj(Constants.REMOTE.OBJ_DATA).array(Constants.REMOTE.ARR_DATA).toList<BrosurePromo>()

                        if(!isLoadMore){
                            brosureList.postValue(dataBrosure)

                            apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.rvBrosur,R.id.srRefresh,isHideView = true))

                            if(dataBrosure.isEmpty()){
                                apiResponse.postValue(ApiResponse().responseEmpty(apiMessage,R.id.rvBrosur,R.id.srRefresh,isHideView = true))
                            }

                        }else{
                            brosureListLoadMore.postValue(dataBrosure)

                            apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.rvBrosur,flagLoadView = R.id.pbLoadingBottom))
                        }


                    } else {
                        if(!isLoadMore){
                            apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.rvBrosur,R.id.srRefresh,isHideView = true))
                        }else{
                            apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.rvBrosur,flagLoadView = R.id.pbLoadingBottom,isHideView = true))
                        }
                    }
                }, {
                    if(!isLoadMore){
                        apiResponse.postValue(ApiResponse().responseError(it,R.id.rvBrosur,R.id.srRefresh,isHideView = true))
                    }else{
                        apiResponse.postValue(ApiResponse().responseError(it,R.id.rvBrosur,flagLoadView = R.id.pbLoadingBottom,isHideView = true))
                    }
                })
        )
    }

    val partPromoList = MutableLiveData<List<PartPromo>>()
    val partPromoListLoadMore = MutableLiveData<List<PartPromo>>()

    /**
     * Fungsi untuk menembak api part promo
     *
     */
    fun hitPartPromo(page : Int,isLoadMore: Boolean) {

        if(!isLoadMore){
            apiResponse.postValue(ApiResponse().responseLoading(R.id.rvPartPromo,R.id.srRefresh,isHideView = true))
        }else{
            apiResponse.postValue(ApiResponse().responseLoading(R.id.rvPartPromo,flagLoadView = R.id.pbLoadingBottom))
        }

        compositeDisposable.add(
            apiService.getPartPromo(page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val responseJson = JSONObject(it)

                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        val dataPartPromo = responseJson.obj(Constants.REMOTE.OBJ_DATA).array(Constants.REMOTE.ARR_DATA).toList<PartPromo>()

                        if(!isLoadMore){
                            partPromoList.postValue(dataPartPromo)

                            apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.rvPartPromo,R.id.srRefresh,isHideView = true))

                            if(dataPartPromo.isEmpty()){
                                apiResponse.postValue(ApiResponse().responseEmpty(apiMessage,R.id.rvPartPromo,R.id.srRefresh,isHideView = true))
                            }

                        }else{
                            partPromoListLoadMore.postValue(dataPartPromo)

                            apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.rvPartPromo,flagLoadView = R.id.pbLoadingBottom))
                        }

                    } else {
                        if(!isLoadMore){
                            apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.rvPartPromo,R.id.srRefresh,isHideView = true))
                        }else{
                            apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.rvPartPromo,flagLoadView = R.id.pbLoadingBottom,isHideView = true))
                        }
                    }
                }, {
                    if(!isLoadMore){
                        apiResponse.postValue(ApiResponse().responseError(it,R.id.rvPartPromo,R.id.srRefresh,isHideView = true))
                    }else{
                        apiResponse.postValue(ApiResponse().responseError(it,R.id.rvPartPromo,flagLoadView = R.id.pbLoadingBottom,isHideView = true))
                    }
                })
        )
    }

}