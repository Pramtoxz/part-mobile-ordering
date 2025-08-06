package ahm.parts.ordering.ui.home.home.partnumber.cart

import ahm.parts.ordering.R
import ahm.parts.ordering.api.ApiStatus
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dashboard.partnumber.cart.Cart
import ahm.parts.ordering.data.model.home.dashboard.partnumber.cart.CartList
import ahm.parts.ordering.data.model.home.dashboard.partnumber.kodedealer.AllDealer
import ahm.parts.ordering.data.room.user.User
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.helper.dialog.DialogHelper
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.base.adapter.RecyclerAdapter
import ahm.parts.ordering.ui.dialog.AlertDialog
import ahm.parts.ordering.ui.home.home.partnumber.cart.skemapembelian.filter.FilterListActivity
import ahm.parts.ordering.ui.home.home.partnumber.cart.summary.SummaryActivity
import ahm.parts.ordering.ui.home.home.partnumber.cart.suggestorder.SuggestOrderActivity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.bottom_sheet_history_detail.*
import kotlinx.android.synthetic.main.item_cart.view.*

class CartActivity : BaseActivity<CartViewModel>(), View.OnClickListener {

    lateinit var cartAdapter: RecyclerAdapter<CartList>
    var carts = ArrayList<CartList>()
    var cart :Cart? = null
    var user : User? = null
    lateinit var kodeDealer : AllDealer

    lateinit var dialogHelper : DialogHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        dialogHelper = DialogHelper(this,true)

        kodeDealer = extra(Constants.BUNDLE.PARTSEARCH.JSON_DEALER).getObject()

        initUI()
        initListener()

