package ahm.parts.ordering.ui.home.dealer.salesmanvisit.realisasivisit.planactual.koordinator

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dealer.realisasivisit.Data
import ahm.parts.ordering.data.model.home.dealer.realisasivisit.RealisasiVisitPlanActualParam
import ahm.parts.ordering.data.room.user.User
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.realisasivisit.adapter.RealisasiPlanActualAdapter
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.realisasivisit.planactual.PlanActualRealiasiViewModel
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.realisasivisit.planactual.detail.PlanActualDetailRealisasiVisitTabActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_realisasi_visit_plan_actual.*
import kotlinx.android.synthetic.main.content_loading_list.*

class PlanActualRealisasiKoordinatorActivity : BaseActivity<PlanActualRealiasiViewModel>(), View.OnClickListener {

    var paging = 1

    var paramActualVisit = RealisasiVisitPlanActualParam()

    var planActuals = ArrayList<Data>()
    lateinit var planActualAdapter : RealisasiPlanActualAdapter

    var user : User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_realisasi_visit_plan_actual)

        paramActualVisit = extra(Constants.BUNDLE.PARAM).getObject()

        initUI()
        initListener()

        observeData()
        hitApi()
    }

    private fun initUI(){
        setToolbar(getString(R.string.lbl_title_plan_actual),true)

        viewModel.getUser().observe(this, Observer {
            user = it
            initAdapter()
        })

        lLoadingView.hide()

    }

    private fun hitApi(){
        paging = 1
        viewModel.getRealisasiPlanVisitKoordinator(paging,paramActualVisit,false)
    }

    private fun observeData(){
        viewModel.apiResponse.observe(this, Observer {
            showStateApiView(it,400){
                hitApi()
            }
        })

        viewModel.realisasiPlanActualVisit.observe(this, Observer {

            it.apply {
                tvActual text actual +" • "
                tvTarget text target +" • "
                tvRealisasi text realisasi +"%"
            }

            lLoadingView.hide()

            planActuals.clear()
            planActuals.addAll(it.data)

            planActualAdapter.notifyDataSetChanged()
        })

        viewModel.realisasiPlanActualVisitLoadMore.observe(this, Observer {

            planActuals.addAll(it.data)

            planActualAdapter.notifyDataSetChanged()
        })

    }

    private fun initAdapter(){
        planActualAdapter = RealisasiPlanActualAdapter(planActuals,this,user!!.id_role){
            /*goTo<PlanActualDetailRealisasiVisitCoordinatorActivity> {
                putExtra(Constants.BUNDLE.JSON,planActuals[it].getString())
                putExtra(Constants.BUNDLE.PARAM,paramActualVisit.getString())
                putExtra(Constants.BUNDLE.LIST,planActuals.getString())
                putExtra(Constants.BUNDLE.POSITION,it)
            }*/
            goTo<PlanActualDetailRealisasiVisitTabActivity> {
                putExtra(Constants.BUNDLE.JSON,planActuals[it].getString())
                putExtra(Constants.BUNDLE.PARAM,paramActualVisit.getString())
                putExtra(Constants.BUNDLE.LIST,planActuals.getString())
                putExtra(Constants.BUNDLE.POSITION,it)
            }
        }

        rvRealisasiVisit.initItem(this,planActualAdapter)

        rvRealisasiVisit.onEndScroll(false){
            paging += 1
            viewModel.getRealisasiPlanVisitKoordinator(paging,paramActualVisit,true)
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
