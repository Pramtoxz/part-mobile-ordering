package ahm.parts.ordering.ui.home.dealer.salesmanvisit.efektivitas

import ahm.parts.ordering.R
import ahm.parts.ordering.api.ApiResponse
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dashboard.partnumber.kodedealer.AllDealer
import ahm.parts.ordering.data.model.home.dashboard.partnumber.kodedealer.KodeDealer
import ahm.parts.ordering.data.model.home.dealer.efektivitas.EfData
import ahm.parts.ordering.data.model.home.dealer.efektivitas.EfektivitasCoordinatorManager
import ahm.parts.ordering.data.model.home.dealer.efektivitas.EfektivitasPlanActualParam
import ahm.parts.ordering.data.model.home.dealer.efektivitas.EfektivitasVisit
import ahm.parts.ordering.data.model.home.dealer.realisasivisit.*
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseViewModel
import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_efektivitas_visit_plan_actual.*
import org.json.JSONObject
import javax.inject.Inject

class EfektivitasViewModel @Inject constructor(private val context : Application) : BaseViewModel(){

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

    val efektivitasVisit = MutableLiveData<EfektivitasVisit>()

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
//
//        Log.e("strKoordinatorSalesman",strKoordinatorSalesman)
//        Log.e("strSalesman",strSalesman)
//        Log.e("strDealer",strDealer)
//        Log.e("startDate",startDate)
//        Log.e("endDate",endDate)

