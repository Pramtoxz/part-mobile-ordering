package ahm.parts.ordering.ui.home.dealer.competitor.detail.single

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dealer.competitor.Competitor
import ahm.parts.ordering.helper.CalendarUtils
import ahm.parts.ordering.helper.extra
import ahm.parts.ordering.helper.getObject
import ahm.parts.ordering.helper.loadImage
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.home.HomeViewModel
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_competitor_detail.*

class CompetitorDetailActivity : BaseActivity<HomeViewModel>(), View.OnClickListener {

    lateinit var competitor : Competitor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_competitor_detail)

        initUI()
        initListener()

    }

    private fun initUI(){
        setToolbar(getString(R.string.lbl_title_competitor_details),true)

        competitor = extra(Constants.BUNDLE.JSON).getObject()

        competitor.apply {
            tvCodeDealer text "Dealer No #" + codeDealer
            tvNameDealer text nameCompetitor
            tvActivitasCompetitor text titleActivityCompetitor
            tvProduk text product
            tvBeginDate text CalendarUtils.setFormatDate(beginEffdate,"yyyy-MM-dd","MMMM dd, yyyy")
            tvLastDate text CalendarUtils.setFormatDate(endEffdate,"yyyy-MM-dd","MMMM dd, yyyy")
            tvDescription text description

            ivCompetitor.loadImage(
                photo[0].photo,
                skGroup
            )

        }


    }

    private fun initListener(){

    }

    override fun onClick(v: View?) {
        when(v!!){

        }
    }

}
