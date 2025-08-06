package ahm.parts.ordering.ui.home

import ahm.parts.ordering.R
import ahm.parts.ordering.api.ApiResponse
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.catalogue.Catalogue
import ahm.parts.ordering.data.model.home.dashboard.Dashboard
import ahm.parts.ordering.data.model.home.dashboard.DashboardDealer
import ahm.parts.ordering.data.model.home.*
import ahm.parts.ordering.data.model.home.dashboard.CheckoutDashboard
import ahm.parts.ordering.data.model.home.wallboard.FilterBulan
import ahm.parts.ordering.data.model.home.wallboard.Hari
import ahm.parts.ordering.data.model.home.wallboard.MasterCalendar
import ahm.parts.ordering.data.model.stock.TypeFilter
import ahm.parts.ordering.data.model.stock.YearFilter
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseViewModel
import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import javax.inject.Inject


class HomeViewModel @Inject constructor(private val context : Application) : BaseViewModel(){

    val checkoutViewDashboard = MutableLiveData<CheckoutDashboard>()
    fun getCheckoutViewDashboard() {

        apiResponse.postValue(ApiResponse().responseLoading(R.id.vCheckout))

        compositeDisposable.add(
            apiService.checkinViewDashboard()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val responseJson = JSONObject(it)

                    Log.e("checkoutDashboard",it)

                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        val data = responseJson.obj(Constants.REMOTE.OBJ_DATA).getObject<CheckoutDashboard>()

                        checkoutViewDashboard.postValue(data)   

                        apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.vCheckout))

                    } else {
                        apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.vCheckout))
                    }
                }, {
                    apiResponse.postValue(ApiResponse().responseError(it,R.id.vCheckout))
                })
        )
    }

    fun hitCheckoutDashboard(codeVisit : String) {

        apiResponse.postValue(ApiResponse().responseLoading(flagLoadDialog = true))

        compositeDisposable.add(
            apiService.salesVisitCheckout(codeVisit)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val responseJson = JSONObject(it)

                    Log.e("checkoutDashboard",it)

                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,flagLoadDialog = true))

                    } else {
                        apiResponse.postValue(ApiResponse().responseWrong(apiMessage,flagLoadDialog = true))
                    }
                }, {
                    apiResponse.postValue(ApiResponse().responseError(it,flagLoadDialog = true))
                })
        )
    }


    val dashboardSalesmans = MutableLiveData<Dashboard>()
    fun hitDashboardSalesman(calendar : String) {

        val month = CalendarUtils.setFormatDate(calendar,"MMMM yyyy","MM")
        val year = CalendarUtils.setFormatDate(calendar,"MMMM yyyy","yyyy")

        apiResponse.postValue(ApiResponse().responseLoading(R.id.rootViewSalesman,flagLoadView = R.id.lLoadingView,isHideView = false))

        compositeDisposable.add(
            apiService.dashboard("Salesman",month,year,"")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val responseJson = JSONObject(it)

                    Log.e("salesmanResponse",it)

                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        val dashboardData = responseJson.obj(Constants.REMOTE.OBJ_DATA).getObject<Dashboard>()
                        dashboardSalesmans.value = dashboardData

                        apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.rootViewSalesman,flagLoadView = R.id.lLoadingView,isHideView = false))

                    } else {
                        apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.rootViewSalesman,flagLoadView = R.id.lLoadingView,isHideView = true))
                    }
                }, {
                    Log.e("thorwable1",it.message!!)
                    Log.e("thorwable2",it.localizedMessage!!)
                    apiResponse.postValue(ApiResponse().responseError(it,R.id.rootViewSalesman,flagLoadView = R.id.lLoadingView,isHideView = true))
                })
        )
    }


    val dashboardDealers = MutableLiveData<DashboardDealer>()
    fun hitDashboardDealer(calendar : String,msDealerId : String) {

        Log.e("msDealeId",msDealerId)

        apiResponse.postValue(ApiResponse().responseLoading(R.id.rootViewDealer,flagLoadView = R.id.lLoadingView,isHideView = false))

        val month = CalendarUtils.setFormatDate(calendar,"MMMM yyyy","MM")
        val year = CalendarUtils.setFormatDate(calendar,"MMMM yyyy","yyyy")

        compositeDisposable.add(
            apiService.dashboard(Constants.DASHBOARD.DEALER,month,year,msDealerId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val responseJson = JSONObject(it)

                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        val dashboardData = responseJson.obj(Constants.REMOTE.OBJ_DATA).getObject<DashboardDealer>()
                        dashboardDealers.value = dashboardData

                        apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.rootViewDealer,flagLoadView = R.id.lLoadingView,isHideView = false))

                    } else {
                        apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.rootViewDealer,flagLoadView = R.id.lLoadingView,isHideView = true))
                    }
                }, {
                    apiResponse.postValue(ApiResponse().responseError(it,R.id.rootViewDealer,flagLoadView = R.id.lLoadingView,isHideView = true))
                })
        )
    }


    val catalogue = MutableLiveData<Catalogue>()
    fun getCatalogue() {

        apiResponse.postValue(ApiResponse().responseLoading(R.id.rootParent,R.id.swipeRefresh,isHideView = true))

        compositeDisposable.add(
            apiService.getCatalogue()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val responseJson = JSONObject(it)

                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        val catalogueData = responseJson.obj(Constants.REMOTE.OBJ_DATA).getObject<Catalogue>()
                        catalogue.postValue(catalogueData)

                        apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.rootParent,R.id.swipeRefresh,isHideView = true))

                    } else {
                        apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.rootParent,R.id.swipeRefresh,isHideView = true))
                    }
                }, {
                    apiResponse.postValue(ApiResponse().responseError(it,R.id.rootParent,R.id.swipeRefresh,isHideView = true))
                })
        )
    }

}