package ahm.parts.ordering.ui.base

import ahm.parts.ordering.BuildConfig
import ahm.parts.ordering.R
import ahm.parts.ordering.api.ApiResponse
import ahm.parts.ordering.api.ApiStatus
import ahm.parts.ordering.data.Session
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.dialog.BasicDialog
import ahm.parts.ordering.ui.dialog.BasicRenewTokenDialog
import ahm.parts.ordering.ui.auth.login.LoginActivity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.widget.ContentLoadingProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import retrofit2.HttpException
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

abstract class BaseActivity<VM : BaseViewModel> : NoViewModelActivity() {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var session : Session

    lateinit var viewModel: VM
    lateinit var viewModel1: VM

    //Toolbar
    internal var toolbar: Toolbar? = null
    lateinit var actionBar: ActionBar
    lateinit var title: TextView
    lateinit var subTitle: TextView

    //Content State
    internal var progressBar: ContentLoadingProgressBar? = null
    internal var ivNotFound: ImageView? = null
    internal var tvReload: TextView? = null
    internal var tvSubReload: TextView? = null
    internal var buttonReload: Button? = null
    protected var flContentLoading: FrameLayout? = null

    lateinit var onAfterReloadToken: () -> Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModelClass = (javaClass
            .genericSuperclass as ParameterizedType)
            .actualTypeArguments[0] as Class<VM> // 0 is BaseViewModel