        observerData()
        viewModel.hitCart(kodeDealer.id)

    }

    private fun initUI() {
        setToolbar(getString(R.string.lbl_title_cart), true)

        swipeRefresh.setOnRefreshListener {
            viewModel.hitCart(kodeDealer.id)
        }

        viewModel.getUser().observe(this, Observer {
            user = it
            initAdapter()
        })
    }

    private fun observerData(){
        viewModel.apiResponse.observe(this, Observer {
            showStateApiView(it){
                viewModel.hitCart(kodeDealer.id)
            }

            if(it.status == ApiStatus.LOADING){
                when(it.flagView){
                    R.id.btnDelete -> {
                        loadingDialog.show(R.string.lbl_loading_harap_tunggu)
                    }
                }
            }else{
                loadingDialog.dismiss()
                if(it.status == ApiStatus.SUCCESS){
                    when(it.flagView){
                        R.id.btnDelete -> {
                            viewModel.hitCart(kodeDealer.id)
                        }
                    }
                }
            }
        })

        viewModel.cartData.observe(this, Observer {
            cart = it
            carts.clear()
            carts.addAll(it.detail)

            tvLabelTotalPembelian text "TOTAL PEMBELIAN ("+carts.size.toString()+ "items)"

            try {
                tvDiscount text StringMasker().addRp(it.discount.toDouble())
            } catch (e: Exception) {
            }

            updatedData()

            cartAdapter.notifyDataSetChanged()
        })

    }

    private fun initAdapter() {
        cartAdapter = rvCart.setAdapter(this, carts, R.layout.item_cart, {
            val item = carts[it]

            when(user!!.id_role){
                Constants.ROLE.DEALER -> {
                    tvAvailabePcs.hide()
                }
            }

            tvNumber text (it + 1).toString()
            tvCountCart text item.qty

            val totalAmountQty = item.het.toDouble() * item.qty.toDouble()
            tvTotalAmount text StringMasker().addRp(totalAmountQty)

            tvPartNumber text item.partNumber
            tvPartDescription text item.partDescription
            tvHet text StringMasker().addRp(item.het.toDouble())
            tvAvailabePcs text item.availablePart
            tvItemGroup text item.itemGroup

            ivInfo.onClick {
                goTo<FilterListActivity>(requestCode = Constants.REQUEST.SKEMA_PEMBELIAN) {
                    putExtra(Constants.BUNDLE.PARTSEARCH.JSON_CART,item.getString())
                    putExtra(Constants.BUNDLE.PARTSEARCH.JSON_DEALER,kodeDealer.getString())
                }
            }

            btnEdit.onClick {
                bottomSheet(R.layout.dialog_bottom_edit_quantity){
                    val etQty = it.findViewById<EditText>(R.id.etQty)

                    etQty text item.qty

                    ViewHelper.showKeyboard(etQty,this@CartActivity)

                    val btnSubmitQty = it.findViewById<Button>(R.id.btnSubmitQty)

                    var strQty = item.qty
                    etQty.onChangeText {
                        strQty = it
                    }

                    btnSubmitQty.onClick {
                        if(isEmptyRequired(etQty)) return@onClick

                        item.qty = strQty

                        viewModel.hitUpdateQty(item.id.toString(),strQty,kodeDealer.id)

                        cartAdapter.notifyDataSetChanged()
                        updatedData()
                        this.dismiss()
                    }

                }
            }

            btnDelete.onClick {
                context?.let {
                    AlertDialog(it)
                        .onPositiveListener { view, dialog ->
                            viewModel.hitDeleteCart(item.id.toString(),kodeDealer.id)
                            dialog.dismiss()
                        }
                        .show(R.string.dialog_title_delete_cart, R.string.dialog_content_delete_cart)
                }
            }

        }, {})
    }

    private fun updatedData(){
        var dblTotalPembelian = 0.0

        carts.forEach {
            var totalAmountQty = it.het.toDouble()
            totalAmountQty *= it.qty.toDouble()

            dblTotalPembelian += totalAmountQty
        }

        tvTotalPembelian text StringMasker().addRp(dblTotalPembelian)
        tvSubTotalPembelian text StringMasker().addRp(dblTotalPembelian)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == Constants.REQUEST.SKEMA_PEMBELIAN && resultCode == Constants.RESULT.SKEMA_PEMBELIAN_UPDATE){
            viewModel.hitCart(kodeDealer.id)
        }else if(requestCode == Constants.REQUEST.SUGGEST_ORDER_RO && resultCode == Constants.RESULT.SUGGEST_ORDER_RO){
            viewModel.hitCart(kodeDealer.id)
        }else if(requestCode == Constants.REQUEST.PART_NUMBER_SUMMARY && resultCode == Constants.RESULT.PART_NUMBER_SUMMARY){
            val intent = Intent()
            setResult(Constants.RESULT.PART_NUMBER_SUMMARY,intent)
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_cart_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add ->{
                bottomSheet(R.layout.dialog_bottom_cart_add){
                    val btnSuggest = it.findViewById<Button>(R.id.btnSuggest)!!
                    val btnSearchPart = it.findViewById<Button>(R.id.btnSearchPart)!!

                    btnSuggest.onClick {
                        this.dismiss()
                        goTo<SuggestOrderActivity>(requestCode = Constants.REQUEST.SUGGEST_ORDER_RO) {
                            putExtra(Constants.BUNDLE.PARTSEARCH.JSON_DEALER,kodeDealer.getString())
                        }
                    }
                    btnSearchPart.onClick {
                        this.dismiss()
                        val intent = Intent()
                        setResult(Constants.RESULT.PART_NUMBER_SUGGEST_ORDER,intent)
                        finish()
                    }
                }


            }
        }
        return super.onOptionsItemSelected(item)
    }



    private fun initListener() {
        btnNext.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!) {
            btnNext -> {
                if(carts.isNotEmpty()){
                    goTo<SummaryActivity>(requestCode = Constants.REQUEST.PART_NUMBER_SUMMARY) {
                        putExtra(Constants.BUNDLE.JSON,cart.getString())
                        putExtra(Constants.BUNDLE.PARTSEARCH.JSON_DEALER,kodeDealer.getString())
                    }
                }
            }
        }
    }


//        val partNumbers = extra(Constants.BUNDLE.JSON).toList<PartNumberFilterSearch>()
//
//        partNumbers.forEach {
//            if(it.isChart){
//                carts.add(it)
//            }
//        }

}
