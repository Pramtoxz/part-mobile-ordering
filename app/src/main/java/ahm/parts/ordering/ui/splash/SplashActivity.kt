package ahm.parts.ordering.ui.splash

import ahm.parts.ordering.R
import ahm.parts.ordering.helper.fade
import ahm.parts.ordering.helper.flagFullScreen
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.home.HomeActivity
import ahm.parts.ordering.ui.auth.login.LoginActivity
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer

class SplashActivity : BaseActivity<SplashViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        flagFullScreen()

        setContentView(R.layout.activity_splash)
        lifecycle.addObserver(viewModel)
        observeData()
    }

    private fun observeData() {
        viewModel.delayCountDown(2000)
        viewModel.splashNotifier.observe(this, Observer { finishSplash ->
            if (finishSplash) viewModel.checkUserIsExist()
        })
        viewModel.userExist.observe(this, Observer { gotoPage(it) })
    }

    /**
     * Menuju page Home / Login berdasarkan apakah ada user atau tidak
     *
     * @param isUserExist true jika user tersedia di database
     */
    private fun gotoPage(isUserExist: Boolean) {
        val targetIntent = if (isUserExist) HomeActivity::class.java else LoginActivity::class.java
        val mainIntent = Intent(this, targetIntent)
        startActivity(mainIntent)
        finish()
        fade()
    }
}