        viewModel = ViewModelProvider(this, viewModelFactory).get(viewModelClass)
        viewModel1 = ViewModelProvider(this, viewModelFactory).get(viewModelClass)
        observeData()



    }

    override fun setContentView(@LayoutRes layoutResID: Int) {
        super.setContentView(layoutResID)

        try {
            toolbar = findViewById(R.id.toolbar)
            setupToolbar()
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }

        initState()

    }


    private fun observeData() {
        viewModel.logoutState.observe(this, Observer {
            Log.e("logoutState","$it")
            loadingDialog.dismiss()
            gotoLoginActivity()
        })
        viewModel.apiResponse.observe(this, Observer {
            if (it.isTokenExpired) {
                ViewHelper.hideKeyboard(this)
                showDialogTokenExpired()
            }
        })
        viewModel.renewTokenResponse.observe(this, Observer {
            ToastUtil(this@BaseActivity).showJsonToasts(it)
            if (it.status == ApiStatus.SUCCESS) loadingDialog.dismiss()
            else {
                showDialogTokenExpired()
            }
        })
//
//        viewModel.reloadAfterRenewToken.observe(this, Observer {
//            onAfterReloadToken()
//        })

    }

    fun initState() {

        try {
            progressBar = findViewById(R.id.progressBar)
            ivNotFound = findViewById(R.id.ivNotFound)
            tvReload = findViewById(R.id.tvReload)
            tvSubReload = findViewById(R.id.tvSubReload)
            buttonReload = findViewById(R.id.buttonRetry)
            flContentLoading = findViewById(R.id.flContentLoading)

            hideAllLoadingStateView()
        } catch (e: Exception) {

        }
    }


    protected fun setupToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar)
            actionBar = supportActionBar!!

            actionBar.setDisplayShowTitleEnabled(false)
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeButtonEnabled(true)

            setSupportActionBar(toolbar)
            actionBar = supportActionBar!!

            val viewActionBar = layoutInflater.inflate(R.layout.toolbar_center, null)
            //Center the textview in the ActionBar
            val params = ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER
            )

            title = viewActionBar.findViewById(R.id.toolbar_title)
            title.setTextColor(ContextCompat.getColor(this, android.R.color.white))
            subTitle = viewActionBar.findViewById(R.id.toolbar_subtitle)
            subTitle.setTextColor(ContextCompat.getColor(this, android.R.color.white))

            actionBar = supportActionBar!!
            actionBar.elevation = 0F
            actionBar.setCustomView(viewActionBar, params)
            actionBar.setDisplayShowCustomEnabled(false)
            actionBar.setDisplayShowTitleEnabled(false)
        }
    }

    /**
     * [strTitle] menampilkan judul dari aktifitas (resource.getString(R.string.name_variable))
     * [isBack] menampilkan icon back
     */
    protected fun setToolbar(strTitle: String, isBack: Boolean) {
        if (isBack) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }
        actionBar.setDisplayHomeAsUpEnabled(isBack)
        actionBar.setDisplayShowCustomEnabled(true)

        title.text = strTitle
        title.setTextColor(ContextCompat.getColor(this, R.color.white))
    }

    /**
     * [strTitle] menampilkan judul dari aktifitas (resource.getString(R.string.name_variable))
     * [strSubTitle] menampilkan sub judul dari aktifitas (resource.getString(R.string.name_variable))
     * [isBack] menampilkan icon back
     */
    protected fun setToolbar(strTitle: String, strSubTitle: String, isBack: Boolean) {
        if (isBack) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_w)
        }
        actionBar.setDisplayHomeAsUpEnabled(isBack)
        actionBar.setDisplayShowCustomEnabled(true)

        title.text = strTitle
        title.setTextColor(ContextCompat.getColor(this, R.color.white))

        subTitle.show()
        subTitle.text = strSubTitle
        subTitle.setTextColor(ContextCompat.getColor(this, R.color.white))
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

    /**
     * handle view state api response all condition
     */
    fun Context.showStateApiView(apiResponse: ApiResponse, delay: Int = 0, onReload: () -> Unit) {

//        try {
        var viewFlag: View? = null
        var viewFlagLoadingRefresh: SwipeRefreshLayout? = null
        var viewFlagLoadingView: View? = null


        if (apiResponse.flagView != null) {
            val idViewFlag = resources.getIdentifier(
                apiResponse.flagView.toString(),
                "id",
                BuildConfig.APPLICATION_ID
            )

            viewFlag = findViewById(idViewFlag)

            if (apiResponse.isHide) {
                viewFlag!!.hide()
            }
        }

        if (apiResponse.flagLoadingRefresh != null) {
            val idFlagLoadRefresh = resources.getIdentifier(
                apiResponse.flagLoadingRefresh.toString(),
                "id",
                BuildConfig.APPLICATION_ID
            )

            viewFlagLoadingRefresh = findViewById(idFlagLoadRefresh)
        }

        if (apiResponse.flagLoadingView != null) {
            val idFlagLoadView = resources.getIdentifier(
                apiResponse.flagLoadingView.toString(),
                "id",
                BuildConfig.APPLICATION_ID
            )

            viewFlagLoadingView = findViewById(idFlagLoadView)
        }


        val rootViewErrorState = this@BaseActivity.findViewById<ViewGroup>(R.id.rootViewErrorState)
        val btnRefreshError = this@BaseActivity.findViewById<Button>(R.id.btnRefreshError)
        val tvErrorState = this@BaseActivity.findViewById<TextView>(R.id.tvErrorState)

        rootViewErrorState.lyTrans()

        when (apiResponse.status) {
            ApiStatus.LOADING -> {
                /**
                 * handle ketika loading
                 */
                if (apiResponse.flagView != null) {

                    if (apiResponse.isHide) {
                        viewFlag!!.hide()
                    }
                }

                if (apiResponse.flagLoadingRefresh != null) {
                    viewFlagLoadingRefresh!!.isRefreshing = true
                }

                if (apiResponse.flagLoadingView != null) {
                    viewFlagLoadingView!!.show()
                }

                rootViewErrorState.hide()
            }
            ApiStatus.SUCCESS -> {
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
            ApiStatus.ERROR -> {
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
                            tvErrorState text "Something Wrong \n With Server"
                            strErrorState = "Something Wrong \n With Server"
                        } else {
                            tvErrorState text resources.getString(R.string.lbl_error_network_state)

                            strErrorState = resources.getString(R.string.lbl_error_network_state)
                        }
                    } else {
                        if (apiResponse.throwableBody!!.startsWith("java.lang")) {
                            tvErrorState text "Something Wrong \n With Server"
                            strErrorState = "Something Wrong \n With Server"
                        } else {
                            tvErrorState text resources.getString(R.string.lbl_error_network_state)
                            strErrorState = resources.getString(R.string.lbl_error_network_state)
                        }
                    }
                }


                if (apiResponse.flagLoadingDialog) {
                    showToast(strErrorState)
                }

            }
            ApiStatus.WRONG -> {
                /**
                 * handle ketika api status !1
                 */
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

                if (!apiResponse.flagLoadingDialog) {
                    rootViewErrorState.show()
                } else {
                    showToast(apiResponse.message[0].toString())
                }

                btnRefreshError.show()

                tvErrorState text apiResponse.message[0].toString()

            }
            ApiStatus.EMPTY -> {
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
            }
        }
