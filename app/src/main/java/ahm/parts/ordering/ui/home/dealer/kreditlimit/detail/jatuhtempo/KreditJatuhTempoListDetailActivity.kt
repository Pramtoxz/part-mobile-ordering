package ahm.parts.ordering.ui.home.dealer.kreditlimit.detail.jatuhtempo

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dealer.kreditlimit.KreditJatuhTempo
import ahm.parts.ordering.data.model.home.dealer.kreditlimit.KreditLimit
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.home.dealer.kreditlimit.KreditLimitViewModel
import ahm.parts.ordering.ui.home.dealer.kreditlimit.adapter.KreditJatuhTempoListAdapter
import ahm.parts.ordering.ui.home.dealer.kreditlimit.adapter.KreditLimitDetailListAdapter
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_kredit_limit_detail_list.*
import org.json.JSONArray
import org.json.JSONObject

class KreditJatuhTempoListDetailActivity : BaseActivity<KreditLimitViewModel>(), View.OnClickListener {

    var dealerKredit = ""

    lateinit var kreditJatuhTempoAdapter : KreditJatuhTempoListAdapter
    var kreditLimits = ArrayList<KreditJatuhTempo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kredit_limit_detail_list)

        dealerKredit = extra(Constants.BUNDLE.KODEDEALER)

        initUI()
        initListener()

        observeData()
        viewModel.hitKreditJatuhTempoList(dealerKredit)
    }

    private fun initUI(){
        setToolbar(getString(R.string.lbl_title_credit_ovedue_details),true)

        swipeRefresh.setOnRefreshListener {
            viewModel.hitKreditJatuhTempoList(dealerKredit)
        }

        initAdapter()
    }

    private fun observeData(){
        viewModel.apiResponse.observe(this, Observer {
            showStateApiView(it){
                viewModel.hitKreditJatuhTempoList(dealerKredit)
            }
        })

        viewModel.kreditJatuhTempos.observe(this, Observer {
            kreditLimits.clear()
            kreditLimits.addAll(it)

            kreditJatuhTempoAdapter.notifyDataSetChanged()
        })

    }

    private fun initAdapter(){
        kreditJatuhTempoAdapter = KreditJatuhTempoListAdapter(kreditLimits,this){
            goTo<KreditJatuhTempoDetailActivity> {
                putExtra(Constants.BUNDLE.JSON,kreditLimits[it].getString())
                putExtra(Constants.BUNDLE.TOOLBAR,getString(R.string.lbl_title_credit_ovedue_details))
            }
        }

        rvKreditLimit.initItem(this,kreditJatuhTempoAdapter)
    }


    private fun initListener(){

    }

    override fun onClick(v: View?) {
        when(v!!){

        }
    }

}
