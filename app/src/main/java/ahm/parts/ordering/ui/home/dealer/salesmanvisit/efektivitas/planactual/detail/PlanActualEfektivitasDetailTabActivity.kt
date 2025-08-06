package ahm.parts.ordering.ui.home.dealer.salesmanvisit.efektivitas.planactual.detail

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dealer.efektivitas.EfData
import ahm.parts.ordering.data.model.home.dealer.efektivitas.EfDetail
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.efektivitas.EfektivitasViewModel
import ahm.parts.ordering.ui.widget.viewpager.ViewPagerAdapter
import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_realisasi_visit_plan_detail_viewpager.*

class PlanActualEfektivitasDetailTabActivity : BaseActivity<EfektivitasViewModel>(), View.OnClickListener {

    var planActuals = ArrayList<EfDetail>()

    var currentPos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_realisasi_visit_plan_detail_viewpager)

        initUI()
        initListener()

    }

    private fun initUI(){
        setToolbar(getString(R.string.lbl_title_details),true)

        val adapter = ViewPagerAdapter(supportFragmentManager)

        val data = extra(Constants.BUNDLE.JSON).getObject<EfData>()
        planActuals.addAll(data.detail)

        for (i in 0 until planActuals.size){
            adapter.addFragment(PlanActualEfektivitasDetailFragment.newInstance(i, planActuals[i],this@PlanActualEfektivitasDetailTabActivity), "")
        }

        hideViewPrevNext()

        vPager.adapter = adapter

        vPager.currentItem = 0

        vPager.offscreenPageLimit = planActuals.size

        vPager.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(i: Int, v: Float, i1: Int) {
            }

            override fun onPageSelected(i: Int) {
                currentPos = i
            }

            override fun onPageScrollStateChanged(i: Int) {
            }
        })
    }

    private fun hideViewPrevNext(){
        if(planActuals.size == 1){
            vBottom.hide()
        }

        when (currentPos) {
            0 -> {
                btnPrev.invisible()
                btnNext.show()
            }
            (planActuals.size - 1) -> {
                btnPrev.show()
                btnNext.invisible()
            }
            else -> {
                btnPrev.show()
                btnNext.invisible()
            }
        }
    }

    private fun initListener(){
        btnPrev.setOnClickListener(this)
        btnNext.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!){
            btnNext -> {
                if(currentPos != (planActuals.size - 1)){
                    currentPos += 1
                    vPager.currentItem = currentPos
                    hideViewPrevNext()
                }
            }
            btnPrev -> {
                if(currentPos != 0){
                    currentPos -= 1
                    vPager.currentItem = currentPos
                    hideViewPrevNext()
                }
            }
        }
    }

}
