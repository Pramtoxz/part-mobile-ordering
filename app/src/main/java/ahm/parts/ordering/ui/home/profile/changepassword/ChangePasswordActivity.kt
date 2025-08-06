package ahm.parts.ordering.ui.home.profile.changepassword

import ahm.parts.ordering.R
import ahm.parts.ordering.api.ApiStatus
import ahm.parts.ordering.helper.bottomSheet
import ahm.parts.ordering.helper.flagTransparant
import ahm.parts.ordering.helper.onClick
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.home.HomeViewModel
import ahm.parts.ordering.ui.home.profile.ProfileViewModel
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_change_password.*

/**
 * class ini mengandung interaksi UI dengan layout activity_change_password.xml
 *
 */
class ChangePasswordActivity : BaseActivity<ProfileViewModel>(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        initUI()
        initListener()

        observeData()
    }

    private fun initUI(){
        flagTransparant()
    }

    /**
     * fungsi untuk menghandle api
     *
     */
    private fun observeData() {
        viewModel.apiResponse.observe(this, Observer {
            if (it.status == ApiStatus.LOADING) {
                loadingDialog.show(R.string.lbl_loading_harap_tunggu)
            } else {
                loadingDialog.dismiss()
                if (it.status == ApiStatus.SUCCESS) {
                    dialogChangePassword(it.message[0].toString())
                } else {
                    snacked(rootView, it)
                }
            }
        })
    }

    /**
     * fungsi membuat dialog
     *
     */
    private fun dialogChangePassword(apiMessage : String){
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
     * fungsi click
     *
     */
    private fun initListener(){
        ivBack.setOnClickListener(this)
        btnShowPasswordCurrent.setOnClickListener(this)
        btnShowPasswordNew.setOnClickListener(this)
        btnShowPasswordRetype.setOnClickListener(this)
        btnChangePassword.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!){
            ivBack -> {
                onBackPressed()
            }
            btnChangePassword -> {
                val strPasswordNew = textOf(etPasswordNew)
                val strPasswordRetype = textOf(etPasswordRetype)

                if (isEmptyRequired(etPasswordCurrent,etPasswordNew,etPasswordRetype)) return

                if(strPasswordNew != strPasswordRetype){
                    snacked(rootView, getString(R.string.lbl_warning_password_yang_diulangi_berbeda))
                    return
                }

                viewModel.hitChangePassword(strPasswordNew)
            }
            btnShowPasswordCurrent -> {
                if (etPasswordCurrent.transformationMethod == null) {
                    etPasswordCurrent.transformationMethod = PasswordTransformationMethod()
                } else {
                    etPasswordCurrent.transformationMethod = null
                }
            }
            btnShowPasswordNew -> {
                if (etPasswordNew.transformationMethod == null) {
                    etPasswordNew.transformationMethod = PasswordTransformationMethod()
                } else {
                    etPasswordNew.transformationMethod = null
                }
            }
            btnShowPasswordRetype -> {
                if (etPasswordRetype.transformationMethod == null) {
                    etPasswordRetype.transformationMethod = PasswordTransformationMethod()
                } else {
                    etPasswordRetype.transformationMethod = null
                }
            }
        }
    }

}
