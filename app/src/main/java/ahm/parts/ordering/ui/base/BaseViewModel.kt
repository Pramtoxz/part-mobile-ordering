package ahm.parts.ordering.ui.base

import ahm.parts.ordering.api.ApiResponse
import ahm.parts.ordering.api.ApiService
import ahm.parts.ordering.data.Session
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.MessageState
import ahm.parts.ordering.data.room.user.User
import ahm.parts.ordering.data.room.user.UserRepository
import ahm.parts.ordering.helper.*
import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.esafirm.rxdownloader.RxDownloader
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import java.io.File
import java.net.URI
import javax.inject.Inject

open class BaseViewModel : ViewModel(), LifecycleObserver {
    @Inject
    lateinit var context1: Application
    @Inject
    lateinit var userRepository: UserRepository
    @Inject
    lateinit var session: Session
    @Inject
    lateinit var gson: Gson
    @Inject
    open lateinit var apiService: ApiService

    val compositeDisposable: CompositeDisposable = CompositeDisposable()
    val apiResponse = MutableLiveData<ApiResponse>()
    val messageState = MutableLiveData<MessageState>()
    val logoutState = MutableLiveData<Boolean>()
    val renewTokenResponse = MutableLiveData<ApiResponse>()
    val reloadAfterRenewToken = MutableLiveData<Boolean>()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    /**
     * mendapatkan data user pada local database
     */
    fun getUser(): LiveData<User?> {
        return userRepository.user
    }

    /**
     * mengirim notifikasi ke server bahwa sesi app telah berakhir
     *
     */
    fun logout() {

        compositeDisposable.add(
            apiService.logout()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.getInt(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.getJSONArray(Constants.REMOTE.API_MESSAGE)
                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {
                        logoutState.postValue(true)
                        apiResponse.postValue(ApiResponse().responseSuccess(apiMessage))
                    } else {
                        logoutState.postValue(true)
                        apiResponse.postValue(ApiResponse().responseWrong(apiMessage))
                    }
                    session.clearAll()
                    userRepository.deleteAll()
                }, {
                    session.clearAll()
                    userRepository.deleteAll()
                    logoutState.postValue(true)
                    apiResponse.postValue(ApiResponse().responseError(it))
                })
        )
    }

    /**
     * mengirim notifikasi ke server bahwa sesi app telah berakhir
     * logout yang di trigger dari renew token
     *
     */
    fun logoutRenewToken() {
        compositeDisposable.add(
            apiService.logout()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.getInt(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.getJSONArray(Constants.REMOTE.API_MESSAGE)
                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {
                        session.clearAll()
                        userRepository.deleteAll()
                        logoutState.value = true
                    } else {
                        renewTokenResponse.value = ApiResponse().responseWrong(apiMessage)
                    }
                }, {
                    renewTokenResponse.value = ApiResponse().responseError(it)
                })
        )
    }


    /**
     * meminta token baru dikarenakan token expired
     *
     * @param password password user yang diinput manual
     */
    fun renewToken(password: String) {

        session.saveSid(null)

        compositeDisposable.add(
            apiService.renewToken(password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({

                    Log.e("renewToken",it)

                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.getInt(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.getJSONArray(Constants.REMOTE.API_MESSAGE)
                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {
                        val jsonObjectData = responseJson.getJSONObject(Constants.REMOTE.OBJ_DATA)
                        session.saveAccessToken(jsonObjectData.getString("authorization"))
                        session.saveSid(jsonObjectData.getString("session"))
                        renewTokenResponse.value = ApiResponse().responseSuccess(apiMessage)

                        reloadAfterRenewToken.postValue(true)

                    } else {
                        renewTokenResponse.value = ApiResponse().responseWrong(apiMessage)
                    }
                }, {
                    val resError = ApiResponse().responseError(it)
                    renewTokenResponse.value = resError
                })
        )
    }

    fun getOauthTokenBase() {

        session.saveAccessToken(null)

        compositeDisposable.add(
            apiService.oauthToken()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({

                    Log.e("responseJsonTokenRenew",it)

                    val responseJson = JSONObject(it)
                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {
                        val jsonObjectData = responseJson.getJSONObject(Constants.REMOTE.OBJ_DATA)
                        session.saveAccessToken(jsonObjectData.getString("token"))
                        loginBase(session.userId, AESEDecrypt(session.passwordUser))
                    } else {
                        renewTokenResponse.value = ApiResponse().responseWrong(apiMessage)
                    }
                }, {
                    renewTokenResponse.value = ApiResponse().responseError(it)
                })
        )
    }

    private fun loginBase(userName: String, pass: String?) {
        compositeDisposable.add(
            apiService.login(userName, pass, session.fcmId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val responseJson = JSONObject(it)

                    Log.e("responseJsonLogin",it)

                    val apiStatus = responseJson.int(Constants.REMOTE.API_STATUS)
                    val apiMessage = responseJson.array(Constants.REMOTE.API_MESSAGE)

                    if (apiStatus == Constants.REMOTE.API_STATUS_SUCCESS) {
                        val jsonObjectData = responseJson.obj(Constants.REMOTE.OBJ_DATA)
                        val user = gson.fromJson(jsonObjectData.toString(), User::class.java).copy(idUser = 1)
                        session.saveUserId(userName)
                        session.passwordUser = AESEncrypt(pass)
                        session.idRole = user.id_role
                        /*session.saveSid(jsonObjectData.getString("session_id"))*/
                        userRepository.insert(user)
                        renewTokenResponse.postValue(ApiResponse().responseSuccess(apiMessage))

                        reloadAfterRenewToken.postValue(true)

                    } else {
                        renewTokenResponse.postValue(ApiResponse().responseWrong(apiMessage))
                    }
                }, {
                    renewTokenResponse.postValue(ApiResponse().responseError(it))
                })
        )
    }

    val fileDownloaded = MutableLiveData<File>()
    @SuppressLint("CheckResult")
    fun downloadFile(path: String) {
        val appStorageHelper = AppStorageHelper(context1)
        if (!appStorageHelper.isDirectoryExists(Constants.DIRECTORY.FILES, false)) {
            appStorageHelper.createSubDirectory(Constants.DIRECTORY.FILES, false)
        }
        val directory = appStorageHelper.getSubDirectory(Constants.DIRECTORY.FILES, false)
        val fileName = appStorageHelper.getFileName(path)

        if (appStorageHelper.isFileExists(fileName, directory)) {
            fileDownloaded.value = appStorageHelper.getFileExist(fileName, directory)
            return
        }

        RxDownloader(context1)
            .download(path, appStorageHelper.getFileName(path), false)
            .subscribe({ pathResult ->
                val fileSource = File(URI(pathResult))
                val fileDest =
                    File(directory.path + File.separator + appStorageHelper.getFileName(path))
                appStorageHelper.copyFile(fileSource, fileDest)
                fileSource.delete()
                fileDownloaded.value = fileDest

            }, { throwable ->
                fileDownloaded.value = null
                val resError = ApiResponse().responseError(throwable)
                apiResponse.value = resError
            })

    }

}