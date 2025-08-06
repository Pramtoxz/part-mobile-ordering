package ahm.parts.ordering.ui.home.home.partnumber.kelompokbarang

import ahm.parts.ordering.api.ApiResponse
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dashboard.partnumber.KelompokBarang
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseViewModel
import android.app.Application
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import javax.inject.Inject


class KelompokBarangSearchViewModel @Inject constructor(private val context : Application) : BaseViewModel(){

    val kelompokBarangSearchs = MutableLiveData<KelompokBarang>()

    fun hitKelompokBarangSearch(strSearch : String) {

        apiResponse.postValue(ApiResponse().responseLoading())

        compositeDisposable.add(
            apiService.getPartKelompokBarang(strSearch)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {

                        val kelompokBarangData = responseJson.obj(Constants.REMOTE.OBJ_DATA).obj(Constants.REMOTE.OBJ_DATA).getObject<KelompokBarang>()
                        kelompokBarangSearchs.value = kelompokBarangData

                        apiResponse.postValue(ApiResponse().responseSuccess(apiMessage))

                    } else {
                        apiResponse.postValue(ApiResponse().responseWrong(apiMessage))
                    }
                }, {
                    apiResponse.postValue(ApiResponse().responseError(it))
                })
        )
    }

}