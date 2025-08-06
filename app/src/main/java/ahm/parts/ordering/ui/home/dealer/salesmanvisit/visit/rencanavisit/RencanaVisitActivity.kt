package ahm.parts.ordering.ui.home.dealer.salesmanvisit.visit.rencanavisit

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dealer.salemanvisit.RencanaVisit
import ahm.parts.ordering.data.model.home.dashboard.partnumber.kodedealer.AllDealer
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.visit.VisitViewModel
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_rencana_visit.*

class RencanaVisitActivity : BaseActivity<VisitViewModel>(), View.OnClickListener {

    lateinit var kodeDealer : AllDealer

    var rencanaVisits = ArrayList<RencanaVisit>()
    lateinit var rencanaVisitAdapter : RencanaVisitAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rencana_visit)

        kodeDealer = extra(Constants.BUNDLE.JSON).getObject()

        initUI()
        initListener()

        observeData()
        viewModel.hitRencanaVisit("${kodeDealer.id}")
    }

    private fun initUI(){
        setToolbar(getString(R.string.lbl_title_rencana_visit),true)

        swipeRefresh.setOnRefreshListener {
            viewModel.hitRencanaVisit("${kodeDealer.id}")
        }

        initAdapter()
    }

    private fun observeData(){
        viewModel.apiResponse.observe(this, Observer {
            showStateApiView(it){
                viewModel.hitRencanaVisit("${kodeDealer.id}")
            }
        })

        viewModel.rencanaVisits.observe(this, Observer {
            rencanaVisits.clear()
            rencanaVisits.addAll(it)

            rencanaVisitAdapter.notifyDataSetChanged()
        })

    }

    private fun initAdapter(){
        rencanaVisitAdapter = RencanaVisitAdapter(rencanaVisits,this){
            setResult(Constants.RESULT.RENCANA_VISIT){
                putExtra(Constants.BUNDLE.JSON,rencanaVisits[it].getString())
            }
        }

        rvVisit.initItem(this,rencanaVisitAdapter)
    }

    private fun initListener(){
    }

    override fun onClick(v: View?) {
        when(v!!){

        }
    }

}
