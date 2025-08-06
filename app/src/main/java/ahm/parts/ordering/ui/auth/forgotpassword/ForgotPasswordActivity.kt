package ahm.parts.ordering.ui.auth.forgotpassword

import ahm.parts.ordering.R
import ahm.parts.ordering.api.ApiStatus
import ahm.parts.ordering.helper.bottomSheet
import ahm.parts.ordering.helper.flagTransparant
import ahm.parts.ordering.helper.onClick
import ahm.parts.ordering.ui.base.BaseActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_forgot_password.*

/**
 * class ini mengandung interaksi UI dengan layout activity_forgot_password.xml
 *
 */
class ForgotPasswordActivity : BaseActivity<ForgotPasswordViewModel>(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        initUI()
        initListener()

        observeData()
    }

    private fun initUI(){
        flagTransparant()
    }

    /**
     * Semua observer di inisialisasikan pada method observeData()
     */
    private fun observeData() {
        viewModel.messageState.observe(this, Observer {
            snacked(rootView, it.message)
        })
        viewModel.apiResponse.observe(this, Observer {
            if (it.status == ApiStatus.LOADING) {
                loadingDialog.show(R.string.lbl_loading_harap_tunggu)
            } else {
                loadingDialog.dismiss()
                if (it.status == ApiStatus.SUCCESS) {
                    /**
                     * Observe ketika sukses api status 1
                     */
                    dialogForgot(it.message[0].toString())
                } else {
                    snacked(rootView, it)
                }
            }
        })
    }

    /**
     * Menampilkan Dialog
     *
     */
    private fun dialogForgot(apiMessage : String){
        bottomSheet(R.layout.dialog_bottom_alert_forgot){
            val tvMessage = it.findViewById<TextView>(R.id.tvMessage)
            val btnOk = it.findViewById<AppCompatButton>(R.id.btnOk)

            tvMessage text apiMessage

            btnOk.onClick {
                this.dismiss()
                finish()
            }
        }
    }

    /**
     * Klik UI di inisialisasikan pada fungsi ini
     *
     */
    private fun initListener(){
        btnForgotPassword.setOnClickListener(this)
        ivBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!){
            btnForgotPassword -> {
                val strEmail = textOf(etEmail)

                if (isEmptyRequired(etEmail)) return

                viewModel.forgotValidation(strEmail)
            }
            ivBack -> {
                onBackPressed()
            }
        }
    }

}
