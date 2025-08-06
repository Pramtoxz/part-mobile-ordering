package ahm.parts.ordering.ui.home.dealer.competitor.detail.slider

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dealer.competitor.Competitor
import ahm.parts.ordering.data.model.home.dealer.competitor.Photo
import ahm.parts.ordering.helper.CalendarUtils
import ahm.parts.ordering.helper.extra
import ahm.parts.ordering.helper.getObject
import ahm.parts.ordering.helper.loadImage
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.home.HomeViewModel
import ahm.parts.ordering.ui.home.dealer.competitor.adapter.ShadowTransformer
import ahm.parts.ordering.ui.home.dealer.competitor.adapter.SliderCarouselCompetitorAdapter
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_competitor_detail_slider.*
import java.util.*

class CompetitorDetailActivity : BaseActivity<HomeViewModel>(), View.OnClickListener {

    lateinit var competitor : Competitor

    var currentCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_competitor_detail_slider)

        initUI()
        initListener()

    }

    private fun initUI(){
        setToolbar(getString(R.string.lbl_title_competitor_details),true)

        competitor = extra(Constants.BUNDLE.JSON).getObject()

        competitor.apply {
            tvCodeDealer text "Dealer No #$codeDealer"
            tvNameDealer text nameCompetitor
            tvActivitasCompetitor text titleActivityCompetitor
            tvProduk text product
            tvBeginDate text CalendarUtils.setFormatDate(beginEffdate,"yyyy-MM-dd","MMMM dd, yyyy")
            tvLastDate text CalendarUtils.setFormatDate(endEffdate,"yyyy-MM-dd","MMMM dd, yyyy")
            tvDescription text description

            initSlider(photo)

        }


    }


    private fun initSlider(sliders: List<Photo>) {

        val listSlider = ArrayList<String>()
        if (sliders.size == 1) {
            listSlider.add(sliders[0].photo)
        }

        sliders.forEach {
            listSlider.add(it.photo)
        }

        val adapterSlider = SliderCarouselCompetitorAdapter(this)

        adapterSlider.addCardItem(listSlider)


        vpSlider.adapter = adapterSlider
        val cardShadowTransformer = ShadowTransformer(vpSlider, adapterSlider)
        cardShadowTransformer.enableScaling(false)

        vpSlider.setPageTransformer(false, cardShadowTransformer)
        vpSlider.offscreenPageLimit = listSlider.size

        dotsIndicator.setViewPager(vpSlider)


    }


    private fun initListener(){

    }

    override fun onClick(v: View?) {
        when(v!!){

        }
    }

}
