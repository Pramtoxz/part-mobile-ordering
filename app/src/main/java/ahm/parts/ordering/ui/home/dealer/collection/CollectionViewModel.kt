package ahm.parts.ordering.ui.home.dealer.collection

import ahm.parts.ordering.R
import ahm.parts.ordering.api.ApiResponse
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dealer.collection.Collection1
import ahm.parts.ordering.data.model.home.dealer.collection.CollectionParam
import ahm.parts.ordering.data.model.home.dealer.collection.CollectionTotal
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseViewModel
import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import javax.inject.Inject


class CollectionViewModel @Inject constructor(private val context : Application) : BaseViewModel(){
    val collection = MutableLiveData<List<Collection1>>()
    val collection1 = MutableLiveData<CollectionTotal>()


    /*
    * Show Data Collection
    */
    fun hitCollectionList(param : CollectionParam) {

        var kddealer = param.dealercollection
        var beginEffdate = param.startime
        var endEffdate = param.endtime

        apiResponse.postValue(ApiResponse().responseLoading(R.id.rvCollectionList,R.id.swipeRefresh,isHideView = true))
        compositeDisposable.add(
            apiService.getCollection(kddealer,beginEffdate,endEffdate)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.getInt(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        val data = responseJson.obj(Constants.REMOTE.OBJ_DATA).array(Constants.REMOTE.ARR_DATA).toList<Collection1>()
                        collection.value = data
                        Log.d("Data Collection 1 : ",collection1.toString())
                        apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.rvCollectionList,R.id.lLoadingView,isHideView = true))

                    } else {
                        apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.rvCollectionList,R.id.lLoadingView,isHideView = true))
                    }
                }, {
                    apiResponse.postValue(ApiResponse().responseError(it,R.id.rvCollectionList,flagLoadView = R.id.pbLoadingBottom,isHideView = true))
                })
        )
    }

    /*
    * untuk menghitung Total Keseluruhan
    */
    fun Collectionttl(param: CollectionParam) {
        var kddealer = param.dealercollection
        var beginEffdate = param.startime
        var endEffdate = param.endtime

        apiResponse.postValue(ApiResponse().responseLoading(R.id.rvCollectionList,R.id.swipeRefresh,isHideView = true))
        compositeDisposable.add(
            apiService.getCollection(kddealer,beginEffdate,endEffdate)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.getInt(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {
//                        val dataobj = responseJson.obj(Constants.REMOTE.OBJ_DATA).getObject<CollectionTotal>()
                        val data = responseJson.obj(Constants.REMOTE.OBJ_DATA).getObject<CollectionTotal>()
                        collection1.postValue(data)
                        Log.d("Data Collection : ",collection1.toString())
                        apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.rvCollectionList,R.id.swipeRefresh,isHideView = true))

                    } else {
                        apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.rvCollectionList,R.id.swipeRefresh,isHideView = true))
                    }
                }, {
//                    apiResponse.postValue(ApiResponse().responseError(it,R.id.rvCollectionList,R.id.swipeRefresh,isHideView = true))
                    apiResponse.postValue(ApiResponse().responseError(it,R.id.rvCollectionList,flagLoadView = R.id.pbLoadingBottom,isHideView = true))
                })
        )
    }
}