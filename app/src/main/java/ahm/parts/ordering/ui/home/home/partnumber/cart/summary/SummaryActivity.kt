package ahm.parts.ordering.ui.home.home.partnumber.cart.summary

import ahm.parts.ordering.R
import ahm.parts.ordering.api.ApiStatus
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dashboard.partnumber.cart.Cart
import ahm.parts.ordering.data.model.home.dashboard.partnumber.cart.CartList
import ahm.parts.ordering.data.model.home.dashboard.partnumber.cart.summary.Summary
import ahm.parts.ordering.data.model.home.dashboard.partnumber.kodedealer.AllDealer
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.home.HomeViewModel
import ahm.parts.ordering.ui.home.home.partnumber.cart.CartViewModel
import ahm.parts.ordering.ui.home.home.partnumber.cart.summary.result.SummaryResultActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_part_number_summary.*
import kotlinx.android.synthetic.main.item_part_summary.view.*

class SummaryActivity : BaseActivity<CartViewModel>(), View.OnClickListener {

    var strDateMonth = ""
    var summarys = ArrayList<CartList>()

    var cart = Cart()
    lateinit var kodeDealer : AllDealer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_part_number_summary)

        initUI()
        initListener()

        observeData()
    }

    private fun initUI(){
        setToolbar(getString(R.string.lbl_title_summary),true)

        cart = extra(Constants.BUNDLE.JSON).getObject()
        kodeDealer = extra(Constants.BUNDLE.PARTSEARCH.JSON_DEALER).getObject()

        summarys.addAll(cart.detail)

        var dblTotalPembelian = 0.0

        summarys.forEach {
            var totalAmountQty = it.het.toDouble()
            totalAmountQty *= it.qty.toDouble()

            dblTotalPembelian += totalAmountQty
        }

        tvTotalPembelian text StringMasker().addRp(dblTotalPembelian)
        tvItemPembelian text "Total Pembelian ("+"${summarys.size}"+" items)"

        try {
            tvDiscount text StringMasker().addRp(cart.discount.toDouble())
        } catch (e: Exception) {
        }

        initAdapter()
    }

    private fun observeData(){
        viewModel.apiResponse.observe(this, Observer {
            if(it.status == ApiStatus.LOADING){
                when(it.flagView){
                    R.id.btnSubmitOrder -> {
                        loadingDialog.show(R.string.lbl_loading_harap_tunggu)
                    }
                }
            }else{
                loadingDialog.dismiss()
                if(it.status == ApiStatus.SUCCESS){
                    when(it.flagView){
                        R.id.btnSubmitOrder -> {

                        }
                    }
                }
            }
        })

        viewModel.summary.observe(this, Observer {

            val summary = Summary(it.codeOrder,textOf(etMonthDeliver),textOf(tvTotalPembelian),"Total Pembelian ("+ "${summarys.size}"+"items)",summarys[0].discount)

            goTo<SummaryResultActivity>(requestCode = Constants.REQUEST.PART_NUMBER_SUMMARY) {
                putExtra(Constants.BUNDLE.JSON,summary.getString())
                putExtra(Constants.BUNDLE.PARTSEARCH.JSON_CART,cart.getString())
            }
        })
    }

    private fun initAdapter(){
        rvSummary.setAdapter(this,summarys,R.layout.item_part_summary,{
            val item = summarys[it]

            tvNumber text "${(it + 1)}"

            tvPartNumber text item.partNumber
            tvPartDescription text item.partDescription
            tvAvailabePcs text item.qty + "pcs"
            tvItemGroup text item.itemGroup

            tvSubPrice text StringMasker().addRp(item.subPrice)

            val totalSubPrice = item.subPrice * item.qty.toDouble()
            tvTotalSubPrice text StringMasker().addRp(totalSubPrice)


        },{

        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == Constants.REQUEST.PART_NUMBER_SUMMARY && resultCode == Constants.RESULT.PART_NUMBER_SUMMARY){
            val intent = Intent()
            setResult(Constants.RESULT.PART_NUMBER_SUMMARY,intent)
            finish()
        }
    }

    private fun initListener(){
        etMonthDeliver.setOnClickListener(this)
        btnSubmitOrder.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!){
            etMonthDeliver -> {
                datePicker {
                    etMonthDeliver text CalendarUtils.setFormatDate(it,"dd MM yyyy","dd MMMM yyyy")
                    strDateMonth = CalendarUtils.setFormatDate(it,"dd MM yyyy","yyyy-MM-dd")
                    Log.e("strDateMonth",strDateMonth)
                }
            }
            btnSubmitOrder -> {
                val strMonthDelivery = textOf(etMonthDeliver)
                if(isEmptyRequired(strMonthDelivery)) return

                viewModel.hitSummary(strDateMonth,kodeDealer.id)
            }
        }
    }

}
