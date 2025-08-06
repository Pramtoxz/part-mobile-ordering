package ahm.parts.ordering.ui.home.home.partnumber.cart.summary.result

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dashboard.partnumber.cart.Cart
import ahm.parts.ordering.data.model.home.dashboard.partnumber.cart.summary.Summary
import ahm.parts.ordering.helper.StringMasker
import ahm.parts.ordering.helper.extra
import ahm.parts.ordering.helper.getObject
import ahm.parts.ordering.helper.goTo
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.home.HomeActivity
import ahm.parts.ordering.ui.home.HomeViewModel
import ahm.parts.ordering.ui.home.home.partnumber.cart.CartViewModel
import android.content.Intent
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_part_number_summary_result.*

class SummaryResultActivity : BaseActivity<CartViewModel>(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_part_number_summary_result)

        initUI()
        initListener()

    }

    private fun initUI() {
        setToolbar(getString(R.string.lbl_title_order_selesai), false)

        val cart = extra(Constants.BUNDLE.PARTSEARCH.JSON_CART).getObject<Cart>()

        extra(Constants.BUNDLE.JSON).getObject<Summary>().apply {
            tvNoOrder text codeOrder
            tvItemPembelian text itemPembelian
            tvTotalPembelian text totalPembelian
            tvMonthDeliver text monthDeliver

            try {
                tvDiscount text StringMasker().addRp(cart.discount.toDouble())
            } catch (e: Exception) {
            }

        }
    }

    private fun initListener() {
        btnNoOrder.setOnClickListener(this)
        btnOrder.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!) {
            btnOrder -> {
                val intent = Intent()
                setResult(Constants.RESULT.PART_NUMBER_SUMMARY, intent)
                finish()
            }
            btnNoOrder -> {
                goTo<HomeActivity> { }
                finishAffinity()
            }
        }
    }

}
