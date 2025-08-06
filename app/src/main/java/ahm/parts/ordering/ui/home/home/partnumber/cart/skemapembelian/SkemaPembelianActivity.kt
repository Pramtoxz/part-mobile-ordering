package ahm.parts.ordering.ui.home.home.partnumber.cart.skemapembelian

import ahm.parts.ordering.R
import ahm.parts.ordering.api.ApiStatus
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dashboard.partnumber.cart.CartFilter
import ahm.parts.ordering.data.model.home.dashboard.partnumber.cart.CartList
import ahm.parts.ordering.data.model.home.dashboard.partnumber.cart.skemapembelian.ListSkemaPembelian
import ahm.parts.ordering.data.model.home.dashboard.partnumber.kodedealer.AllDealer
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.base.adapter.RecyclerAdapter
import ahm.parts.ordering.ui.home.HomeViewModel
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_skema_pembelian.*
import kotlinx.android.synthetic.main.item_skema_pembelian.view.*

class SkemaPembelianActivity : BaseActivity<SkemaPembelianViewModel>(), View.OnClickListener {

    lateinit var skemaPembelianAdapter: SkemaPembelianAdapter
    var skemaPembelians = ArrayList<ListSkemaPembelian>()
    lateinit var skemaFilters: List<CartFilter>

    lateinit var kodeDealer: AllDealer
    lateinit var cart: CartList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skema_pembelian)

        kodeDealer = extra(Constants.BUNDLE.ORDERSUGESTION.JSON_DEALER).getObject()
        cart = extra(Constants.BUNDLE.PARTSEARCH.JSON_CART).getObject()
        skemaFilters = extra(Constants.BUNDLE.JSON).toList()

        initUI()
        initListener()

        observeData()
        viewModel.hitSkemaPembelian(kodeDealer.id,"${cart.id}", skemaFilters)
    }

    private fun initUI() {
        setToolbar(getString(R.string.lbl_title_skema_pembelian), true)

        cart.apply {
            tvPartNumber text this.partNumber
            tvPartDescription text this.partDescription
            tvItemGroup text this.itemGroup
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
                viewModel.hitSkemaPembelian(kodeDealer.id,"${cart.id}", skemaFilters)
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
                            val intent = Intent()
                            setResult(Constants.RESULT.SKEMA_PEMBELIAN_UPDATE, intent)
                            finish()
                        }
                    }
                }
            }
        })

        viewModel.skemaPembelian.observe(this, Observer {

            tvAvailabePcs text "Available " + it.availablePart + " pcs"

            try {
                tvHet text StringMasker().addRp(it.het)
            } catch (e: Exception) {
            }

            tvSkemaSelected text it.skemaSelected

            try {
                tvTotalBelanja text StringMasker().addRp(it.amountTotal)
            } catch (e: Exception) {
            }

            tvQtyBackOrder text it.qtyBack + "pcs"
            try {
                tvAmountBackOrder text StringMasker().addRp(it.amountBack.toDouble())
            } catch (e: Exception) {
            }
            tvQtySuggest text it.qtySuggest + "pcs"

            try {
                tvAmountSuggestOrder text StringMasker().addRp(it.amountSuggest.toDouble())
            } catch (e: Exception) {
            }

            tvKelipatanDus text "${it.multipleDus}"

            tvQtyAveragePembelian text it.qtyAvg
            tvAverageAmountPembelian text StringMasker().addRp(it.amountAvg)
            tvFlagCampaignPromo text it.flagCampaign

            tvTotalQty text it.qtySuggest

            skemaPembelians.clear()

            Log.e("it.list",it.list.getString())

            it.list.forEach{
                if(!it.qty.equals("0") && it.price.toString() != "0"){
                    skemaPembelians.add(it)
                }
            }
            skemaPembelianAdapter.notifyDataSetChanged()
        })

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
                viewModel.hitUpdateQty(cart.id.toString(), textOf(tvTotalQty),kodeDealer.id)
            }
            btnCancel -> {
                setResult(Constants.RESULT.SKEMA_PEMBELIAN){}
            }
        }
    }

}