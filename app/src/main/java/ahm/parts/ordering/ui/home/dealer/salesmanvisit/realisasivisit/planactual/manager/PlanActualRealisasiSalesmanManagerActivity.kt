package ahm.parts.ordering.ui.home.dealer.salesmanvisit.realisasivisit.planactual.manager

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dealer.realisasivisit.*
import ahm.parts.ordering.data.room.user.User
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.realisasivisit.adapter.RealisasiPlanActualSalesmanManagerAdapter
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.realisasivisit.planactual.PlanActualRealiasiViewModel
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.realisasivisit.planactual.manager.detail.PlanActualManagerDetailActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_realisasi_visit_plan_actual_manager.*
import kotlinx.android.synthetic.main.content_loading_list.*

class PlanActualRealisasiSalesmanManagerActivity : BaseActivity<PlanActualRealiasiViewModel>(), View.OnClickListener {

    var paging = 1

    var realisasiPlanParam = RealisasiVisitPlanActualParam()
    var realisasiCoordinatorManager = RealisasiCoordinatorManager()
    var coordinator = Coordinator()

    var salesmans = ArrayList<Sales>()
    lateinit var salesmanAdapter : RealisasiPlanActualSalesmanManagerAdapter

    var user : User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_realisasi_visit_plan_actual_manager)

        realisasiPlanParam = extra(Constants.BUNDLE.PARAM).getObject()
        coordinator = extra(Constants.BUNDLE.JSON).getObject()
        realisasiCoordinatorManager = extra(Constants.BUNDLE.KOORDINATOR).getObject()

        initUI()
        initListener()

        viewModel.getUser().observe(this, Observer {
            user = it
            initAdapter()
        })
    }

    private fun initUI(){
        setToolbar(getString(R.string.lbl_title_plan_actual),true)

        tvHeaderDesc text getString(R.string.lbl_list_salesman)

        realisasiCoordinatorManager.apply {
            tvActual text "$actual • "
            tvTarget text "$target • "
            tvRealisasi text "$realisasi%"
        }

        salesmans.addAll(coordinator.sales)

        lLoadingView.hide()
    }

    private fun initAdapter(){
        salesmanAdapter = RealisasiPlanActualSalesmanManagerAdapter(salesmans,this,user!!.id_role){
            goTo<PlanActualManagerDetailActivity> {
                putExtra(Constants.BUNDLE.JSON,salesmans[it].getString())
                putExtra(Constants.BUNDLE.PARAM,realisasiPlanParam.getString())
                putExtra(Constants.BUNDLE.SALESMAN,salesmans.getString())
                putExtra(Constants.BUNDLE.POSITION,it)
            }
        }

        rvRealisasiVisit.initItem(this,salesmanAdapter)
    }

    private fun initListener(){
        rootView.lyTrans()
    }

    override fun onClick(v: View?) {
        when(v!!){

        }
    }

}
