package ahm.parts.ordering.ui.home.order.tracking.detailpengiriman

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.order.Invoice
import ahm.parts.ordering.data.model.home.order.TrackingItem
import ahm.parts.ordering.data.model.home.order.TrackingOrderDetail
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.home.HomeViewModel
import ahm.parts.ordering.ui.home.order.tracking.TrackingOrderViewModel
import ahm.parts.ordering.ui.home.order.tracking.adapter.TrackingOrderListPengirimanAdapter
import android.os.Bundle
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_tracking_order_pengiriman_details.*

class TrackingOrderPengirimanDetailActivity : BaseActivity<TrackingOrderViewModel>() {

    lateinit var trackingItems : TrackingItem

    var invoices = ArrayList<Invoice>()
    lateinit var trackingOrderAdapter : TrackingOrderListPengirimanAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tracking_order_pengiriman_details)

        trackingItems = extra(Constants.BUNDLE.JSON).getObject()

        initUI()

        observeData()
        viewModel.hitTrackingOrderDetail("${trackingItems.id}")
    }

    private fun initUI(){
        setToolbar(getString(R.string.lbl_title_details),true)

        rvInvoice.lyTrans()

        trackingItems.apply {
            tvPartNumber text partNumber
            tvPartDescription text partDescription
            tvItemGroup text itemGroup
            tvTotalQty text "$totalPcs pcs"
            tvTotalAmount text StringMasker().addRp(totalPrice.toDouble())
            tvStatusPengiriman text "$delivered / $totalPcs DELIVERED"
        }

        initAdapter()
    }

    private fun observeData(){
        viewModel.apiResponse.observe(this, Observer {
            showStateApiView(it,1000){
                viewModel.hitTrackingOrderDetail("${trackingItems.id}")
            }
        })

        viewModel.trackingOrderDetails.observe(this, Observer {
            invoices.clear()
            invoices.addAll(it.invoice)

            trackingOrderAdapter.notifyDataSetChanged()
        })
    }

    private fun initAdapter(){
        trackingOrderAdapter = TrackingOrderListPengirimanAdapter(invoices, this) {}

        rvInvoice.initItem(this,trackingOrderAdapter)
    }

}