//            if(apiResponse.status == ApiStatus.LOADING){
//
//            }else if (apiResponse.status == ApiStatus.ERROR) {
//
//            } else if (apiResponse.status == ApiStatus.WRONG) {
//
//            } else if (apiResponse.status == ApiStatus.EMPTY) {
//
//            } else if(apiResponse.status == ApiStatus.SUCCESS){
//
//            }


        btnRefreshError.onClick {
            Log.e("btnRefreshError", "jalan")
            rootViewErrorState.hide()
            onReload()
        }
//        } catch (e: Exception) {
//        }
    }

    fun View.showErrorView(apiResponse: ApiResponse, onReload: () -> Unit) {

        this.hide()

        val rootViewErrorState = this@BaseActivity.findViewById<ViewGroup>(R.id.rootViewErrorState)
        val btnRefreshError = this@BaseActivity.findViewById<Button>(R.id.btnRefreshError)
        val tvErrorState = this@BaseActivity.findViewById<TextView>(R.id.tvErrorState)

        rootViewErrorState.lyTrans()

        if (apiResponse.status == ApiStatus.ERROR) {
            /**
             * handle throwable antara koneksi dan server error
             */
            rootViewErrorState.show()
            btnRefreshError.show()

            if(isErrorServer(apiResponse.throwableHttp!!) == 401){
                tvErrorState text resources.getString(R.string.state_token_exp)
            }else{
                if (apiResponse.throwableHttp is HttpException) {
                    if ((apiResponse.throwableHttp as HttpException).isErrorServer()) {
                        tvErrorState text "Something Wrong \n With Server"
                    } else {
                        tvErrorState text resources.getString(R.string.lbl_error_network_state)
                    }
                } else {
                    if (apiResponse.throwableBody!!.startsWith("java.lang")) {
                        tvErrorState text "Something Wrong \n With Server"
                    } else {
                        tvErrorState text resources.getString(R.string.lbl_error_network_state)
                    }
                }
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
                showToast(strErrorState)
            }

        } else if (apiResponse.status == ApiStatus.WRONG) {
            /**
             * handle ketika api status 0
             */
            rootViewErrorState.show()
            btnRefreshError.show()

            tvErrorState text apiResponse.message[0].toString()

        } else if (apiResponse.status == ApiStatus.EMPTY) {
            rootViewErrorState.show()
            btnRefreshError.hide()

            tvErrorState text getString(R.string.lbl_empty_state)
        } else {
            this.show()
            rootViewErrorState.hide()
        }


        btnRefreshError.onClick {
            rootViewErrorState.hide()
            onReload()
        }
    }


    /**
     * Menuju page login ketika ada trigger logout
     */
    private fun gotoLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finishAffinity()
    }

    /**
     * Menampilkan dialog logout kepada user
     */
    private fun showDialogLogout() {
        BasicDialog(this, R.style.Dialog)
            .setTitleVisibility(false)
            .setMessage(R.string.dialog_lbl_loading)
            .setOnlyText()
            .setMessageGravity(Gravity.CENTER)
            .setPositiveButton(R.string.btn_submit)
            .setNegativeButton(R.string.btn_cancel)
            .setButtonNegativeClickListener(object : BasicDialog.ButtonNegativeClickListener {
                override fun clicked(dialog: BasicDialog) {
                    dialog.dismiss()
                }

            })
            .setButtonPositiveClickListener(object : BasicDialog.ButtonPositiveClickListener {
                override fun clicked(dialog: BasicDialog) {
                    dialog.dismiss()
                    loadingDialog.show(R.string.dialog_lbl_loading)
                    viewModel.logoutRenewToken()
                }
            })
            .show()
    }

    /**
     * Menampilkan dialog token expired kepada user
     * Di Trigger ketika session pada respon API telah expired
     */
    private fun showDialogTokenExpired() {
        BasicRenewTokenDialog(this, R.style.Dialog)
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
                        ToastUtil(this@BaseActivity).showAToast(getString(R.string.token_expired_lbl_dialog_empty))
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
     * hide menu sesuai role
     *
     */
    fun roleFiturMenu() {

        try {
            viewModel.getUser().observe(this, Observer {
                try {
                    when(it!!.id_role){
                        "MD_H3_MGMT" -> {

                        }
                        "MD_H3_KORSM" -> {

                        }
                        "MD_H3_SM" -> {

                        }
                        "D_H3" -> {
                            /**
                             * menu Part Number Search
                             *
                             */
                            /*try {
                                val menuPartNumberSearch = findViewById<View>(R.id.vMenuPartNumberSearch)
                                menuPartNumberSearch.hide()
                            }catch (e : Exception){
                                e.printStackTrace()
                            }*/


                            /**
                             * menu Salesman Visit
                             *
                             */
                            try {
                                val btnSalesmanVisit = findViewById<View>(R.id.btnSalesmanVisit)
                                btnSalesmanVisit.hide()
                            }catch (e : Exception){
                                e.printStackTrace()
                            }
                        }
                        Constants.ROLE.NONCHANNEL -> {
                            /**
                             * menu home dashboard
                             *
                             */
                            try {
                                val homeViewDashboard = findViewById<View>(R.id.viewDashboard)
                                homeViewDashboard.hide()
                            }catch (e : Exception){
                                e.printStackTrace()
                            }

                            try {
                                val viewNotification = findViewById<View>(R.id.ivNotification)
                                viewNotification.invisible()
                            }catch (e : Exception){
                                e.printStackTrace()
                            }

                            try {
                                val tvHeaderPilihKategori = findViewById<View>(R.id.tvHeaderPilihKategori)
                                tvHeaderPilihKategori.hide()
                            }catch (e : Exception){
                                e.printStackTrace()
                            }

                            /**
                             * menu Order Suggestion
                             *
                             */
                            try {
                                val menuOrderSuggestion = findViewById<View>(R.id.vMenuOrderSuggestion)
                                menuOrderSuggestion.hide()
                            }catch (e : Exception){
                                e.printStackTrace()
                            }

                            /**
                             * menu Campaign Promo
                             *
                             */
                            try {
                                val menuCampaignPromo = findViewById<View>(R.id.vMenuCampaignPromo)
                                menuCampaignPromo.hide()
                            }catch (e : Exception){
                                e.printStackTrace()
                            }

                            /**
                             * menu create order
                             *
                             */
                            try {
                                val btnCreateRo = findViewById<View>(R.id.btnCreateRo)
                                btnCreateRo.hide()
                            }catch (e : Exception){
                                e.printStackTrace()
                            }

                            /**
                             * menu kredit limit & jatuh tempo
                             *
                             */
                            try {
                                val btnKreditLimit = findViewById<View>(R.id.btnKreditLimit)
                                btnKreditLimit.hide()
                            }catch (e : Exception){
                                e.printStackTrace()
                            }

                            /**
                             * menu Salesman Visit
                             *
                             */
                            try {
                                val btnSalesmanVisit = findViewById<View>(R.id.btnSalesmanVisit)
                                btnSalesmanVisit.hide()
                            }catch (e : Exception){
                                e.printStackTrace()
                            }

                            /**
                             * menu Competitor
                             *
                             */
                            try {
                                val btnCompetitor = findViewById<View>(R.id.btnCompetitor)
                                btnCompetitor.hide()
                            }catch (e : Exception){
                                e.printStackTrace()
                            }

                        }
                        "MD_ADMIN" -> {
                            /**
                             * menu Salesman Visit
                             *
                             */
                            try {
                                val btnSalesmanVisit = findViewById<View>(R.id.btnSalesmanVisit)
                                btnSalesmanVisit.hide()
                            }catch (e : Exception){
                                e.printStackTrace()
                            }
                        }
                    }
                } catch (e: Exception) {
                }
            })
        } catch (e: Exception) {
        }


    }



    fun encodeBase64(path: String): String? {

        val file = File(path)

        var encodedBase64 = ""
        var encImage: String? = null
        try {
            val fileInputStreamReader = FileInputStream(file)
            val bytes = ByteArray(file.length().toInt())
            fileInputStreamReader.read(bytes)
            encodedBase64 = Base64.encodeToString(bytes, Base64.DEFAULT)
            encImage = encodedBase64

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }


        return encImage

    }



    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        fade()
    }
}