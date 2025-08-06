package ahm.parts.ordering.ui.home.home.ordersugestion.ordersugestion

import ahm.parts.ordering.R
import ahm.parts.ordering.api.ApiResponse
import ahm.parts.ordering.api.ApiStatus
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dashboard.ordersuggestion.SuggestOrder
import ahm.parts.ordering.data.model.home.dashboard.partnumber.kodedealer.AllDealer
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.base.adapter.RecyclerAdapter
import ahm.parts.ordering.ui.home.home.ordersugestion.cart.CartActivity
import ahm.parts.ordering.ui.home.home.partnumber.cart.CartViewModel
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_suggest_order.*
import kotlinx.android.synthetic.main.bottom_sheet_order_sugestion.*
import kotlinx.android.synthetic.main.item_order_sugestion.view.*
import org.json.JSONArray
import org.json.JSONObject


class SuggestOrderActivity : BaseActivity<SuggestOrderViewModel>(), View.OnClickListener {

    lateinit var searchView: SearchView
    var isCheckAll = false

    lateinit var kodeDealer: AllDealer

    lateinit var suggestAdapter: SuggestOrderAdapter
    var suggestOrders = ArrayList<SuggestOrder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suggest_order)

        kodeDealer = extra(Constants.BUNDLE.JSON).getObject()

        initUI()
        initListener()

        observeData()
        viewModel.hitOrderSuggestion(kodeDealer.id)
    }

    private fun initUI() {
        setToolbar(getString(R.string.lbl_title_order_sugestion), true)

        roleFiturMenu()

        swipeRefresh.setOnRefreshListener {
            viewModel.hitOrderSuggestion(kodeDealer.id)
        }

        initAdapter()
    }

    private fun observeData() {
        viewModel.apiResponse.observe(this, Observer {
            showStateApiView(it) {
                viewModel.hitOrderSuggestion(kodeDealer.id)
            }
            if (it.status == ApiStatus.LOADING) {
                when (it.flagView) {
                    R.id.btnCreateRo -> {
                        loadingDialog.show(R.string.lbl_loading_harap_tunggu)
                    }
                }
            } else {
                if(it.flagLoadingDialog){
                    loadingDialog.dismiss()
                }
                if (it.status == ApiStatus.SUCCESS) {
                    when (it.flagView) {
                        R.id.btnCreateRo -> {
                            goTo<CartActivity> {
                                putExtra(
                                    Constants.BUNDLE.ORDERSUGESTION.JSON_DEALER,
                                    kodeDealer.getString()
                                )
                            }
                        }
                    }
                }
            }
        })

        viewModel.orderSuggestions.observe(this, Observer {
            suggestOrders.clear()
            suggestOrders.addAll(it)

            suggestAdapter.notifyDataSetChanged()

            tvLabelTotalPembelian text "TOTAL PEMBELIAN (" + "${suggestOrders.size}" + "items)"

            updatedData()
        })

    }

    private fun initAdapter() {
        suggestAdapter = SuggestOrderAdapter(suggestOrders, this) {}
        rvHistoryOrder.initItem(this, suggestAdapter)
    }

    private fun checkListAll() {
        if (isCheckAll) {
            suggestOrders.forEach {
                it.isChecked = false
            }
            isCheckAll = false
            ivCheck.hide()
        } else {
            suggestOrders.forEach {
                it.isChecked = true
            }
            isCheckAll = true
            ivCheck.show()
        }
        suggestAdapter.notifyDataSetChanged()
    }

    fun createJsonPart() {
        val jsonArray = JSONArray()

        for (i in 0 until suggestOrders.size) {
            val item = suggestOrders[i]

            if (item.isChecked) {
                val jsonObject = JSONObject()
                jsonObject.put("part_id", item.id)
                jsonObject.put("qty", item.qtyTotal)
                jsonArray.put(jsonObject)
            }
        }
        val jsonSend = jsonArray.toString()
        viewModel.hitUseSugestion(jsonSend,kodeDealer.id)
    }

    private fun updatedData() {
        var dblTotalPembelian = 0.0

        suggestOrders.forEach {
            dblTotalPembelian += it.amountTotal
        }

        tvTotalPembelian text StringMasker().addRp(dblTotalPembelian)
        tvSubTotalPembelian text StringMasker().addRp(dblTotalPembelian)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == Constants.REQUEST.SKEMA_PEMBELIAN && resultCode == Constants.RESULT.SKEMA_PEMBELIAN_UPDATE){
            viewModel.hitOrderSuggestion(kodeDealer.id)
        }
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
        val filteredList = java.util.ArrayList<SuggestOrder>()

        for (item in suggestOrders) {
            if (item.partNumber.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item)
            }
        }

        if (filteredList.size == 0) {
            viewModel.apiResponse.postValue(ApiResponse().responseEmpty(JSONArray(),R.id.rootContent,R.id.swipeRefresh,isHideView = true))
            /*snacked(rootView, "Data Tidak Ditemukan")*/
        }else{
            viewModel.apiResponse.postValue(ApiResponse().responseSuccess(JSONArray(),R.id.rootContent,R.id.swipeRefresh,isHideView = true))
        }

        try {
            suggestAdapter.filterList(filteredList)
        } catch (e: Exception) {
        }
    }


    private fun initListener() {
        btnCheckAll.setOnClickListener(this)
        btnCreateRo.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!) {
            btnCheckAll -> {
                checkListAll()
            }
            btnCreateRo -> {
                createJsonPart()
            }
        }
    }

}