package ahm.parts.ordering.ui.home.dealer.salesmanvisit.addnewdealer

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


class AddNewDealerViewModel @Inject constructor(private val context : Application) : BaseViewModel(){

    fun addNewDealer(nameDealer : String,latlong : String,address : String,filePhoto : File,phone : String,description : String) {

        val photoBody = RequestBody.create(MediaType.parse("image/*"), filePhoto)
        val photo = MultipartBody.Part.createFormData(
            "photo",
            filePhoto.name, photoBody
        )

        apiResponse.postValue(ApiResponse().responseLoading(R.id.btnAddDealer,flagLoadDialog = true))

        compositeDisposable.add(
            apiService.addNewDealer(nameDealer,latlong,address,photo,phone,description)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        apiResponse.postValue(ApiResponse().responseSuccess(apiMessage,R.id.btnAddDealer,flagLoadDialog = true))

                    } else {
                        apiResponse.postValue(ApiResponse().responseWrong(apiMessage,R.id.btnAddDealer,flagLoadDialog = true))
                    }
                }, {
                    apiResponse.postValue(ApiResponse().responseError(it,R.id.btnAddDealer,flagLoadDialog = true))
                })
        )
    }

}