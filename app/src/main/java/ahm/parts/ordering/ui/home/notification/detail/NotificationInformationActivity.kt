package ahm.parts.ordering.ui.home.notification.detail

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.notification.Information
import ahm.parts.ordering.helper.extra
import ahm.parts.ordering.helper.getObject
import ahm.parts.ordering.helper.htmlTextLoad
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.home.HomeViewModel
import ahm.parts.ordering.ui.home.notification.NotificationViewModel
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_notification_information_detail.*

class NotificationInformationActivity : BaseActivity<NotificationViewModel>(), View.OnClickListener {

    lateinit var notificationInfo : Information

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_information_detail)

        initUI()
        initListener()

    }

    private fun initUI(){
        setToolbar(getString(R.string.lbl_title_details),true)

        notificationInfo = extra(Constants.BUNDLE.JSON).getObject()

        tvTitleNotif text notificationInfo.name

        webView htmlTextLoad notificationInfo.description
    }

    private fun initListener(){

    }

    override fun onClick(v: View?) {
        when(v!!){

        }
    }

}
