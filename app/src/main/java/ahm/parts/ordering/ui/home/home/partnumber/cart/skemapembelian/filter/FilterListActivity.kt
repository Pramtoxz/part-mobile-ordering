package ahm.parts.ordering.ui.home.home.partnumber.cart.skemapembelian.filter

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dashboard.partnumber.cart.CartFilter
import ahm.parts.ordering.data.model.home.dashboard.partnumber.cart.CartList
import ahm.parts.ordering.data.model.home.dashboard.partnumber.kodedealer.AllDealer
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.base.adapter.RecyclerAdapter
import ahm.parts.ordering.ui.home.HomeViewModel
import ahm.parts.ordering.ui.home.home.partnumber.cart.skemapembelian.SkemaPembelianActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_filter_list_part_number.*
import kotlinx.android.synthetic.main.item_skema_pembelian_filter.view.*

class FilterListActivity : BaseActivity<HomeViewModel>(), View.OnClickListener {

    lateinit var filterAdapter: RecyclerAdapter<CartFilter>
    var filters = ArrayList<CartFilter>()

    lateinit var kodeDealer : AllDealer
    lateinit var cart : CartList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter_list_part_number)

        kodeDealer = extra(Constants.BUNDLE.ORDERSUGESTION.JSON_DEALER).getObject()
        cart = extra(Constants.BUNDLE.PARTSEARCH.JSON_CART).getObject()

        initUI()
        initListener()

    }

    private fun initUI() {
        setToolbar(getString(R.string.lbl_title_filter_list), true)

        initAdapter()
    }

    private fun initAdapter(){

        filters.add(CartFilter(getString(R.string.filter_part_number)))
        filters.add(CartFilter(getString(R.string.filter_part_deskripsi)))
        filters.add(CartFilter(getString(R.string.filter_het)))
        filters.add(CartFilter(getString(R.string.filter_quantity_pembelian_w1)))
        filters.add(CartFilter(getString(R.string.filter_amount_pembelian_w1)))
        filters.add(CartFilter(getString(R.string.filter_quantity_pembelian_w2)))
        filters.add(CartFilter(getString(R.string.filter_amount_pembelian_w2)))
        filters.add(CartFilter("Quantity Pembelian W3"))
        filters.add(CartFilter("Amount Pembelian W3"))
        filters.add(CartFilter("Quantity Pembelian W4"))
        filters.add(CartFilter("Amount Pembelian W4"))
        filters.add(CartFilter("Quantity Average Pembelian"))
        filters.add(CartFilter("Amount Average Pembelian"))
        filters.add(CartFilter("Quantity Back Order"))
        filters.add(CartFilter("Amount Back Order"))
        filters.add(CartFilter("Quantity Suggested Order"))
        filters.add(CartFilter("Amount Suggested Order"))
        filters.add(CartFilter("Flag Campaign Promo"))
        filters.add(CartFilter("Kelipatan Dus"))

        filterAdapter = rvFilter.setAdapter(this, filters, R.layout.item_skema_pembelian_filter, {
            val item = filters[it]

            tvTitle text item.name

            if(item.isCheck){
                ivCheck.setImageResource(R.drawable.bg_fill_sorting_red)
            }else{
                ivCheck.setImageResource(R.drawable.bg_fill_sorting_grey)
            }

        }, {
            if(this.isCheck){
                this.isCheck = false
                this.isCheckInt = 0
            }else{
                this.isCheck = true
                this.isCheckInt = 1
            }
            filterAdapter.notifyDataSetChanged()
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == Constants.REQUEST.SKEMA_PEMBELIAN && resultCode == Constants.RESULT.SKEMA_PEMBELIAN){
            setResult(Constants.RESULT.SKEMA_PEMBELIAN){}
        }else if(requestCode == Constants.REQUEST.SKEMA_PEMBELIAN && resultCode == Constants.RESULT.SKEMA_PEMBELIAN_UPDATE){
            setResult(Constants.RESULT.SKEMA_PEMBELIAN_UPDATE){}
        }
    }

    private fun initListener() {
        btnSkema.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!) {
            btnSkema -> {
                goTo<SkemaPembelianActivity>(requestCode = Constants.REQUEST.SKEMA_PEMBELIAN) {
                    putExtra(Constants.BUNDLE.PARTSEARCH.JSON_CART,cart.getString())
                    putExtra(Constants.BUNDLE.PARTSEARCH.JSON_DEALER,kodeDealer.getString())
                    putExtra(Constants.BUNDLE.JSON,filters.getString())
                }
            }
        }
    }

}
