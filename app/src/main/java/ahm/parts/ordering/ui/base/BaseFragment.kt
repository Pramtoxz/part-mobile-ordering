package ahm.parts.ordering.ui.base

import ahm.parts.ordering.BuildConfig
import ahm.parts.ordering.R
import ahm.parts.ordering.api.ApiResponse
import ahm.parts.ordering.api.ApiStatus
import ahm.parts.ordering.data.Session
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.dialog.BasicRenewTokenDialog
import ahm.parts.ordering.ui.dialog.LoadingDialog
import ahm.parts.ordering.ui.auth.login.LoginActivity
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import org.json.JSONArray
import retrofit2.HttpException
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

abstract class BaseFragment<VM : BaseViewModel> : DaggerFragment(), ClickPrevention {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var session : Session

    lateinit var loadingDialog: LoadingDialog

    protected lateinit var viewModel: VM

    internal var progressBar: ContentLoadingProgressBar? = null
    internal var ivNotFound: ImageView? = null
    internal var tvReload: TextView? = null
    internal var tvSubReload: TextView? = null
    internal var buttonReload: Button? = null
    protected var flContentLoading: FrameLayout? = null

    lateinit var onAfterReloadToken: Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModelClass = (javaClass
            .genericSuperclass as ParameterizedType)
            .actualTypeArguments[0] as Class<VM> // 0 is BaseViewModel

        viewModel = ViewModelProvider(this, viewModelFactory).get(viewModelClass)

        context?.let { loadingDialog = LoadingDialog(it) }

