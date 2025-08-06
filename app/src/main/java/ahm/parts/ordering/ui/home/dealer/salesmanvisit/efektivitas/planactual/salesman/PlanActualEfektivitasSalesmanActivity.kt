package ahm.parts.ordering.ui.home.dealer.salesmanvisit.efektivitas.planactual.salesman

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dealer.efektivitas.EfektivitasPlanActualParam
import ahm.parts.ordering.data.model.home.dealer.efektivitas.EfData
import ahm.parts.ordering.data.room.user.User
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.efektivitas.EfektivitasViewModel
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.efektivitas.adapter.EfektivitasPlanActualAdapter
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.efektivitas.planactual.detail.PlanActualEfektivitasDetailTabActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_efektivitas_visit_plan_actual.*
import kotlinx.android.synthetic.main.content_loading_list.*

class PlanActualEfektivitasSalesmanActivity : BaseActivity<EfektivitasViewModel>(), View.OnClickListener {

    var paging = 1

    var efektivitasParam = EfektivitasPlanActualParam()

    var planActuals = ArrayList<EfData>()
    lateinit var planActualAdapter : EfektivitasPlanActualAdapter

    var user : User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_efektivitas_visit_plan_actual)

        efektivitasParam = extra(Constants.BUNDLE.PARAM).getObject()

        initUI()
        initListener()

        viewModel.getUser().observe(this, Observer {
            user = it
            initAdapter()
            hitApi()
        })

        observeData()
    }

    private fun initUI(){
        setToolbar(getString(R.string.lbl_efektivitas),true)

        lLoadingView.hide()

    }

    private fun observeData(){
        viewModel.apiResponse.observe(this, Observer {
            showStateApiView(it,400){
                hitApi()
            }
        })

        viewModel.efektivitasPlanActualVisit.observe(this, Observer {

            it.apply {
                tvActual text "$actual • "
                tvTarget text "$target • "
                tvRealisasi text "$realisasi% • "
                tvEfektivitas text "$efectivitas%"
                tvAmountTotal text StringMasker().addRp(amountTotal)
            }

            lLoadingView.hide()

            planActuals.clear()
            planActuals.addAll(it.data)

            planActualAdapter.notifyDataSetChanged()
        })

        viewModel.efektivitasPlanActualVisitLoadMore.observe(this, Observer {

            planActuals.addAll(it.data)

            planActualAdapter.notifyDataSetChanged()
        })

    }

    private fun hitApi(){
        planActuals.clear()
        planActualAdapter.notifyDataSetChanged()

        paging = 1
        viewModel.hitEfektivitasPlanVisitSalesman(paging,efektivitasParam,false)
    }

    private fun initAdapter(){
        planActualAdapter = EfektivitasPlanActualAdapter(planActuals,this,user!!.id_role){
            goTo<PlanActualEfektivitasDetailTabActivity> {
                putExtra(Constants.BUNDLE.JSON,planActuals[it].getString())
                putExtra(Constants.BUNDLE.PARAM,efektivitasParam.getString())
            }
        }

        rvEfektivitas.initItem(this,planActualAdapter)

        rvEfektivitas.onEndScroll(false){
            paging += 1
            viewModel.hitEfektivitasPlanVisitSalesman(paging,efektivitasParam,true)
        }

    }

    private fun initListener(){
        rootView.lyTrans()
    }

    override fun onClick(v: View?) {
        when(v!!){

        }
    }

}