        compositeDisposable.add(
            apiService.getEfektivitasVisit(strKoordinatorSalesman,strSalesman,strDealer,startDate,endDate)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({

                    Log.e("efektivitasLog",it)

                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        val data = responseJson.obj(Constants.REMOTE.OBJ_DATA).getObject<EfektivitasVisit>()
                        efektivitasVisit.postValue(data)

                        apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.btnViewPlanActual,flagLoadDialog = true))

                    } else {
                        apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.btnViewPlanActual,flagLoadDialog = true))
                    }
                }, {
                    apiResponse.postValue(ApiResponse().responseError(it,R.id.btnViewPlanActual,flagLoadDialog = true))
                })
        )
    }


    fun hitEfektivitasSalesman(dealer : String,startDate : String,endDate : String,isAllDealer : Boolean) {

        apiResponse.postValue(ApiResponse().responseLoading(R.id.btnViewPlanActual,flagLoadDialog = true))

        Log.e("startDate",startDate)
        Log.e("endDate",endDate)

        var dealerArrays = ""
        if(!isAllDealer){
            dealerArrays = dealer
        }

        Log.e("dealerJson",dealerArrays)

        compositeDisposable.add(
            apiService.getEfektivitasSalesman(dealerArrays,startDate,endDate,1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({

                    Log.e("efektivitasLog",it)

                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        val data = responseJson.obj(Constants.REMOTE.OBJ_DATA).getObject<EfektivitasVisit>()
                        efektivitasVisit.postValue(data)

                        apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.btnViewPlanActual,flagLoadDialog = true))

                    } else {
                        apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.btnViewPlanActual,flagLoadDialog = true))
                    }
                }, {
                    apiResponse.postValue(ApiResponse().responseError(it,R.id.btnViewPlanActual,flagLoadDialog = true))
                })
        )
    }


    fun hitEfektivitasCoordinator(salesman: String,dealer : String,startDate : String,endDate : String,isAllDealer : Boolean) {

        apiResponse.postValue(ApiResponse().responseLoading(R.id.btnViewPlanActual,flagLoadDialog = true))

        Log.e("startDate",startDate)
        Log.e("endDate",endDate)

        var dealerArrays = ""
        if(!isAllDealer){
            dealerArrays = dealer
        }

        Log.e("dealerJson",dealerArrays)
        Log.e("salesmanSelected",salesman)

        compositeDisposable.add(
            apiService.getEfektivitasCoordinator(salesman,dealerArrays,startDate,endDate,1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({

                    Log.e("efektivitasLog",it)

                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        val data = responseJson.obj(Constants.REMOTE.OBJ_DATA).getObject<EfektivitasVisit>()
                        efektivitasVisit.postValue(data)

                        apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.btnViewPlanActual,flagLoadDialog = true))

                    } else {
                        apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.btnViewPlanActual,flagLoadDialog = true))
                    }
                }, {
                    apiResponse.postValue(ApiResponse().responseError(it,R.id.btnViewPlanActual,flagLoadDialog = true))
                })
        )
    }




    val efektivitasPlanActualVisit = MutableLiveData<EfektivitasVisit>()
    val efektivitasPlanActualVisitLoadMore = MutableLiveData<EfektivitasVisit>()

    fun hitEfektivitasPlanVisitSalesman(page : Int,param : EfektivitasPlanActualParam,isLoadMore : Boolean) {

        val dealer = param.dealer
        val startDate = param.startTime
        val endDate = param.endTime
        val isAllDealer = param.isAllDealer

        if(!isLoadMore){
            apiResponse.postValue(ApiResponse().responseLoading(R.id.rvEfektivitas,flagLoadView = R.id.lLoadingView,isHideView = true))
        }else{
            apiResponse.postValue(ApiResponse().responseLoading(R.id.rvEfektivitas,flagLoadView = R.id.pbLoadingBottom))
        }

        var dealerArrays = ""
        if(!isAllDealer){
            dealerArrays = dealer
        }

        Log.e("dealerJson",dealerArrays)

        compositeDisposable.add(
            apiService.getEfektivitasSalesman(dealerArrays,startDate,endDate,page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({

                    Log.e("efektivitasLog",it)

                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        val data = responseJson.obj(Constants.REMOTE.OBJ_DATA).getObject<EfektivitasVisit>()

                        if(!isLoadMore){
                            efektivitasPlanActualVisit.postValue(data)

                            apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.rvEfektivitas,flagLoadView = R.id.lLoadingView,isHideView = true))

                            if(data.data.isEmpty()){
                                apiResponse.postValue(ApiResponse().responseEmpty(apiMessage,R.id.rvEfektivitas,flagLoadView = R.id.lLoadingView,isHideView = true))
                            }

                        }else{
                            efektivitasPlanActualVisitLoadMore.postValue(data)

                            apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.rvEfektivitas,flagLoadView = R.id.pbLoadingBottom,isHideView = true))
                        }

                    } else {
                        if(!isLoadMore){
                            apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.rvEfektivitas,flagLoadView = R.id.lLoadingView,isHideView = true))
                        }else{
                            apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.rvEfektivitas,flagLoadView = R.id.pbLoadingBottom,isHideView = true))
                        }
                    }
                }, {
                    if(!isLoadMore){
                        apiResponse.postValue(ApiResponse().responseError(it,R.id.rvEfektivitas,flagLoadView = R.id.lLoadingView,isHideView = true))
                    }else{
                        apiResponse.postValue(ApiResponse().responseError(it,R.id.rvEfektivitas,flagLoadView = R.id.pbLoadingBottom,isHideView = true))
                    }
                })
        )
    }


    val efektivitasDetail = MutableLiveData<EfData>()
    @SuppressLint("LongLogTag")
    fun getEfektivasNextSalesman(idEfektivitas : String,param : EfektivitasPlanActualParam) {

        apiResponse.postValue(ApiResponse().responseLoading(R.id.btnNext,flagLoadDialog = true))

        val dealer = param.dealer
        val startTime = param.startTime
        val endTime = param.endTime
        val isAllDealer = param.isAllDealer

        var dealerArrays = ""
        if(!isAllDealer){
            dealerArrays = dealer
        }


        Log.e("idEfektivitas",idEfektivitas)

        compositeDisposable.add(
            apiService.getEfektivitasNextSalesman(idEfektivitas,dealerArrays,startTime,endTime)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({

                    Log.e("efektivitasNextSalesmanRes",it)

                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        val data = responseJson.obj(Constants.REMOTE.OBJ_DATA).getObject<EfData>()

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
    fun getEfektivasNextCoordinator(idEfektivitas : String,param : EfektivitasPlanActualParam) {

        var dealer = param.dealer
        var startTime = param.startTime
        var endTime = param.endTime
        var isAllDealer = param.isAllDealer
        var isAllSalesman = param.isAllSalesman
        var idSalesman = param.salesman

        apiResponse.postValue(ApiResponse().responseLoading(R.id.btnNext,flagLoadDialog = true))

        var dealerArrays = ""
        if(!isAllDealer){
            dealerArrays = dealer
        }

        var salesmanArrays = ""
        if(!isAllSalesman){
            salesmanArrays = idSalesman
        }

        Log.e("idSalesman",salesmanArrays)

        compositeDisposable.add(
            apiService.getEfektivitasNextCoordinator(salesmanArrays,dealerArrays,startTime,endTime,idEfektivitas)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({

                    Log.e("efektivitasNextKoordinatorRes",it)

                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        val data = responseJson.obj(Constants.REMOTE.OBJ_DATA).getObject<EfData>()

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



    fun hitEfektivitasPlanVisitCoordinator(page : Int, param : EfektivitasPlanActualParam,isLoadMore : Boolean) {

        var salesmanId = param.salesman
        var dealer = param.dealer
        var startDate = param.startTime
        var endDate = param.endTime
        var isAllDealer  = param.isAllDealer
        var isAllSalesman  = param.isAllSalesman

        if(!isLoadMore){
            apiResponse.postValue(ApiResponse().responseLoading(R.id.rvEfektivitas,flagLoadView = R.id.lLoadingView,isHideView = true))
        }else{
            apiResponse.postValue(ApiResponse().responseLoading(R.id.rvEfektivitas,flagLoadView = R.id.pbLoadingBottom))
        }

        Log.e("salesmanId",salesmanId)
        Log.e("dealer",dealer)
        Log.e("startDate",startDate)
        Log.e("endDate",endDate)
        Log.e("pageVisit","${page}")

        var dealerArrays = ""
        if(!isAllDealer){
            dealerArrays = dealer
        }

        var salesmanArrays = ""
        if(!isAllSalesman){
            salesmanArrays = salesmanId
        }

        Log.e("dealerJson",dealerArrays)

        compositeDisposable.add(
            apiService.getEfektivitasCoordinator(salesmanArrays,dealerArrays,startDate,endDate,page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({

                    Log.e("efektivitasLog",it)

                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        val data = responseJson.obj(Constants.REMOTE.OBJ_DATA).getObject<EfektivitasVisit>()

                        if(!isLoadMore){
                            efektivitasPlanActualVisit.postValue(data)

                            apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.rvEfektivitas,flagLoadView = R.id.lLoadingView,isHideView = true))

                            if(data.data.isEmpty()){
                                apiResponse.postValue(ApiResponse().responseEmpty(apiMessage,R.id.rvEfektivitas,flagLoadView = R.id.lLoadingView,isHideView = true))
                            }

                        }else{
                            efektivitasPlanActualVisitLoadMore.postValue(data)

                            apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.rvEfektivitas,flagLoadView = R.id.pbLoadingBottom,isHideView = true))
                        }

                    } else {
                        if(!isLoadMore){
                            apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.rvEfektivitas,flagLoadView = R.id.lLoadingView,isHideView = true))
                        }else{
                            apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.rvEfektivitas,flagLoadView = R.id.pbLoadingBottom,isHideView = true))
                        }
                    }
                }, {
                    if(!isLoadMore){
                        apiResponse.postValue(ApiResponse().responseError(it,R.id.rvEfektivitas,flagLoadView = R.id.lLoadingView,isHideView = true))
                    }else{
                        apiResponse.postValue(ApiResponse().responseError(it,R.id.rvEfektivitas,flagLoadView = R.id.pbLoadingBottom,isHideView = true))
                    }
                })
        )
    }


    val efektivitasPlanActualVisitManager = MutableLiveData<EfektivitasCoordinatorManager>()
    val efektivitasPlanActualVisitManagerLoadMore = MutableLiveData<EfektivitasCoordinatorManager>()

    fun getEfektivitasPlanVisitCoordinatorManager(page : Int,param : EfektivitasPlanActualParam,isLoadMore : Boolean) {

        val koordinator = param.koordinator
        val dealer = param.dealer
        val salesman = param.salesman
        val startDate = param.startTime
        val endDate = param.endTime
        val isAllDealer = param.isAllDealer
        val isAllSalesman = param.isAllSalesman
        val isAllKoordinator = param.isAllKoordinator

        if(!isLoadMore){
            apiResponse.postValue(ApiResponse().responseLoading(R.id.rvEfektivitas,flagLoadView = R.id.lLoadingView,isHideView = true))
        }else{
            apiResponse.postValue(ApiResponse().responseLoading(R.id.rvEfektivitas,flagLoadView = R.id.pbLoadingBottom))
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

        var koordinatorArrays = ""
        if(!isAllKoordinator){
            koordinatorArrays = koordinator
        }

        compositeDisposable.add(
            apiService.getEfektvitasCoordinatorManager(koordinatorArrays,salesmanArrays,dealerArrays,startDate,endDate,page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({

                    Log.e("managerRealisasiLog",it)

                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        val data = responseJson.obj(Constants.REMOTE.OBJ_DATA).getObject<EfektivitasCoordinatorManager>()

                        if(!isLoadMore){
                            efektivitasPlanActualVisitManager.postValue(data)

                            apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.rvEfektivitas,flagLoadView = R.id.lLoadingView,isHideView = true))

                            if(data.data.isEmpty()){
                                apiResponse.postValue(ApiResponse().responseEmpty(apiMessage,R.id.rvEfektivitas,flagLoadView = R.id.lLoadingView,isHideView = true))
                            }

                        }else{
                            efektivitasPlanActualVisitManagerLoadMore.postValue(data)

                            apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.rvEfektivitas,flagLoadView = R.id.pbLoadingBottom,isHideView = true))
                        }

                    } else {
                        if(!isLoadMore){
                            apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.rvEfektivitas,flagLoadView = R.id.lLoadingView,isHideView = true))
                        }else{
                            apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.rvEfektivitas,flagLoadView = R.id.pbLoadingBottom,isHideView = true))
                        }
                    }
                }, {
                    if(!isLoadMore){
                        apiResponse.postValue(ApiResponse().responseError(it,R.id.rvEfektivitas,flagLoadView = R.id.lLoadingView,isHideView = true))
                    }else{
                        apiResponse.postValue(ApiResponse().responseError(it,R.id.rvEfektivitas,flagLoadView = R.id.pbLoadingBottom,isHideView = true))
                    }
                })
        )
    }

}