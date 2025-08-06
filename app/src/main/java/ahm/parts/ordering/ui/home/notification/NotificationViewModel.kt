package ahm.parts.ordering.ui.home.notification

import ahm.parts.ordering.R
import ahm.parts.ordering.api.ApiResponse
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.notification.Notification
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseViewModel
import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import javax.inject.Inject

class NotificationViewModel @Inject constructor(private val context : Application) : BaseViewModel(){

    val notifications = MutableLiveData<List<Notification>>()
    val notificationsLoadMore = MutableLiveData<List<Notification>>()

    /**
     * Fungsi untuk menembak api notifikasi
     */
    fun hitNotification(page : Int,isLoadMore : Boolean) {

        if(!isLoadMore){
            apiResponse.postValue(ApiResponse().responseLoading(R.id.rvNotification,R.id.swipeRefresh,isHideView = true))
        }else{
            apiResponse.postValue(ApiResponse().responseLoading(R.id.rvNotification,flagLoadView = R.id.pbLoadingBottom))
        }

        compositeDisposable.add(
            apiService.notification(page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        val data = responseJson.array(Constants.REMOTE.ARR_DATA).toList<Notification>()

                        if(!isLoadMore){
                            notifications.value = data

                            apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.rvNotification,R.id.swipeRefresh,isHideView = true))

                            if(data.isEmpty()){
                                apiResponse.postValue(ApiResponse().responseEmpty(apiMessage,R.id.rvNotification,R.id.swipeRefresh,isHideView = true))
                            }

                        }else{
                            notificationsLoadMore.value = data

                            apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.rvNotification,flagLoadView = R.id.pbLoadingBottom))
                        }


                    } else {
                        if(!isLoadMore){
                            apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.rvNotification,R.id.swipeRefresh,isHideView = true))
                        }else{
                            apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.rvNotification,flagLoadView = R.id.pbLoadingBottom,isHideView = true))
                        }
                    }
                }, {
                    if(!isLoadMore){
                        apiResponse.postValue(ApiResponse().responseError(it,R.id.rvNotification,R.id.swipeRefresh,isHideView = true))
                    }else{
                        apiResponse.postValue(ApiResponse().responseError(it,R.id.rvNotification,flagLoadView = R.id.pbLoadingBottom,isHideView = true))
                    }
                })
        )
    }
}