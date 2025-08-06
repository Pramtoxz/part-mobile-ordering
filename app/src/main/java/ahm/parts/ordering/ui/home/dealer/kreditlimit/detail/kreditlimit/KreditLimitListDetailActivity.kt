package ahm.parts.ordering.ui.home.dealer.kreditlimit.detail.kreditlimit

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dealer.kreditlimit.KreditLimit
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.home.dealer.kreditlimit.KreditLimitViewModel
import ahm.parts.ordering.ui.home.dealer.kreditlimit.adapter.KreditLimitDetailListAdapter
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_kredit_limit_detail_list.*

class KreditLimitListDetailActivity : BaseActivity<KreditLimitViewModel>(), View.OnClickListener {

    var dealerKredit = ""

    lateinit var kreditLimitAdapter : KreditLimitDetailListAdapter
    var kreditLimits = ArrayList<KreditLimit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kredit_limit_detail_list)

        dealerKredit = extra(Constants.BUNDLE.KODEDEALER)

        initUI()
        initListener()

        observeData()
        viewModel.hitCheckKreditLimitList(dealerKredit)
    }

    private fun initUI(){
        setToolbar(getString(R.string.lbl_title_kredit_limit_details),true)

        swipeRefresh.setOnRefreshListener {
            viewModel.hitCheckKreditLimitList(dealerKredit)
        }

        initAdapter()
    }

    private fun observeData(){
        viewModel.apiResponse.observe(this, Observer {
            showStateApiView(it){
                viewModel.hitCheckKreditLimitList(dealerKredit)
            }
        })

        viewModel.kreditLimits.observe(this, Observer {
            kreditLimits.clear()
            kreditLimits.addAll(it)

            kreditLimitAdapter.notifyDataSetChanged()
        })

    }

    private fun initAdapter(){
        kreditLimitAdapter = KreditLimitDetailListAdapter(kreditLimits,this){
            goTo<KreditLimitDetailActivity> {
                putExtra(Constants.BUNDLE.JSON,kreditLimits[it].getString())
            }
        }

        rvKreditLimit.initItem(this,kreditLimitAdapter)
    }

    private fun initListener(){

    }

    override fun onClick(v: View?) {
        when(v!!){

        }
    }

}
