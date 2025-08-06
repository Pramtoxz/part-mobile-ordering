package ahm.parts.ordering.ui.home.order.tracking.detailitem

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.order.TrackingItem
import ahm.parts.ordering.data.model.home.order.TrackingOrder
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.home.HomeViewModel
import ahm.parts.ordering.ui.home.order.tracking.TrackingOrderViewModel
import ahm.parts.ordering.ui.home.order.tracking.adapter.TrackingOrderListItemAdapter
import ahm.parts.ordering.ui.home.order.tracking.detailpengiriman.TrackingOrderPengirimanDetailActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import kotlinx.android.synthetic.main.activity_tracking_order_details.*

class TrackingOrderDetailActivity : BaseActivity<TrackingOrderViewModel>() {

    lateinit var trackingOrder : TrackingOrder

    lateinit var searchView : SearchView

    var trackingItems = ArrayList<TrackingItem>()
    lateinit var trackingOrderAdapter : TrackingOrderListItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tracking_order_details)

        trackingOrder = extra(Constants.BUNDLE.JSON).getObject()

        initUI()

    }

    private fun initUI(){
        setToolbar(getString(R.string.lbl_title_details),true)

        trackingItems.addAll(trackingOrder.item)

        trackingOrder.apply {
            tvPartNumber text code
            tvDate text CalendarUtils.setFormatDate(dateOrder,SERVER_DATE_TIME_FORMAT,"d MMM yyyy")
            tvQtyOrder text totalItem + " item • "+ totalPcs + "pcs"
            tvAmountOrder text StringMasker().addRp(totalPrice.toDouble())
            tvBoQuantity text boQuantity
            tvShippingQty text shippingQuantity
        }


        initAdapter()
    }

    private fun initAdapter(){

        trackingOrderAdapter = TrackingOrderListItemAdapter(trackingItems, this) {}

        rvTrackingList.initItem(this,trackingOrderAdapter)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)
        val menuItem = menu!!.findItem(R.id.search).actionView

        searchView = menuItem as SearchView
        searchView.init(getString(R.string.hint_search), { strChange ->
            filter(strChange)
        }, { strSubmit ->
            filter(strSubmit)
        })

        return super.onCreateOptionsMenu(menu)
    }


    private fun filter(text: String) {
        val filteredList = java.util.ArrayList<TrackingItem>()

        for (item in trackingItems) {
            if (item.partNumber.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item)
            }
        }
        try {
            trackingOrderAdapter.filterList(filteredList)
        } catch (e: Exception) {
        }


//        if (filteredList.size == 0) {
//            snacked(rootView,"Data Tidak Ditemukan")
//        } else {
//
//        }

    }

}
