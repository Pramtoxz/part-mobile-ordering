package ahm.parts.ordering.ui.home.dealer.competitor

import ahm.parts.ordering.R
import ahm.parts.ordering.api.ApiResponse
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dealer.competitor.Competitor
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseViewModel
import android.app.Application
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.File
import javax.inject.Inject


class CompetitorViewModel @Inject constructor(private val context : Application) : BaseViewModel(){

    val competitors = MutableLiveData<List<Competitor>>()

    fun hitCompetitor() {

        apiResponse.postValue(ApiResponse().responseLoading(R.id.rvCompetitor,R.id.swipeRefresh,isHideView = true))

        compositeDisposable.add(
            apiService.getDealerCompetitor()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        val data = responseJson.obj(Constants.REMOTE.OBJ_DATA).array(Constants.REMOTE.ARR_DATA).toList<Competitor>()
                        competitors.value = data

                        apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.rvCompetitor,R.id.swipeRefresh,isHideView = true))

                    } else {
                        apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.rvCompetitor,R.id.swipeRefresh,isHideView = true))
                    }
                }, {
                    apiResponse.postValue(ApiResponse().responseError(it,R.id.rvCompetitor,R.id.swipeRefresh,isHideView = true))
                })
        )
    }



    fun hitCompetitorAdd(codeDealer : String,idRole : String,nameCompetitor : String,product : String,titleActivityCompetitor : String,beginEffdate : String,endEffdate : String,filePhoto : File,description : String) {

        apiResponse.postValue(ApiResponse().responseLoading(R.id.btnAddCompetitor,flagLoadDialog = true))

        val photoBody = RequestBody.create(MediaType.parse("image/*"), filePhoto)
        val photo = MultipartBody.Part.createFormData(
            "photo",
            filePhoto.name, photoBody
        )

        compositeDisposable.add(
            apiService.addDealerCompetitor(codeDealer,idRole,nameCompetitor,product,titleActivityCompetitor,beginEffdate,endEffdate,photo,description)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.btnAddCompetitor,flagLoadDialog = true))

                    } else {
                        apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.btnAddCompetitor,flagLoadDialog = true))
                    }
                }, {
                    apiResponse.postValue(ApiResponse().responseError(it,R.id.btnAddCompetitor,flagLoadDialog = true))
                })
        )
    }

    fun hitCompetitorAddMultiple(codeDealer : String,idRole : String,nameCompetitor : String,product : String,titleActivityCompetitor : String,beginEffdate : String,endEffdate : String,photoArray : String,description : String) {

        apiResponse.postValue(ApiResponse().responseLoading(R.id.btnAddCompetitor,flagLoadDialog = true))

        compositeDisposable.add(
            apiService.addDealerCompetitorMultiple(codeDealer,idRole,nameCompetitor,product,titleActivityCompetitor,beginEffdate,endEffdate,photoArray,description)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.btnAddCompetitor,flagLoadDialog = true))

                    } else {
                        apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.btnAddCompetitor,flagLoadDialog = true))
                    }
                }, {
                    apiResponse.postValue(ApiResponse().responseError(it,R.id.btnAddCompetitor,flagLoadDialog = true))
                })
        )
    }

}