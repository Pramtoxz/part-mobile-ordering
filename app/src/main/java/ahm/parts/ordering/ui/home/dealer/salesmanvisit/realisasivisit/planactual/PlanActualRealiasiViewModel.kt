package ahm.parts.ordering.ui.home.dealer.salesmanvisit.realisasivisit.planactual

import ahm.parts.ordering.R
import ahm.parts.ordering.api.ApiResponse
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dashboard.partnumber.kodedealer.AllDealer
import ahm.parts.ordering.data.model.home.dashboard.partnumber.kodedealer.KodeDealer
import ahm.parts.ordering.data.model.home.dealer.realisasivisit.*
import ahm.parts.ordering.data.model.home.dealer.salemanvisit.RencanaVisit
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseViewModel
import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import javax.inject.Inject

class PlanActualRealiasiViewModel @Inject constructor(private val context : Application) : BaseViewModel(){

    val realisasiPlanActualVisit = MutableLiveData<RealisasiVisit>()
    val realisasiPlanActualVisitLoadMore = MutableLiveData<RealisasiVisit>()

    fun getRealisasiPlanVisitSalesman(page : Int,param : RealisasiVisitPlanActualParam,isLoadMore : Boolean) {

        val dealer = param.dealer
        val startDate = param.startTime
        val endDate = param.endTime
        val isAllDealer = param.isAllDealer

        if(!isLoadMore){
            apiResponse.postValue(ApiResponse().responseLoading(R.id.rvRealisasiVisit,flagLoadView = R.id.lLoadingView,isHideView = true))
        }else{
            apiResponse.postValue(ApiResponse().responseLoading(R.id.rvRealisasiVisit,flagLoadView = R.id.pbLoadingBottom))
        }

        var dealerArrays = ""
        if(!isAllDealer){
            dealerArrays = dealer
        }

        Log.e("dealerJson",dealerArrays)

        compositeDisposable.add(
            apiService.getRealisasiVisitSalesman(dealerArrays,startDate,endDate,page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({

                    Log.e("efektivitasLog",it)

                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        val data = responseJson.obj(Constants.REMOTE.OBJ_DATA).getObject<RealisasiVisit>()

                        if(!isLoadMore){
                            realisasiPlanActualVisit.postValue(data)

                            apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.rvRealisasiVisit,flagLoadView = R.id.lLoadingView,isHideView = true))

                            if(data.data.isEmpty()){
                                apiResponse.postValue(ApiResponse().responseEmpty(apiMessage,R.id.rvRealisasiVisit,flagLoadView = R.id.lLoadingView,isHideView = true))
                            }

                        }else{
                            realisasiPlanActualVisitLoadMore.postValue(data)

                            apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.rvRealisasiVisit,flagLoadView = R.id.pbLoadingBottom,isHideView = true))
                        }

                    } else {
                        if(!isLoadMore){
                            apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.rvRealisasiVisit,flagLoadView = R.id.lLoadingView,isHideView = true))
                        }else{
                            apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.rvRealisasiVisit,flagLoadView = R.id.pbLoadingBottom,isHideView = true))
                        }
                    }
                }, {
                    if(!isLoadMore){
                        apiResponse.postValue(ApiResponse().responseError(it,R.id.rvRealisasiVisit,flagLoadView = R.id.lLoadingView,isHideView = true))
                    }else{
                        apiResponse.postValue(ApiResponse().responseError(it,R.id.rvRealisasiVisit,flagLoadView = R.id.pbLoadingBottom,isHideView = true))
                    }
                })
        )
    }

    fun getRealisasiPlanVisitKoordinator(page : Int,param : RealisasiVisitPlanActualParam,isLoadMore : Boolean) {

        val salesman = param.salesman
        val dealer = param.dealer
        val startDate = param.startTime
        val endDate = param.endTime
        val isAllDealer = param.isAllDealer
        val isAllSalesman = param.isAllSalesman

        if(!isLoadMore){
            apiResponse.postValue(ApiResponse().responseLoading(R.id.rvRealisasiVisit,flagLoadView = R.id.lLoadingView,isHideView = true))
        }else{
            apiResponse.postValue(ApiResponse().responseLoading(R.id.rvRealisasiVisit,flagLoadView = R.id.pbLoadingBottom))
        }

        Log.e("startDate",startDate)
        Log.e("endDate",endDate)
        Log.e("pageVisit","${page}")

        var dealerArrays = ""
        if(!isAllDealer){
            dealerArrays = dealer
        }

        var salesmanArrays = ""
        if(!isAllSalesman){
            salesmanArrays = salesman
        }

        Log.e("salesmanArrays",salesmanArrays)
        Log.e("dealerJson",dealerArrays)

        compositeDisposable.add(
            apiService.getRealisasiVisitCoordinator(salesmanArrays,dealerArrays,startDate,endDate,page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({

                    Log.e("efektivitasLog",it)

                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        val data = responseJson.obj(Constants.REMOTE.OBJ_DATA).getObject<RealisasiVisit>()

                        if(!isLoadMore){
                            realisasiPlanActualVisit.postValue(data)

                            apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.rvRealisasiVisit,flagLoadView = R.id.lLoadingView,isHideView = true))

                            if(data.data.isEmpty()){
                                apiResponse.postValue(ApiResponse().responseEmpty(apiMessage,R.id.rvRealisasiVisit,flagLoadView = R.id.lLoadingView,isHideView = true))
                            }

                        }else{
                            realisasiPlanActualVisitLoadMore.postValue(data)

                            apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.rvRealisasiVisit,flagLoadView = R.id.pbLoadingBottom,isHideView = true))
                        }

                    } else {
                        if(!isLoadMore){
                            apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.rvRealisasiVisit,flagLoadView = R.id.lLoadingView,isHideView = true))
                        }else{
                            apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.rvRealisasiVisit,flagLoadView = R.id.pbLoadingBottom,isHideView = true))
                        }
                    }
                }, {
                    if(!isLoadMore){
                        apiResponse.postValue(ApiResponse().responseError(it,R.id.rvRealisasiVisit,flagLoadView = R.id.lLoadingView,isHideView = true))
                    }else{
                        apiResponse.postValue(ApiResponse().responseError(it,R.id.rvRealisasiVisit,flagLoadView = R.id.pbLoadingBottom,isHideView = true))
                    }
                })
        )
    }


    val realisasiPlanActualVisitManager = MutableLiveData<RealisasiCoordinatorManager>()
    val realisasiPlanActualVisitManagerLoadMore = MutableLiveData<RealisasiCoordinatorManager>()

    fun getRealisasiPlanVisitCoordinatorManager(page : Int,param : RealisasiVisitPlanActualParam,isLoadMore : Boolean) {

        val koordinator = param.koordinator
        val dealer = param.dealer
        val salesman = param.salesman
        val startDate = param.startTime
        val endDate = param.endTime
        val isAllDealer = param.isAllDealer
        val isAllSalesman = param.isAllSalesman
        val isAllKoordinator = param.isAllKoordinator

        if(!isLoadMore){
            apiResponse.postValue(ApiResponse().responseLoading(R.id.rvRealisasiVisit,flagLoadView = R.id.lLoadingView,isHideView = true))
        }else{
            apiResponse.postValue(ApiResponse().responseLoading(R.id.rvRealisasiVisit,flagLoadView = R.id.pbLoadingBottom))
        }

        var dealerArrays = ""
        if(!isAllDealer){
            dealerArrays = dealer
        }

        var salesmanArrays = ""
        if(!isAllSalesman){
            salesmanArrays = salesman
        }

        var koordinatorArrays = ""
        if(!isAllKoordinator){
            koordinatorArrays = koordinator
        }

        compositeDisposable.add(
            apiService.getRealisasiVisitCoordinatorManager(koordinatorArrays,salesmanArrays,dealerArrays,startDate,endDate,page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({

                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        val data = responseJson.obj(Constants.REMOTE.OBJ_DATA).getObject<RealisasiCoordinatorManager>()

                        if(!isLoadMore){
                            realisasiPlanActualVisitManager.postValue(data)

                            apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.rvRealisasiVisit,flagLoadView = R.id.lLoadingView,isHideView = true))

                            if(data.data.isEmpty()){
                                apiResponse.postValue(ApiResponse().responseEmpty(apiMessage,R.id.rvRealisasiVisit,flagLoadView = R.id.lLoadingView,isHideView = true))
                            }

                        }else{
                            realisasiPlanActualVisitManagerLoadMore.postValue(data)

                            apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.rvRealisasiVisit,flagLoadView = R.id.pbLoadingBottom,isHideView = true))
                        }

                    } else {
                        if(!isLoadMore){
                            apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.rvRealisasiVisit,flagLoadView = R.id.lLoadingView,isHideView = true))
                        }else{
                            apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.rvRealisasiVisit,flagLoadView = R.id.pbLoadingBottom,isHideView = true))
                        }
                    }
                }, {
                    if(!isLoadMore){
                        apiResponse.postValue(ApiResponse().responseError(it,R.id.rvRealisasiVisit,flagLoadView = R.id.lLoadingView,isHideView = true))
                    }else{
                        apiResponse.postValue(ApiResponse().responseError(it,R.id.rvRealisasiVisit,flagLoadView = R.id.pbLoadingBottom,isHideView = true))
                    }
                })
        )
    }

    val efektivitasDetail = MutableLiveData<Data>()
    fun getRealisasiVisitNextSalesman(idEfektivitas : String,param : RealisasiVisitPlanActualParam) {

        val dealerJson = param.dealer
        val startTime = param.startTime
        val endTime = param.endTime
        val isAllDealer = param.isAllDealer

        apiResponse.postValue(ApiResponse().responseLoading(R.id.btnNext,flagLoadDialog = true))

        Log.e("idEfektivitas",idEfektivitas)

        var dealerArrays = ""
        if(!isAllDealer){
            dealerArrays = dealerJson
        }


        compositeDisposable.add(
            apiService.getRealisasiVisitNextSalesman(idEfektivitas,dealerArrays,startTime,endTime)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({

                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        val data = responseJson.obj(Constants.REMOTE.OBJ_DATA).getObject<Data>()

                        efektivitasDetail.postValue(data)

                        apiResponse.postValue(
                            ApiResponse().responseSuccess(
                                apiMessage,
                                R.id.btnNext,
                                flagLoadDialog = true
                            )
                        )

                    } else {
                        apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.btnNext,flagLoadDialog = true))
                    }
                }, {
                    apiResponse.postValue(ApiResponse().responseError(it,R.id.btnNext,flagLoadDialog = true))

                })
        )
    }

    @SuppressLint("LongLogTag")
    fun getRealisasiVisitNextCoordinator(idEfektivitas : String,param : RealisasiVisitPlanActualParam) {

        var dealer = param.dealer
        var salesman = param.salesman
        var startTime = param.startTime
        var endTime = param.endTime
        var isAllDealer = param.isAllDealer
        var isAllSalemman = param.isAllSalesman

        apiResponse.postValue(ApiResponse().responseLoading(R.id.btnNext,flagLoadDialog = true))

        Log.e("idEfektivitas",idEfektivitas)

        var dealerArrays = ""
        if(!isAllDealer){
            dealerArrays = dealer
        }

        var salesmanArrays = ""
        if(!isAllSalemman){
            salesmanArrays = salesman
        }

        compositeDisposable.add(
            apiService.getRealisasiVisitNextCoordinator(idEfektivitas,salesmanArrays,dealerArrays,startTime,endTime)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({

                    Log.e("realisasiNextCoordinatorRes",it)

                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        val data = responseJson.obj(Constants.REMOTE.OBJ_DATA).getObject<Data>()

                        efektivitasDetail.postValue(data)

                        apiResponse.postValue(
                            ApiResponse().responseSuccess(
                                apiMessage,
                                R.id.btnNext,
                                flagLoadDialog = true
                            )
                        )

                    } else {
                        apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.btnNext,flagLoadDialog = true))
                    }
                }, {
                    apiResponse.postValue(ApiResponse().responseError(it,R.id.btnNext,flagLoadDialog = true))

                })
        )
    }



}