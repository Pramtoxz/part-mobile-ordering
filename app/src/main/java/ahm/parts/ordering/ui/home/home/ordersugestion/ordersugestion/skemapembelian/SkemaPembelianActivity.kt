package ahm.parts.ordering.ui.home.home.ordersugestion.ordersugestion.skemapembelian

import ahm.parts.ordering.R
import ahm.parts.ordering.api.ApiStatus
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dashboard.ordersuggestion.SkemaPembelian
import ahm.parts.ordering.data.model.home.dashboard.ordersuggestion.SuggestOrder
import ahm.parts.ordering.data.model.home.dashboard.partnumber.cart.CartFilter
import ahm.parts.ordering.data.model.home.dashboard.partnumber.cart.CartList
import ahm.parts.ordering.data.model.home.dashboard.partnumber.kodedealer.AllDealer
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.base.adapter.RecyclerAdapter
import ahm.parts.ordering.ui.home.HomeViewModel
import ahm.parts.ordering.ui.home.home.partnumber.cart.skemapembelian.SkemaPembelianViewModel
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_skema_pembelian.*
import kotlinx.android.synthetic.main.content_loading_list.*

class SkemaPembelianActivity : BaseActivity<SkemaPembelianViewModel>(), View.OnClickListener {

    lateinit var skemaPembelianAdapter: SkemaPembelianAdapter
    var skemaPembelians = ArrayList<SkemaPembelian>()
    lateinit var skemaFilters: List<CartFilter>

    lateinit var kodeDealer : AllDealer
    lateinit var order: SuggestOrder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skema_pembelian)

        kodeDealer = extra(Constants.BUNDLE.PARTSEARCH.JSON_DEALER).getObject()
        order = extra(Constants.BUNDLE.PARTSEARCH.JSON_ORDER).getObject()

        initUI()
        initListener()

        observeData()
    }

    private fun initUI() {
        setToolbar(getString(R.string.lbl_title_skema_pembelian), true)

        lLoadingView.hide()

        order.apply {

            tvPartNumber text this.partNumber
            tvPartDescription text this.partDescription
            tvItemGroup text this.itemGroup

            tvAvailabePcs text this.availablePart

            try {
                tvHet text StringMasker().addRp(this.het)
            } catch (e: Exception) {
            }

            tvSkemaSelected text this.skemaSelected

            try {
                tvTotalBelanja text StringMasker().addRp(this.amountTotal)
            } catch (e: Exception) {
            }

            tvQtyBackOrder text this.qtyBack + "pcs"

            try {
                tvAmountBackOrder text StringMasker().addRp(this.amountBack.toDouble())
            } catch (e: Exception) {
            }

            tvQtySuggest text this.qtySuggest + "pcs"

            try {
                tvAmountSuggestOrder text StringMasker().addRp(this.amountSuggest)
            } catch (e: Exception) {
            }

            tvKelipatanDus text "${this.multipleDus}"

            tvQtyAveragePembelian text this.qtyAvg
            tvAverageAmountPembelian text StringMasker().addRp(this.amountAvg)
            tvFlagCampaignPromo text this.flagCampaign

            tvTotalQty text this.qtySuggest

            skemaPembelians.clear()

            this.list.forEach{
                if(it.qty != "0" && it.price.toString() != "0"){
                    skemaPembelians.add(it)
                }
            }
        }

        initAdapter()


        viewModel.getUser().observe(this, Observer {
            try {
                when(it!!.id_role){
                    Constants.ROLE.NONCHANNEL -> {
                        tvAvailabePcs.hide()
                    }
                    Constants.ROLE.DEALER -> {
                        tvAvailabePcs.hide()
                    }
                }
            } catch (e: Exception) {
            }
        })
    }

    private fun observeData() {
        viewModel.apiResponse.observe(this, Observer {
            showStateApiView(it) {
                viewModel.hitSkemaPembelian(kodeDealer.id,"${order.id}", skemaFilters)
            }
            if (it.status == ApiStatus.LOADING) {
                when (it.flagView) {
                    R.id.btnSetQty -> {
                        loadingDialog.show(R.string.lbl_loading_harap_tunggu)
                    }
                }
            } else {
                loadingDialog.dismiss()
                if (it.status == ApiStatus.SUCCESS) {
                    when (it.flagView) {
                        R.id.btnSetQty -> {
                            setResult(Constants.RESULT.SKEMA_PEMBELIAN_UPDATE){}
                        }
                    }
                }
            }
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == Constants.REQUEST.SKEMA_PEMBELIAN && resultCode == Constants.RESULT.SKEMA_PEMBELIAN_UPDATE){
            viewModel.hitSkemaPembelian(kodeDealer.id,"${order.id}", skemaFilters)
        }
    }

    private fun initAdapter() {
        skemaPembelianAdapter = SkemaPembelianAdapter(skemaPembelians, this) {}
        rvSkemaPembelian.initItem(this, skemaPembelianAdapter)
    }

    private fun initListener() {
        btnSetQty.setOnClickListener(this)
        btnCancel.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!) {
            btnSetQty -> {
                viewModel.hitUpdateQty(order.id.toString(), textOf(tvTotalQty),kodeDealer.id)
            }
            btnCancel -> {
                setResult(Constants.RESULT.SKEMA_PEMBELIAN){}
            }
        }
    }

}
