package ahm.parts.ordering.ui.home.dealer.salesmanvisit.realisasivisit.planactual.detail

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dealer.realisasivisit.RealisasiDetail
import ahm.parts.ordering.data.model.home.dealer.realisasivisit.Visit
import ahm.parts.ordering.helper.hide
import ahm.parts.ordering.helper.initItem
import ahm.parts.ordering.helper.show
import ahm.parts.ordering.ui.base.BaseFragment
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.realisasivisit.adapter.VisitDetailsAdapter
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.realisasivisit.planactual.PlanActualRealiasiViewModel
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer

import kotlinx.android.synthetic.main.fragment_realisasi_visit_plan_detail.*
import kotlin.collections.ArrayList

class PlanActualDetailRealisasiFragment : BaseFragment<PlanActualRealiasiViewModel>(){

    lateinit var mainActivity: PlanActualDetailRealisasiVisitTabActivity

    var coordinator = RealisasiDetail()
    var visits = ArrayList<Visit>()
    lateinit var visitAdapter: VisitDetailsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_realisasi_visit_plan_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()

    }

    private fun initUI() {

        visits.addAll(coordinator.visit)

        coordinator.apply {
            tvSalesName text salesmanName
            tvSalesKode text "$dealerCode • $dealerName"
            tvAddress text dealerAddress
            tvReport text "$plan Plan Visit • $actual Actual Visit \nRealisasi $realisasi%"
        }

        viewModel.getUser().observe(activity!!, Observer {
            when(it!!.id_role){
                Constants.ROLE.KOORDINATOR -> {
                    tvSalesKode.show()
                    tvAddress.show()
                }
                Constants.ROLE.SALESMAN -> {
                    tvSalesName.hide()
                }
            }
        })


        initAdapter()
    }


    private fun initAdapter() {
        visitAdapter = VisitDetailsAdapter(visits,mainActivity){}

        rvVisitDetails.initItem(activity!!,visitAdapter)
    }

    companion object {

        fun newInstance(
            position: Int,
            coordinator : RealisasiDetail,
            activity: PlanActualDetailRealisasiVisitTabActivity
        ): PlanActualDetailRealisasiFragment {
            val fragment = PlanActualDetailRealisasiFragment()

            fragment.mainActivity = activity
            fragment.coordinator = coordinator

            val args = Bundle()
            fragment.arguments = args

            return fragment
        }

    }

}
