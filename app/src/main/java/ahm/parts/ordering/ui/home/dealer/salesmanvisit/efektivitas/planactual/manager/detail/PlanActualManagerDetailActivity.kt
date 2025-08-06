package ahm.parts.ordering.ui.home.dealer.salesmanvisit.efektivitas.planactual.manager.detail

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dealer.efektivitas.Sales
import ahm.parts.ordering.data.model.home.dealer.efektivitas.SalesDetail
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.efektivitas.EfektivitasViewModel
import ahm.parts.ordering.ui.widget.viewpager.ViewPagerAdapter
import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_realisasi_visit_plan_detail_viewpager.*

class PlanActualManagerDetailActivity : BaseActivity<EfektivitasViewModel>(), View.OnClickListener {

    var sales = ArrayList<SalesDetail>()

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

        val data = extra(Constants.BUNDLE.JSON).getObject<Sales>()
        sales.addAll(data.detail)

        for (i in 0 until sales.size){
            adapter.addFragment(PlanActualManagerDetailEfektivitasFragment.newInstance(i, sales[i],this@PlanActualManagerDetailActivity), "")
        }

        hideViewPrevNext()

        vPager.adapter = adapter

        vPager.currentItem = 0

        vPager.offscreenPageLimit = sales.size

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
        if(sales.size == 1){
            vBottom.hide()
        }

        when (currentPos) {
            0 -> {
                btnPrev.invisible()
                btnNext.show()
            }
            (sales.size - 1) -> {
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
                if(currentPos != (sales.size - 1)){
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
