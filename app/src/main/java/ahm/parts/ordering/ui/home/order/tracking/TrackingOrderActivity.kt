package ahm.parts.ordering.ui.home.order.tracking

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dashboard.partnumber.kodedealer.AllDealer
import ahm.parts.ordering.data.model.home.order.TrackingFilter
import ahm.parts.ordering.data.model.home.order.TrackingOrder
import ahm.parts.ordering.data.model.home.order.TrackingOrderParam
import ahm.parts.ordering.data.model.home.order.TrackingSorting
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.base.adapter.RecyclerAdapter
import ahm.parts.ordering.ui.dialog.BasicDialog
import ahm.parts.ordering.ui.dialog.TrackingFilterDialog
import ahm.parts.ordering.ui.home.order.tracking.adapter.TrackingOrderAdapter
import ahm.parts.ordering.ui.home.order.tracking.detailitem.TrackingOrderDetailActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_tracking_order.*
import kotlinx.android.synthetic.main.item_sorting_part.view.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList


class TrackingOrderActivity : BaseActivity<TrackingOrderViewModel>(){

    lateinit var trackingParam : TrackingOrderParam
    lateinit var kodeDealer : AllDealer

    lateinit var trackingOrderAdapter: TrackingOrderAdapter
    var trackingOrders = ArrayList<TrackingOrder>()

    var trackingSorting = ""

    var strSearch = ""
    var timer = Timer()

    lateinit var dialogFilter : TrackingFilterDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tracking_order)

        kodeDealer = extra(Constants.BUNDLE.KODEDEALER).getObject()
        trackingParam = extra(Constants.BUNDLE.PARAM).getObject()

        initUI()

        observeData()
        hitApi()
    }

    private fun initUI() {
        setToolbar(getString(R.string.lbl_title_tracking_order), true)

        dialogFilter = TrackingFilterDialog(this, R.style.Dialog)

        swipeRefresh.setOnRefreshListener {
            hitApi()
        }

        etSearch.onChangeText {
            strSearch = it

            timer = onSearch(timer) {
                hitApi()
            }
        }


        initAdapter()
    }

    private fun hitApi(){
        viewModel.hitTrackingOrder(kodeDealer.id,strSearch,trackingSorting,trackingParam)
    }

    private fun observeData(){
        viewModel.apiResponse.observe(this, Observer {
            showStateApiView(it){
                hitApi()
            }
        })

        viewModel.trackingOrders.observe(this, Observer {
            trackingOrders.clear()
            trackingOrders.addAll(it)

            trackingOrderAdapter.notifyDataSetChanged()
        })

    }

    private fun initAdapter(){
        trackingOrderAdapter = TrackingOrderAdapter(trackingOrders,this){
            goTo<TrackingOrderDetailActivity> {
                putExtra(Constants.BUNDLE.JSON,trackingOrders[it].getString())
            }
        }

        rvTrackingOrder.initItem(this,trackingOrderAdapter)
    }

    fun createJsonFilter(trackingSorts : ArrayList<TrackingSorting>) {
        val jsonArray = JSONArray()

        for (i in 0 until trackingSorts.size) {
            val item = trackingSorts[i]

            if (item.selectedName != "") {
                val jsonObject = JSONObject()
                jsonObject.put("sorting", item.selectedName)
                jsonArray.put(jsonObject)
            }
        }
        trackingSorting = jsonArray.toString()

        hitApi()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_order_filter, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.filter ->{
                dialogFilter.show()

                dialogFilter.positiveListener = object : TrackingFilterDialog.ClickListenerPos {
                    override fun clickPos(
                        dialog: TrackingFilterDialog,
                        list: ArrayList<TrackingSorting>
                    ) {
                        createJsonFilter(list)
                    }
                }

            }
        }
        return super.onOptionsItemSelected(item)
    }

}
