package ahm.parts.ordering.ui.home.home.campaignpromo.fragment.brosur

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.campaignpromo.BrosurePromo
import ahm.parts.ordering.helper.extra
import ahm.parts.ordering.helper.getObject
import ahm.parts.ordering.helper.loadImage
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.home.HomeViewModel
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_brosur_detail_preview.*

class BrosureDetailActivity : BaseActivity<HomeViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brosur_detail_preview)

        initUI()

    }

    private fun initUI(){
        setToolbar(getString(R.string.lbl_title_promo_details),true)

        extra(Constants.BUNDLE.JSON).getObject<BrosurePromo>().apply {
            ivBrosure loadImage photo
        }

    }

}
