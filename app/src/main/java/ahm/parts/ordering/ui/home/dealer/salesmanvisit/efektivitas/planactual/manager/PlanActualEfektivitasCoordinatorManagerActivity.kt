package ahm.parts.ordering.ui.home.dealer.salesmanvisit.efektivitas.planactual.manager

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dealer.efektivitas.Coordinator
import ahm.parts.ordering.data.model.home.dealer.efektivitas.EfektivitasCoordinatorManager
import ahm.parts.ordering.data.model.home.dealer.efektivitas.EfektivitasPlanActualParam
import ahm.parts.ordering.data.room.user.User
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.efektivitas.EfektivitasViewModel
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.efektivitas.adapter.EfektivitasPlanActualCoordinatorManagerAdapter
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_efektivitas_visit_plan_actual_manager.*
import kotlinx.android.synthetic.main.content_loading_list.*

class PlanActualEfektivitasCoordinatorManagerActivity : BaseActivity<EfektivitasViewModel>(), View.OnClickListener {

    var paging = 1

    var efektivitasPlanParam = EfektivitasPlanActualParam()
    var efektivitasCoordinatorManager = EfektivitasCoordinatorManager()

    var planActualCoordinators = ArrayList<Coordinator>()
    lateinit var planActualCoordinatorAdapter : EfektivitasPlanActualCoordinatorManagerAdapter

    var user : User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_efektivitas_visit_plan_actual_manager)

        efektivitasPlanParam = extra(Constants.BUNDLE.PARAM).getObject()

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

        tvHeaderDesc text getString(R.string.lbl_list_koordinator)

        lLoadingView.hide()
    }

    private fun hitApi(){
        paging = 1
        viewModel.getEfektivitasPlanVisitCoordinatorManager(paging,efektivitasPlanParam,false)
    }

    private fun observeData(){
        viewModel.apiResponse.observe(this, Observer {
            showStateApiView(it,400){
                hitApi()
            }
        })

        viewModel.efektivitasPlanActualVisitManager.observe(this, Observer {

            efektivitasCoordinatorManager = it

            it.apply {
                tvActual text "$actual • "
                tvTarget text "$target • "
                tvRealisasi text "$realisasi% • "
                tvEfektivitas text "$efectivitas%"
                tvAmountTotal text StringMasker().addRp(amountTotal)
            }

            lLoadingView.hide()

            planActualCoordinators.clear()
            planActualCoordinators.addAll(it.data)

            planActualCoordinatorAdapter.notifyDataSetChanged()
        })

        viewModel.efektivitasPlanActualVisitManagerLoadMore.observe(this, Observer {

            planActualCoordinators.addAll(it.data)

            planActualCoordinatorAdapter.notifyDataSetChanged()
        })

    }

    private fun initAdapter(){
        planActualCoordinatorAdapter = EfektivitasPlanActualCoordinatorManagerAdapter(planActualCoordinators,this,user!!.id_role){
            goTo<PlanActualEfektivitasSalesmanManagerActivity> {
                putExtra(Constants.BUNDLE.JSON,planActualCoordinators[it].getString())
                putExtra(Constants.BUNDLE.KOORDINATOR,efektivitasCoordinatorManager.getString())
                putExtra(Constants.BUNDLE.PARAM,efektivitasPlanParam.getString())
            }
        }

        rvEfektivitas.initItem(this,planActualCoordinatorAdapter)

        rvEfektivitas.onEndScroll(false){
            paging += 1
            viewModel.getEfektivitasPlanVisitCoordinatorManager(paging,efektivitasPlanParam,true)
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
