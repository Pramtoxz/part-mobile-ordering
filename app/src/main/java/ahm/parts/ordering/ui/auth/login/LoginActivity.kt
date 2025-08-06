package ahm.parts.ordering.ui.auth.login

import ahm.parts.ordering.R
import ahm.parts.ordering.api.ApiStatus
import ahm.parts.ordering.helper.ClickPrevention
import ahm.parts.ordering.helper.goTo
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.auth.forgotpassword.ForgotPasswordActivity
import ahm.parts.ordering.ui.home.HomeActivity
import android.graphics.Color
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_login.*

/**
 * class ini mengandung interaksi UI dengan layout activity_login.xml
 *
 */
class LoginActivity : BaseActivity<LoginViewModel>(), ClickPrevention {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initUI()
        initListener()

        observeData()
    }

    private fun initUI() {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT

        etUsername.setText(session.userId)
    }

    private fun initListener() {
        btnLogin.setOnClickListener(this)
        btnForgot.setOnClickListener(this)
        btnShowPassword.setOnClickListener(this)
    }

    private fun observeData() {
        viewModel.updateFcmTokenId()
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
                     * Menuju page home ketika login berhasil
                     */
                    goTo<HomeActivity> { }
                    finish()
                } else {
                    /**
                     * Case saat respon error / jaringan error
                     */
                    snacked(rootView, it)
                }
            }
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnLogin -> {
                val strUserName = textOf(etUsername)
                val strPassword = textOf(etPassword)

                if (isEmptyRequired(etUsername, etPassword)) return
                viewModel.loginValidation(strUserName, strPassword)
            }
            R.id.btnShowPassword -> {
                if (etPassword.transformationMethod == null) {
                    etPassword.transformationMethod = PasswordTransformationMethod()
                } else {
                    etPassword.transformationMethod = null
                }
            }
            R.id.btnForgot -> {
                goTo<ForgotPasswordActivity> {  }
            }
        }
        super.onClick(v)
    }
}