        observeData()

    }

    fun initState(view: View, activity: Activity) {
        try {
            progressBar = view.findViewById(R.id.progressBar)
            ivNotFound = view.findViewById(R.id.ivNotFound)
            tvReload = view.findViewById(R.id.tvReload)
            tvSubReload = view.findViewById(R.id.tvSubReload)
            buttonReload = view.findViewById(R.id.buttonRetry)
            flContentLoading = view.findViewById(R.id.flContentLoading)

            hideAllLoadingStateView()
        } catch (e: Exception) {

        }
    }

    private fun observeData() {

        viewModel.logoutState.observe(this, Observer {
            loadingDialog.dismiss()
            goTo<LoginActivity> {  }
            activity!!.finishAffinity()
        })

        viewModel.apiResponse.observe(this, Observer {
            if (it.isTokenExpired) {
                ViewHelper.hideKeyboard(activity!!)
                showDialogTokenExpired()
            }
        })
        viewModel.renewTokenResponse.observe(this, Observer {
            ToastUtil(activity!!).showJsonToasts(it)
            if (it.status == ApiStatus.SUCCESS) loadingDialog.dismiss()
            else {
                showDialogTokenExpired()
            }
        })
//        viewModel.reloadAfterRenewToken.observe(activity!!, Observer {
//            onAfterReloadToken
//        })

    }

    /**
     * Menampilkan dialog token expired kepada user
     * Di Trigger ketika session pada respon API telah expired
     */
    private fun showDialogTokenExpired() {
        BasicRenewTokenDialog(activity!!, R.style.Dialog)
            .setTitleVisibility(false)
            .setMessage(getString(R.string.token_expired_lbl_title))
            .setOnlyText()
            .setPasswordText(AESEDecrypt(session.passwordUser))
            .setMessageGravity(Gravity.CENTER)
            .setPositiveButton(R.string.token_expired_lbl_dialog_renew)
            .setNegativeButton(R.string.token_expired_lbl_dialog_logout)
            .setButtonPositiveClickListener(object :
                BasicRenewTokenDialog.ButtonPositiveClickListener {
                override fun clicked(value: String, dialog: BasicRenewTokenDialog) {
                    if (value.isEmpty()) {
                        ToastUtil(activity!!).showAToast(getString(R.string.token_expired_lbl_dialog_empty))
                        return
                    }
                    dialog.dismiss()
                    loadingDialog.show(R.string.dialog_lbl_loading)
                    //viewModel.renewToken(value)
                    viewModel.getOauthTokenBase()
                }
            })
            .setButtonNegativeClickListener(object :
                BasicRenewTokenDialog.ButtonNegativeClickListener {
                override fun clicked(dialog: BasicRenewTokenDialog) {
                    dialog.dismiss()
                    loadingDialog.show(R.string.dialog_lbl_loading)
                    viewModel.logout()
                }

            })
            .show()
    }

    /**
     * Menampilkan dialog token expired kepada user
     * Di Trigger ketika session pada respon API telah expired
     */
    private fun showDialogFailure(message : String,onReload: () -> Unit) {
        BasicRenewTokenDialog(activity!!, R.style.Dialog)
            .setTitleVisibility(false)
            .setMessage(message)
            .setOnlyText()
            .setMessageGravity(Gravity.CENTER)
            .setSingleButton()
            .setPositiveButton(R.string.btn_oke)
            .setButtonPositiveClickListener(object :
                BasicRenewTokenDialog.ButtonPositiveClickListener {
                override fun clicked(value: String, dialog: BasicRenewTokenDialog) {
                    dialog.dismiss()
                    onReload()
                }
            })
            .setButtonNegativeClickListener(object :
                BasicRenewTokenDialog.ButtonNegativeClickListener {
                override fun clicked(dialog: BasicRenewTokenDialog) {
                    dialog.dismiss()
                    loadingDialog.show(R.string.dialog_lbl_loading)
                    viewModel.logout()
                }

            })
            .show()
    }


    fun showLoadingStateView() {
        flContentLoading?.visibility = View.VISIBLE
        flContentLoading?.isClickable = true
        flContentLoading?.isEnabled = true

        progressBar?.visibility = View.VISIBLE
        progressBar?.show()
        tvReload?.visibility = View.GONE
        tvSubReload?.visibility = View.GONE
        ivNotFound?.visibility = View.GONE
        buttonReload?.visibility = View.GONE
    }


    protected fun hideAllLoadingStateView() {
        progressBar!!.visibility = View.GONE
        tvReload!!.visibility = View.GONE
        tvSubReload!!.visibility = View.GONE
        ivNotFound!!.visibility = View.GONE
        buttonReload!!.visibility = View.GONE


        flContentLoading?.visibility = View.GONE
        flContentLoading?.isClickable = false
        flContentLoading?.isEnabled = false
    }

    protected fun showErrorStateView(title: String, message: String) {
        progressBar!!.visibility = View.GONE
        tvReload!!.visibility = View.VISIBLE
        tvSubReload!!.visibility = View.VISIBLE
        ivNotFound!!.visibility = View.VISIBLE
        buttonReload!!.visibility = View.GONE
        if (title.isEmpty())
            tvReload!!.text = "Terjadi Kesalahan"
        else
            tvReload!!.text = title

        tvSubReload!!.text = message
        ivNotFound!!.setImageResource(R.drawable.es_no_data)
    }

    protected fun showEmptyStateView(message: String) {
        progressBar!!.visibility = View.GONE
        tvReload!!.visibility = View.VISIBLE
        tvSubReload!!.visibility = View.VISIBLE
        ivNotFound!!.visibility = View.VISIBLE
        buttonReload!!.visibility = View.GONE
//        tvReload!!.text = resources.getString(R.string.error_state_title_no_data)
        tvSubReload!!.text = message
        ivNotFound!!.setImageResource(R.drawable.es_no_data)
    }


    fun snacked(view: View, message: Int, duration: Int = Snackbar.LENGTH_SHORT) {
        Snackbar.make(view, message, duration).show()
    }

    fun snacked(view: View, message: String?, duration: Int = Snackbar.LENGTH_SHORT) {
        message?.let { Snackbar.make(view, it, duration).show() }
    }

    fun snacked(view: View, message: ApiResponse) {
        if (message.status == ApiStatus.ERROR) {
            if (!message.message.isNull(0)) {
                snacked(view, message.message)
            } else {
                snacked(view, message.throwableBody)
            }
        } else if (message.status == ApiStatus.WRONG) snacked(view, message.message)
    }

    fun snacked(view: View, message: JSONArray) {
        for (i in 0 until message.length()) {
            snacked(view, message[i].toString())
        }
    }

    fun isEmptyRequired(vararg editText: EditText?): Boolean {
        for (e in editText) {
            if (textOf(e).isEmpty()) {
                e?.error = getString(R.string.error_fill_empty_field)
                e?.requestFocus()
                return true
            }
        }
        return false
    }

    fun isEmptyRequiredSnackbar(vararg editText: EditText): Boolean {

        for (e in editText) {
            if (textOf(e).isEmpty()) {
                snackedBar(e, getString(R.string.error_empty, e.hint.toString()))
                e.requestFocus()
                return true
            }
        }

        return false
    }

    fun snackedBar(view: View, message: String?, duration: Int = Snackbar.LENGTH_SHORT) {
        message?.let { Snackbar.make(view, it, duration).show() }
    }


    fun textOf(e: EditText?): String {
        return e?.text.toString().trim()
    }

    protected fun isFill(vararg editText: EditText): Boolean {
        for (e in editText) {
            if (e.itText().isEmpty()) {
                e.error = getString(R.string.error_fill_empty_field)
                return false
            }
        }

        return true
    }

    /**
     * Fungsi untuk mengeset text dengan StringMasker() didalamnya
     *
     */
    infix fun TextView.text(value: String?) {
        text = StringMasker().validateEmpty(value)
    }

    /**
     * Fungsi untuk menghandle response dari class ApiResponse
     *
     */
    fun Fragment.showStateApiView(apiResponse: ApiResponse, delay: Int = 0, onReload: () -> Unit) {

        try {
            var viewFlag: View? = null
            var viewFlagLoadingRefresh: SwipeRefreshLayout? = null
            var viewFlagLoadingView: View? = null

            if (apiResponse.flagView != null) {
                val idViewFlag = resources.getIdentifier(
                    apiResponse.flagView.toString(),
                    "id",
                    BuildConfig.APPLICATION_ID
                )

                viewFlag = activity!!.findViewById(idViewFlag)

                if (apiResponse.isHide!!) {
                    viewFlag!!.hide()
                }
            }

            if (apiResponse.flagLoadingRefresh != null) {
                val idFlagLoadRefresh = resources.getIdentifier(
                    apiResponse.flagLoadingRefresh.toString(),
                    "id",
                    BuildConfig.APPLICATION_ID
                )

                viewFlagLoadingRefresh = activity!!.findViewById(idFlagLoadRefresh)
            }

            if (apiResponse.flagLoadingView != null) {
                val idFlagLoadView = resources.getIdentifier(
                    apiResponse.flagLoadingView.toString(),
                    "id",
                    BuildConfig.APPLICATION_ID
                )

                viewFlagLoadingView = activity!!.findViewById(idFlagLoadView)
            }


            val rootViewErrorState = activity!!.findViewById<ViewGroup>(R.id.rootViewErrorState)
            val btnRefreshError = activity!!.findViewById<Button>(R.id.btnRefreshError)
            val tvErrorState = activity!!.findViewById<TextView>(R.id.tvErrorState)

            rootViewErrorState.lyTrans()

            if (apiResponse.status == ApiStatus.LOADING) {
                /**
                 * handle ketika loading
                 */
                if (apiResponse.flagView != null) {

                    if (apiResponse.isHide!!) {
                        viewFlag!!.hide()
                    }else{
                        viewFlag!!.show()
                    }
                }

                if (apiResponse.flagLoadingRefresh != null) {
                    viewFlagLoadingRefresh!!.isRefreshing = true
                }

                if (apiResponse.flagLoadingView != null) {
                    viewFlagLoadingView!!.show()
                }

                rootViewErrorState.hide()
            } else if (apiResponse.status == ApiStatus.ERROR) {
                /**
                 * handle throwable antara koneksi dan server error
                 */

                if (!apiResponse.flagLoadingDialog) {
                    rootViewErrorState.show()
                }

                btnRefreshError.show()

                if (apiResponse.flagView != null) {
                    if (apiResponse.isHide) {
                        viewFlag!!.hide()
                    }

                }

                if (apiResponse.flagLoadingRefresh != null) {
                    viewFlagLoadingRefresh!!.isRefreshing = false
                }

                if (apiResponse.flagLoadingView != null) {
                    viewFlagLoadingView!!.hide()
                }

                var strErrorState = ""

                if(isErrorServer(apiResponse.throwableHttp!!) == 401){
                    tvErrorState text resources.getString(R.string.state_token_exp)
                }else{
                    if (apiResponse.throwableHttp is HttpException) {
                        if ((apiResponse.throwableHttp as HttpException).isErrorServer()) {
                            tvErrorState text resources.getString(R.string.lbl_error_server)
                            strErrorState = resources.getString(R.string.error_server)
                        } else {
                            tvErrorState text resources.getString(R.string.lbl_error_network_state)
                            strErrorState = resources.getString(R.string.error_network)
                        }


                    } else {
                        if (apiResponse.throwableBody!!.startsWith("java.lang")) {
                            tvErrorState text resources.getString(R.string.lbl_error_server)
                            strErrorState = resources.getString(R.string.error_server)
                        } else {
                            tvErrorState text resources.getString(R.string.lbl_error_network_state)
                            strErrorState = resources.getString(R.string.error_network)
                        }
                    }
                }


                if (apiResponse.flagLoadingDialog) {
                    activity!!.showToast(strErrorState)
                }

            } else if (apiResponse.status == ApiStatus.WRONG) {
                /**
                 * handle ketika api status !1
                 */
                if (apiResponse.flagView != null) {
                    if (apiResponse.isHide!!) {
                        viewFlag!!.hide()
                    }
                }

                if (apiResponse.flagLoadingRefresh != null) {
                    viewFlagLoadingRefresh!!.isRefreshing = false
                }

                if (apiResponse.flagLoadingView != null) {
                    viewFlagLoadingView!!.hide()
                }

                if (!apiResponse.flagLoadingDialog) {
                    rootViewErrorState.show()
                } else {
                    activity!!.showToast(apiResponse.message[0].toString())
                }

                btnRefreshError.show()

                tvErrorState text apiResponse.message[0].toString()

            } else if (apiResponse.status == ApiStatus.EMPTY) {
                /**
                 * handle ketika empty data
                 */
                if (apiResponse.flagView != null) {
                    if (apiResponse.isHide!!) {
                        viewFlag!!.hide()
                    }
                }

                if (apiResponse.flagLoadingRefresh != null) {
                    viewFlagLoadingRefresh!!.isRefreshing = false
                }

                if (apiResponse.flagLoadingView != null) {
                    viewFlagLoadingView!!.hide()
                }

                rootViewErrorState.show()
                btnRefreshError.hide()

                tvErrorState text getString(R.string.lbl_empty_state)
            } else if (apiResponse.status == ApiStatus.SUCCESS) {
                /**
                 * handle ketika success
                 */
                if (apiResponse.flagView != null) {
                    viewFlag!!.show()
                }

                if (apiResponse.flagLoadingRefresh != null) {
                    viewFlagLoadingRefresh!!.isRefreshing = false
                }


                if (apiResponse.flagLoadingView != null) {
                    if (delay != 0) {
                        delay(delay.toLong()) {
                            try {
                                viewFlagLoadingView!!.hide()
                            } catch (e: Exception) {
                            }
                        }
                    } else {
                        viewFlagLoadingView!!.hide()
                    }
                }

                rootViewErrorState.hide()
            }


            btnRefreshError.onClick {
                Log.e("btnRefreshError", "jalan")
                rootViewErrorState.hide()
                onReload()
            }
        } catch (e: Exception) {
        }
    }

    fun Fragment.showStateApiViewDealerDashboard(apiResponse: ApiResponse, delay: Int = 0, onReload: () -> Unit) {

        try {
            var viewFlag: View? = null
            var viewFlagLoadingRefresh: SwipeRefreshLayout? = null
            var viewFlagLoadingView: View? = null

            if (apiResponse.flagView != null) {
                val idViewFlag = resources.getIdentifier(
                    apiResponse.flagView.toString(),
                    "id",
                    BuildConfig.APPLICATION_ID
                )

                viewFlag = activity!!.findViewById(idViewFlag)

                if (apiResponse.isHide!!) {
                    viewFlag!!.hide()
                }
            }

            if (apiResponse.flagLoadingRefresh != null) {
                val idFlagLoadRefresh = resources.getIdentifier(
                    apiResponse.flagLoadingRefresh.toString(),
                    "id",
                    BuildConfig.APPLICATION_ID
                )

                viewFlagLoadingRefresh = activity!!.findViewById(idFlagLoadRefresh)
            }

            if (apiResponse.flagLoadingView != null) {
                val idFlagLoadView = resources.getIdentifier(
                    apiResponse.flagLoadingView.toString(),
                    "id",
                    BuildConfig.APPLICATION_ID
                )

                viewFlagLoadingView = activity!!.findViewById(idFlagLoadView)
            }


            val rootViewErrorState = activity!!.findViewById<ViewGroup>(R.id.rootViewStateDealerDashboard)
            val btnRefreshError = activity!!.findViewById<Button>(R.id.btnRefreshError)
            val tvHeaderErrorState = activity!!.findViewById<TextView>(R.id.tvHeaderErrorState)
            val tvErrorState = activity!!.findViewById<TextView>(R.id.tvErrorState)

            rootViewErrorState.lyTrans()

            if (apiResponse.status == ApiStatus.LOADING) {
                /**
                 * handle ketika loading
                 */
                if (apiResponse.flagView != null) {

                    if (apiResponse.isHide!!) {
                        viewFlag!!.hide()
                    }else{
                        viewFlag!!.show()
                    }
                }

                if (apiResponse.flagLoadingRefresh != null) {
                    viewFlagLoadingRefresh!!.isRefreshing = true
                }

                if (apiResponse.flagLoadingView != null) {
                    viewFlagLoadingView!!.show()
                }

                rootViewErrorState.hide()
                tvHeaderErrorState.hide()

            } else if (apiResponse.status == ApiStatus.ERROR) {
                /**
                 * handle throwable antara koneksi dan server error
                 */

                if (!apiResponse.flagLoadingDialog) {
                    rootViewErrorState.show()
                }

                btnRefreshError.show()

                if (apiResponse.flagView != null) {
                    if (apiResponse.isHide) {
                        viewFlag!!.hide()
                    }

                }

                if (apiResponse.flagLoadingRefresh != null) {
                    viewFlagLoadingRefresh!!.isRefreshing = false
                }

                if (apiResponse.flagLoadingView != null) {
                    viewFlagLoadingView!!.hide()
                }

                var strErrorState = ""

                if(isErrorServer(apiResponse.throwableHttp!!) == 401){
                    tvErrorState text resources.getString(R.string.state_token_exp)
                }else{
                    if (apiResponse.throwableHttp is HttpException) {
                        if ((apiResponse.throwableHttp as HttpException).isErrorServer()) {
                            tvErrorState text resources.getString(R.string.lbl_error_server)
                            strErrorState = resources.getString(R.string.error_server)
                        } else {
                            tvErrorState text resources.getString(R.string.lbl_error_network_state)
                            strErrorState = resources.getString(R.string.error_network)
                        }


                    } else {
                        if (apiResponse.throwableBody!!.startsWith("java.lang")) {
                            tvErrorState text resources.getString(R.string.lbl_error_server)
                            strErrorState = resources.getString(R.string.error_server)
                        } else {
                            tvErrorState text resources.getString(R.string.lbl_error_network_state)
                            strErrorState = resources.getString(R.string.error_network)
                        }
                    }
                }


                if (apiResponse.flagLoadingDialog) {
                    activity!!.showToast(strErrorState)
                }

                tvHeaderErrorState.hide()
            } else if (apiResponse.status == ApiStatus.WRONG) {
                /**
                 * handle ketika api status !1
                 */
                if (apiResponse.flagView != null) {
                    if (apiResponse.isHide!!) {
                        viewFlag!!.hide()
                    }
                }

                if (apiResponse.flagLoadingRefresh != null) {
                    viewFlagLoadingRefresh!!.isRefreshing = false
                }

                if (apiResponse.flagLoadingView != null) {
                    viewFlagLoadingView!!.hide()
                }

                if (!apiResponse.flagLoadingDialog) {
                    rootViewErrorState.show()
                } else {
                    activity!!.showToast(apiResponse.message[0].toString())
                }

                btnRefreshError.show()

                tvErrorState text apiResponse.message[0].toString()

                tvHeaderErrorState.hide()
            } else if (apiResponse.status == ApiStatus.EMPTY) {
                /**
                 * handle ketika empty data
                 */
                if (apiResponse.flagView != null) {
                    if (apiResponse.isHide!!) {
                        viewFlag!!.hide()
                    }
                }

                if (apiResponse.flagLoadingRefresh != null) {
                    viewFlagLoadingRefresh!!.isRefreshing = false
                }

                if (apiResponse.flagLoadingView != null) {
                    viewFlagLoadingView!!.hide()
                }

                rootViewErrorState.show()
                btnRefreshError.hide()

                tvErrorState text getString(R.string.lbl_empty_state)


                tvHeaderErrorState.hide()
            } else if (apiResponse.status == ApiStatus.SUCCESS) {
                /**
                 * handle ketika success
                 */
                if (apiResponse.flagView != null) {
                    viewFlag!!.show()
                }

                if (apiResponse.flagLoadingRefresh != null) {
                    viewFlagLoadingRefresh!!.isRefreshing = false
                }


                if (apiResponse.flagLoadingView != null) {
                    if (delay != 0) {
                        delay(delay.toLong()) {
                            try {
                                viewFlagLoadingView!!.hide()
                            } catch (e: Exception) {
                            }
                        }
                    } else {
                        viewFlagLoadingView!!.hide()
                    }
                }

                rootViewErrorState.hide()
                tvHeaderErrorState.hide()
            }


            btnRefreshError.onClick {
                rootViewErrorState.hide()
                onReload()
            }
        } catch (e: Exception) {
        }
    }


    override fun onClick(v: View?) {
        when (v?.id) {
//            R.id.ivUser, R.id.btnProfile -> startActivity(
//                Intent(
//                    context,
//                    ProfileActivity::class.java
//                )
//            )
        }
        super.onClick(v)
    }
}