package ahm.parts.ordering.ui.home.home.partnumber.historyorder.list

import ahm.parts.ordering.R
import ahm.parts.ordering.helper.setAdapter
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.base.adapter.RecyclerAdapter
import ahm.parts.ordering.ui.home.HomeViewModel
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_part_number_history_order.*

class HistoryOrderActivity : BaseActivity<HomeViewModel>(), View.OnClickListener {

    lateinit var historyOrderAdapter: RecyclerAdapter<String>
    var historyOrders = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_part_number_history_order)

        initUI()
        initListener()

    }

    private fun initUI() {
        setToolbar(getString(R.string.lbl_title_history_order), true)

        initAdapter()
    }

    private fun initAdapter() {

        historyOrders.add("")
        historyOrders.add("")
        historyOrders.add("")
        historyOrders.add("")
        historyOrders.add("")
        historyOrders.add("")
        historyOrders.add("")
        historyOrders.add("")
        historyOrders.add("")
        historyOrders.add("")
        historyOrders.add("")
        historyOrders.add("")
        historyOrders.add("")

        historyOrderAdapter = rvHistoryOrder.setAdapter(this, historyOrders, R.layout.item_part_number_history_order, {
            val item = historyOrders[it]

        }, {

        })
    }

    private fun initListener() {

    }

    override fun onClick(v: View?) {
        when (v!!) {

        }
    }

}
