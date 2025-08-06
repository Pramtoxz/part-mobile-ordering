package ahm.parts.ordering.ui.home.dealer.salesmanvisit.efektivitas.planactual.manager

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dealer.efektivitas.Coordinator
import ahm.parts.ordering.data.model.home.dealer.efektivitas.EfektivitasCoordinatorManager
import ahm.parts.ordering.data.model.home.dealer.efektivitas.EfektivitasPlanActualParam
import ahm.parts.ordering.data.model.home.dealer.efektivitas.Sales
import ahm.parts.ordering.data.room.user.User
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.efektivitas.EfektivitasViewModel
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.efektivitas.adapter.EfektivitasPlanActualSalesmanManagerAdapter
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.efektivitas.planactual.manager.detail.PlanActualManagerDetailActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_efektivitas_visit_plan_actual_manager.*
import kotlinx.android.synthetic.main.content_loading_list.*

class PlanActualEfektivitasSalesmanManagerActivity : BaseActivity<EfektivitasViewModel>(){

    var paging = 1

    var efektvitasPlanParam = EfektivitasPlanActualParam()
    var efektivitasCoordinatorManager = EfektivitasCoordinatorManager()
    var coordinator = Coordinator()

    var salesmans = ArrayList<Sales>()
    lateinit var salesmanAdapter : EfektivitasPlanActualSalesmanManagerAdapter

    var user : User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_efektivitas_visit_plan_actual_manager)

        efektvitasPlanParam = extra(Constants.BUNDLE.PARAM).getObject()
        coordinator = extra(Constants.BUNDLE.JSON).getObject()
        efektivitasCoordinatorManager = extra(Constants.BUNDLE.KOORDINATOR).getObject()

        initUI()

        viewModel.getUser().observe(this, Observer {
            user = it
            initAdapter()
        })
    }

    private fun initUI(){
        setToolbar(getString(R.string.lbl_title_plan_actual),true)

        rootView.lyTrans()

        tvHeaderDesc text getString(R.string.lbl_list_salesman)

        efektivitasCoordinatorManager.apply {
            tvActual text "$actual • "
            tvTarget text "$target • "
            tvRealisasi text "$realisasi% • "
            tvEfektivitas text "$efectivitas%"
            tvAmountTotal text StringMasker().addRp(amountTotal)
        }

        salesmans.addAll(coordinator.sales)

        lLoadingView.hide()
    }

    private fun initAdapter(){
        salesmanAdapter = EfektivitasPlanActualSalesmanManagerAdapter(salesmans,this,user!!.id_role){
            goTo<PlanActualManagerDetailActivity> {
                putExtra(Constants.BUNDLE.JSON,salesmans[it].getString())
                putExtra(Constants.BUNDLE.PARAM,efektvitasPlanParam.getString())
                putExtra(Constants.BUNDLE.SALESMAN,salesmans.getString())
                putExtra(Constants.BUNDLE.POSITION,it)
            }
        }

        rvEfektivitas.initItem(this,salesmanAdapter)

    }

}
