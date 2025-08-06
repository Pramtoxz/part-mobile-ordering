package ahm.parts.ordering.ui.home.dealer.salesmanvisit.realisasivisit.planactual.manager

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dealer.realisasivisit.Coordinator
import ahm.parts.ordering.data.model.home.dealer.realisasivisit.RealisasiCoordinatorManager
import ahm.parts.ordering.data.model.home.dealer.realisasivisit.RealisasiVisitPlanActualParam
import ahm.parts.ordering.data.room.user.User
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.realisasivisit.adapter.RealisasiPlanActualCoordinatorManagerAdapter
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.realisasivisit.planactual.PlanActualRealiasiViewModel
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_realisasi_visit_plan_actual_manager.*
import kotlinx.android.synthetic.main.content_loading_list.*

class PlanActualRealisasiCoordinatorManagerActivity : BaseActivity<PlanActualRealiasiViewModel>(){

    var paging = 1

    var realisasiPlanParam = RealisasiVisitPlanActualParam()
    var realisasiCoordinatorManager = RealisasiCoordinatorManager()

    var planActualCoordinators = ArrayList<Coordinator>()
    lateinit var planActualCoordinatorAdapter : RealisasiPlanActualCoordinatorManagerAdapter

    var user : User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_realisasi_visit_plan_actual_manager)

        realisasiPlanParam = extra(Constants.BUNDLE.PARAM).getObject()

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
        setToolbar(getString(R.string.lbl_title_plan_actual),true)

        lLoadingView.hide()
    }

    private fun hitApi(){
        paging = 1
        viewModel.getRealisasiPlanVisitCoordinatorManager(paging,realisasiPlanParam,false)
    }

    private fun observeData(){
        viewModel.apiResponse.observe(this, Observer {
            showStateApiView(it,400){
                hitApi()
            }
        })

        viewModel.realisasiPlanActualVisitManager.observe(this, Observer {

            realisasiCoordinatorManager = it

            it.apply {
                tvActual text "$actual • "
                tvTarget text "$target • "
                tvRealisasi text "$realisasi%"
            }

            lLoadingView.hide()

            planActualCoordinators.clear()
            planActualCoordinators.addAll(it.data)

            planActualCoordinatorAdapter.notifyDataSetChanged()
        })

        viewModel.realisasiPlanActualVisitManagerLoadMore.observe(this, Observer {

            planActualCoordinators.addAll(it.data)

            planActualCoordinatorAdapter.notifyDataSetChanged()
        })

    }

    private fun initAdapter(){
        planActualCoordinatorAdapter = RealisasiPlanActualCoordinatorManagerAdapter(planActualCoordinators,this,user!!.id_role){
            goTo<PlanActualRealisasiSalesmanManagerActivity> {
                putExtra(Constants.BUNDLE.JSON,planActualCoordinators[it].getString())
                putExtra(Constants.BUNDLE.KOORDINATOR,realisasiCoordinatorManager.getString())
                putExtra(Constants.BUNDLE.PARAM,realisasiPlanParam.getString())
            }
        }

        rvRealisasiVisit.initItem(this,planActualCoordinatorAdapter)

        rvRealisasiVisit.onEndScroll(false){
            paging += 1
            viewModel.getRealisasiPlanVisitCoordinatorManager(paging,realisasiPlanParam,true)
        }
    }

    private fun initListener(){
        rootView.lyTrans()
    }

}
