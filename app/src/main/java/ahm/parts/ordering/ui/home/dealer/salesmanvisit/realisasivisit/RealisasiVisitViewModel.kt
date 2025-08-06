package ahm.parts.ordering.ui.home.dealer.salesmanvisit.realisasivisit

import ahm.parts.ordering.R
import ahm.parts.ordering.api.ApiResponse
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dashboard.partnumber.kodedealer.AllDealer
import ahm.parts.ordering.data.model.home.dashboard.partnumber.kodedealer.KodeDealer
import ahm.parts.ordering.data.model.home.dealer.realisasivisit.Data
import ahm.parts.ordering.data.model.home.dealer.realisasivisit.KoordinatorSalesman
import ahm.parts.ordering.data.model.home.dealer.realisasivisit.RealisasiVisit
import ahm.parts.ordering.data.model.home.dealer.realisasivisit.Salesman
import ahm.parts.ordering.data.model.home.dealer.salemanvisit.RencanaVisit
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseViewModel
import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import javax.inject.Inject

class RealisasiVisitViewModel @Inject constructor(private val context : Application) : BaseViewModel(){

    val kodeDealer = MutableLiveData<KodeDealer>()

    fun hitKodeDealer(search : String) {

        apiResponse.postValue(ApiResponse().responseLoading(R.id.rootContent,R.id.swipeRefresh,isHideView = true))

        compositeDisposable.add(
            apiService.getKodeDealerRealisasiVisit(search)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        val dealerData = responseJson.obj(Constants.REMOTE.OBJ_DATA).obj(Constants.REMOTE.OBJ_DATA).getObject<KodeDealer>()
                        kodeDealer.postValue(dealerData)

                        apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.rootContent,R.id.swipeRefresh,isHideView = true))

                    } else {
                        apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.rootContent,R.id.swipeRefresh,isHideView = true))
                    }
                }, {
                    apiResponse.postValue(ApiResponse().responseError(it,R.id.rootContent,R.id.swipeRefresh,isHideView = true))
                })
        )
    }

    val koordinatorSalesmans = MutableLiveData<List<KoordinatorSalesman>>()

    fun hitKoordinatorSalesman(search: String) {

        apiResponse.postValue(ApiResponse().responseLoading(R.id.rootContent,R.id.swipeRefresh,isHideView = true))

        compositeDisposable.add(
            apiService.getSalesKoordinator(search)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        val data = responseJson.obj(Constants.REMOTE.OBJ_DATA).array(Constants.REMOTE.ARR_DATA).toList<KoordinatorSalesman>()
                        koordinatorSalesmans.postValue(data)

                        apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.rootContent,R.id.swipeRefresh,isHideView = true))

                    } else {
                        apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.rootContent,R.id.swipeRefresh,isHideView = true))
                    }
                }, {
                    apiResponse.postValue(ApiResponse().responseError(it,R.id.rootContent,R.id.swipeRefresh,isHideView = true))
                })
        )
    }

    val salesmans = MutableLiveData<List<Salesman>>()

    fun hitSalesman(search: String) {

        apiResponse.postValue(ApiResponse().responseLoading(R.id.rootContent,R.id.swipeRefresh,isHideView = true))

        compositeDisposable.add(
            apiService.getSalesman(search)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        val data = responseJson.obj(Constants.REMOTE.OBJ_DATA).array(Constants.REMOTE.ARR_DATA).toList<Salesman>()
                        salesmans.postValue(data)

                        apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.rootContent,R.id.swipeRefresh,isHideView = true))

                    } else {
                        apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.rootContent,R.id.swipeRefresh,isHideView = true))
                    }
                }, {
                    apiResponse.postValue(ApiResponse().responseError(it,R.id.rootContent,R.id.swipeRefresh,isHideView = true))
                })
        )
    }

    val realisasiVisit = MutableLiveData<RealisasiVisit>()

    fun hitPlanVisit(koordinatorSalesman : KoordinatorSalesman,salesman: Salesman,dealer : AllDealer,startDate : String,endDate : String) {

        apiResponse.postValue(ApiResponse().responseLoading(R.id.btnViewPlanActual,flagLoadDialog = true))

        var strKoordinatorSalesman = ""
        var strSalesman = ""
        var strDealer = ""

        if(koordinatorSalesman.id.isNotEmpty()){
            strKoordinatorSalesman = koordinatorSalesman.id
        }

        if(salesman.id.isNotEmpty()){
            strSalesman = salesman.id
        }

        if(dealer.id != ""){
            strDealer = "${dealer.id}"
        }

        Log.e("strKoordinatorSalesman",strKoordinatorSalesman)
        Log.e("strSalesman",strSalesman)
        Log.e("strDealer",strDealer)
        Log.e("startDate",startDate)
        Log.e("endDate",endDate)

        compositeDisposable.add(
            apiService.getRealisasiVisit(strKoordinatorSalesman,strSalesman,strDealer,startDate,endDate)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        val data = responseJson.obj(Constants.REMOTE.OBJ_DATA).getObject<RealisasiVisit>()
                        realisasiVisit.postValue(data)

                        apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.btnViewPlanActual,flagLoadDialog = true))

                    } else {
                        apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.btnViewPlanActual,flagLoadDialog = true))
                    }
                }, {
                    apiResponse.postValue(ApiResponse().responseError(it,R.id.btnViewPlanActual,flagLoadDialog = true))
                })
        )
    }

    fun hitRealisasiSalesman(dealer : String,startDate : String,endDate : String,isAllDealer : Boolean) {

        apiResponse.postValue(ApiResponse().responseLoading(R.id.btnViewPlanActual,flagLoadDialog = true))

        var strDealer = ""

        if(!isAllDealer){
            strDealer = dealer
        }

        Log.e("strDealer",strDealer)
        Log.e("startDate",startDate)
        Log.e("endDate",endDate)

        compositeDisposable.add(
            apiService.getRealisasiVisitSalesman(strDealer,startDate,endDate,1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        val data = responseJson.obj(Constants.REMOTE.OBJ_DATA).getObject<RealisasiVisit>()
                        realisasiVisit.postValue(data)

                        apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.btnViewPlanActual,flagLoadDialog = true))

                    } else {
                        apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.btnViewPlanActual,flagLoadDialog = true))
                    }
                }, {
                    apiResponse.postValue(ApiResponse().responseError(it,R.id.btnViewPlanActual,flagLoadDialog = true))
                })
        )
    }

    fun hitRealisasiCoordinator(salesman : String,dealer : String,startDate : String,endDate : String,isAllDealer : Boolean) {

        apiResponse.postValue(ApiResponse().responseLoading(R.id.btnViewPlanActual,flagLoadDialog = true))

        var strDealer = ""

        if(!isAllDealer){
            strDealer = dealer
        }

        Log.e("strDealer",strDealer)
        Log.e("startDate",startDate)
        Log.e("endDate",endDate)

        compositeDisposable.add(
            apiService.getRealisasiVisitCoordinator(salesman,strDealer,startDate,endDate,1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        val data = responseJson.obj(Constants.REMOTE.OBJ_DATA).getObject<RealisasiVisit>()
                        realisasiVisit.postValue(data)

                        apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.btnViewPlanActual,flagLoadDialog = true))

                    } else {
                        apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.btnViewPlanActual,flagLoadDialog = true))
                    }
                }, {
                    apiResponse.postValue(ApiResponse().responseError(it,R.id.btnViewPlanActual,flagLoadDialog = true))
                })
        )
    }


    val realisasiPlanActualVisit = MutableLiveData<RealisasiVisit>()
    val realisasiPlanActualVisitLoadMore = MutableLiveData<RealisasiVisit>()

    fun getRealisasiPlanVisitSalesman(page : Int,dealer : String,startDate : String,endDate : String,isAllDealer : Boolean,isLoadMore : Boolean) {

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



}