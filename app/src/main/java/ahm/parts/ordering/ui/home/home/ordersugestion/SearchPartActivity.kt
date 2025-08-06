package ahm.parts.ordering.ui.home.home.ordersugestion

import ahm.parts.ordering.R
import ahm.parts.ordering.data.model.home.calendar.Calendar
import ahm.parts.ordering.helper.div
import ahm.parts.ordering.helper.goTo
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.home.HomeViewModel
import ahm.parts.ordering.ui.home.home.ordersugestion.cekstockpartfilter.CheckStockPartActivity
import ahm.parts.ordering.ui.home.home.ordersugestion.kelompok_barang.KelompokBarangSearchActivity
import ahm.parts.ordering.ui.home.home.ordersugestion.tipemotor.TipeMotorSearchActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_search_part_stock.*

class SearchPartActivity : BaseActivity<HomeViewModel>(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_part_stock)

        initUI()
        initListener()

    }

    private fun initUI(){
        setToolbar(getString(R.string.lbl_title_search_parts),true)

        spKelompokBarang.div(vDivKelompokBarang,R.color.colorPrimary) {
            goTo<KelompokBarangSearchActivity> {  }
        }

        spTypeMotor.div(vDivTypeMotor,R.color.colorPrimary){
            goTo<TipeMotorSearchActivity> {  }
        }

    }

    private fun initListener(){
        btnSearch.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            btnSearch -> {
                goTo<CheckStockPartActivity> {  }
            }

        }
    }

}
