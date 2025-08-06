package ahm.parts.ordering.ui.home.dealer.salesmanvisit.efektivitas.planactual.detail

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dealer.efektivitas.EfData
import ahm.parts.ordering.data.model.home.dealer.efektivitas.EfDetail
import ahm.parts.ordering.data.model.home.dealer.efektivitas.EfVisit
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseFragment
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.efektivitas.EfektivitasViewModel
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.efektivitas.adapter.VisitDetailsAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager

import kotlinx.android.synthetic.main.fragment_realisasi_visit_plan_detail.*
import retrofit2.HttpException
import java.lang.reflect.Field
import java.util.*
import kotlin.collections.ArrayList

class PlanActualEfektivitasDetailFragment : BaseFragment<EfektivitasViewModel>(), View.OnClickListener {

    lateinit var mainActivity: PlanActualEfektivitasDetailTabActivity

    var coordinator = EfDetail()
    var visits = ArrayList<EfVisit>()
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
        initListener()

    }

    private fun initUI() {

        visits.addAll(coordinator.visit)

        coordinator.apply {
            tvSalesName text salesmanName
            tvSalesKode text "$dealerCode • $dealerName"
            tvAddress text dealerAddress
            tvReport text plan + " Plan Visit • "+ actual + " Actual Visit " +  "\nRealisasi " + realisasi + "% • Efektivitas " + efectivitas + "%\nOrder " + StringMasker().addRp(order)
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

    private fun initListener() {
        // btnRefreshTrouble.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view!!.id) {

        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {

        fun newInstance(
            position: Int,
            coordinator : EfDetail,
            activity: PlanActualEfektivitasDetailTabActivity
        ): PlanActualEfektivitasDetailFragment {
            val fragment = PlanActualEfektivitasDetailFragment()

            fragment.mainActivity = activity
            fragment.coordinator = coordinator

            val args = Bundle()
            fragment.arguments = args

            return fragment
        }

    }

}
