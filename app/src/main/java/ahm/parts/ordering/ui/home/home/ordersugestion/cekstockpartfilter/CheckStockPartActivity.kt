package ahm.parts.ordering.ui.home.home.ordersugestion.cekstockpartfilter

import ahm.parts.ordering.R
import ahm.parts.ordering.helper.setAdapter
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.home.HomeViewModel
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_check_stock_part.*

class CheckStockPartActivity : BaseActivity<HomeViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_stock_part)

        initUI()

    }

    private fun initUI() {
        setToolbar(getString(R.string.lbl_title_cek_stock_part),true)

        srRefresh.setOnRefreshListener {
            srRefresh.isRefreshing = false
        }

        initAdapter()
    }

    private fun initAdapter() {
        var listStock = ArrayList<String>()

        listStock.add("")
        listStock.add("")
        listStock.add("")
        listStock.add("")
        listStock.add("")
        listStock.add("")
        listStock.add("")
        listStock.add("")
        listStock.add("")
        listStock.add("")
        listStock.add("")

        rvCheckStockPart.setAdapter(this, listStock, R.layout.item_favorite_part_number, {

        }, {

        })
    }

}
