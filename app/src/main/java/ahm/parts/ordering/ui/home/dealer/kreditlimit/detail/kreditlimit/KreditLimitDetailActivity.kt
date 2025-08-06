package ahm.parts.ordering.ui.home.dealer.kreditlimit.detail.kreditlimit

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dealer.kreditlimit.KreditLimit
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.home.dealer.kreditlimit.KreditLimitViewModel
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_kredit_limit_detail.*

class KreditLimitDetailActivity : BaseActivity<KreditLimitViewModel>(), View.OnClickListener {

    lateinit var kreditLimit : KreditLimit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kredit_limit_detail)

        kreditLimit = extra(Constants.BUNDLE.JSON).getObject()

        initUI()
        initListener()

    }

    private fun initUI(){
        setToolbar(getString(R.string.lbl_title_kredit_limit_details),true)

        kreditLimit.apply {
            tvKodeDealer text dealerCode
            tvNameDealer text dealerName
            tvDate text CalendarUtils.setFormatDate(date,SERVER_DATE_FORMAT,VIEW_DATE_FORMAT)
            tvPlafonKredit text StringMasker().addRp(plafonKreditLimit)
            tvMaximumOpen text StringMasker().addRp(maximumOpen)
        }

    }

    private fun initListener(){

    }

    override fun onClick(v: View?) {
        when(v!!){

        }
    }

}
