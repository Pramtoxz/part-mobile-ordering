package ahm.parts.ordering.ui.home.dealer.salesmanvisit.realisasivisit.planactual.manager.detail

import ahm.parts.ordering.R
import ahm.parts.ordering.data.model.home.dealer.realisasivisit.Sales
import ahm.parts.ordering.data.model.home.dealer.realisasivisit.SalesDetail
import ahm.parts.ordering.data.model.home.dealer.realisasivisit.VisitSalesman
import ahm.parts.ordering.helper.extra
import ahm.parts.ordering.helper.initItem
import ahm.parts.ordering.ui.base.BaseFragment
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.realisasivisit.adapter.VisitDetailsManagerAdapter
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.realisasivisit.planactual.PlanActualRealiasiViewModel
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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager

import kotlinx.android.synthetic.main.fragment_realisasi_visit_plan_detail.*
import retrofit2.HttpException
import java.lang.reflect.Field
import java.util.*
import kotlin.collections.ArrayList

class PlanActualManagerDetailFragment : BaseFragment<PlanActualRealiasiViewModel>(), View.OnClickListener {

    lateinit var mainActivity: PlanActualManagerDetailActivity

    var sales = SalesDetail()
    var visits = ArrayList<VisitSalesman>()
    lateinit var visitAdapter: VisitDetailsManagerAdapter

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

        visits.addAll(sales.visit)

        sales.apply {
            tvSalesName text salesmanName
            tvSalesKode text "$dealerCode • $dealerName"
            tvAddress text dealerAddress
            tvReport text "$plan Plan Visit • $actual Actual Visit \nRealisasi $realisasi%"
        }

        initAdapter()
    }


    private fun initAdapter() {
        visitAdapter = VisitDetailsManagerAdapter(visits,mainActivity){}

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
            sales : SalesDetail,
            activity: PlanActualManagerDetailActivity
        ): PlanActualManagerDetailFragment {
            val fragment = PlanActualManagerDetailFragment()

            fragment.mainActivity = activity
            fragment.sales = sales

            val args = Bundle()
            fragment.arguments = args

            return fragment
        }

    }

}
