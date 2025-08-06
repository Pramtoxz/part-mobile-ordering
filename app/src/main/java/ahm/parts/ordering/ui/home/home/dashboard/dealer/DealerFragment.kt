package ahm.parts.ordering.ui.home.home.dashboard.dealer

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dashboard.partnumber.kodedealer.AllDealer
import ahm.parts.ordering.data.room.user.User
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseFragment
import ahm.parts.ordering.ui.home.HomeViewModel
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_dealer_category.*
import kotlinx.android.synthetic.main.content_loading_list.*

class DealerFragment : BaseFragment<HomeViewModel>(), View.OnClickListener {

    var monthNow = CalendarUtils.getCurrentDate("MMMM yyyy")

    var user : User? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dealer_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initListener()

        observeData()

        viewModel.getUser().observe(viewLifecycleOwner, Observer {
            try {
                user = it
                if(user!!.id_role == Constants.ROLE.DEALER){
                    session.dashboardDealerIdSelected = user!!.dealerId
                    etDealer text user!!.dealerName

                    viewModel.hitDashboardDealer(monthNow,session.dashboardDealerIdSelected)
                }else{
                    if(session.dashboardDealerIdSelected != ""){
                        viewModel.hitDashboardDealer(monthNow,session.dashboardDealerIdSelected)
                        etDealer text session.dashboardDealerNameSelected
                    }
                }
            } catch (e: Exception) {
            }
        })

    }

    private fun initUI() {
        lLoadingView.hide()

        val filter = IntentFilter(Constants.BROADCAST.DASHBOARD)
        activity!!.registerReceiver(calendarReciver, filter)

        rootView.lyTrans()
    }

    private fun observeData() {
        viewModel.apiResponse.observe(viewLifecycleOwner, Observer {
            showStateApiViewDealerDashboard(it, 400) {
                viewModel.hitDashboardDealer(monthNow,session.dashboardDealerIdSelected)
            }
        })

        /**
         * mendapatkan data dari api
         */
        viewModel.dashboardDealers.observe(viewLifecycleOwner, Observer {

            tvTotalTarget text StringMasker().addRp(it.target)
            tvTotalPencapaian text StringMasker().addRp(it.pencapaian)
            tvTotalOmzet text StringMasker().addRp(it.totalOmzet)
            tvTotalAverage text it.average
            tvTotalPencapaianCampaign text it.pencapaianCampaign
            tvTotalPointProgram text it.pointProgram + " Pts"

        })
    }

    private val calendarReciver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val bundle = intent.extras!!.getBundle(Constants.BUNDLE.CALENDAR)
            if (bundle != null) {

                val strCalendar = bundle.getString(Constants.BUNDLE.NAME)!!

                if(session.dashboardDealerIdSelected != ""){
                    monthNow = strCalendar
                    viewModel.hitDashboardDealer(monthNow,session.dashboardDealerIdSelected)
                }
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == Constants.REQUEST.KODE_DEALER && resultCode == Constants.RESULT.KODE_DEALER){
            val dealerSelected = data!!.extra(Constants.BUNDLE.JSON).getObject<AllDealer>()

            etDealer text dealerSelected!!.name

            session.dashboardDealerIdSelected = dealerSelected!!.id
            session.dashboardDealerNameSelected = dealerSelected!!.name

            viewModel.hitDashboardDealer(monthNow,session.dashboardDealerIdSelected)
        }
    }

    private fun initListener() {
        etDealer.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view!!){
            etDealer -> {
                if(user!!.id_role != Constants.ROLE.DEALER){
                    goTo<KodeDealerDashboardDealer>(requestCode = Constants.REQUEST.KODE_DEALER) {}
                }
            }
        }
    }

    fun newInstance(): DealerFragment {
        val fragment =
            DealerFragment()

        val args = Bundle()
        fragment.arguments = args

        return fragment
    }


}
