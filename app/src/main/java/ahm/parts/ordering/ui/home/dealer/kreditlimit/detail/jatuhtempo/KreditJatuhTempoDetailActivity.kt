package ahm.parts.ordering.ui.home.dealer.kreditlimit.detail.jatuhtempo

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dealer.kreditlimit.KreditJatuhTempo
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.home.dealer.kreditlimit.KreditLimitViewModel
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_kredit_jatuh_tempo_detail.*

class KreditJatuhTempoDetailActivity : BaseActivity<KreditLimitViewModel>(), View.OnClickListener {

    lateinit var kreditJatuhTempo : KreditJatuhTempo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kredit_jatuh_tempo_detail)

        kreditJatuhTempo = extra(Constants.BUNDLE.JSON).getObject()

        initUI()
        initListener()

    }

    private fun initUI(){
        setToolbar(extra(Constants.BUNDLE.TOOLBAR),true)

        kreditJatuhTempo.apply {
            tvKodeDealer text dealerCode
            tvNameDealer text dealerName
            tvDate text CalendarUtils.setFormatDate(date,SERVER_DATE_FORMAT,VIEW_DATE_FORMAT)
            tvDateOver text CalendarUtils.setFormatDate(dateOver,SERVER_DATE_FORMAT,VIEW_DATE_FORMAT)
            tvAmount text StringMasker().addRp(amount)

            if(flag.equals("green",ignoreCase = true)){
                ivWarning.hide()
                tvDateOver.setTextColor(resources.getColor(R.color.green_dark))
            }else{
                ivWarning.show()
                tvDateOver.setTextColor(resources.getColor(R.color.red_amount_order))
            }

        }

    }

    private fun initListener(){

    }

    override fun onClick(v: View?) {
        when(v!!){

        }
    }

}
