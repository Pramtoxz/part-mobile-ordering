package ahm.parts.ordering.ui.home.home

import ahm.parts.ordering.R
import ahm.parts.ordering.api.ApiStatus
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dashboard.CheckoutDashboard
import ahm.parts.ordering.data.model.home.dashboard.partnumber.kodedealer.AllDealer
import ahm.parts.ordering.data.room.user.User
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.helper.dialog.DialogHelper
import ahm.parts.ordering.ui.base.BaseFragment
import ahm.parts.ordering.ui.home.HomeActivity
import ahm.parts.ordering.ui.home.HomeViewModel
import ahm.parts.ordering.ui.home.home.campaignpromo.CampaignPromoActivity
import ahm.parts.ordering.ui.home.home.dashboard.dealer.DealerFragment
import ahm.parts.ordering.ui.home.home.dashboard.salesman.SalesmanFragment
import ahm.parts.ordering.ui.home.home.ordersugestion.ordersugestion.SuggestOrderActivity
import ahm.parts.ordering.ui.home.home.partnumber.kodedealer.KodeDealerActivity
import ahm.parts.ordering.ui.home.home.partnumber.searchpart.SearchPartActivity
import ahm.parts.ordering.ui.home.notification.NotificationActivity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_home.*
import java.lang.Exception

class HomeFragment : BaseFragment<HomeViewModel>() {

    lateinit var mainActivity : HomeActivity

    var monthNow = CalendarUtils.getCurrentDate("MMMM yyyy")

    var checkoutDashboard : CheckoutDashboard? = null

    var user : User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initListener()

        observeData()
    }

    fun initUI() {
        mainActivity.roleFiturMenu()

        tvMonthNow text monthNow

        openPage(SalesmanFragment().newInstance())

        viewModel.getUser().observe(viewLifecycleOwner, Observer {
            user = it
            try {
                when(user!!.id_role){
                    Constants.ROLE.DEALER -> {
                        btnSalesman.hide()

                        btnDealer.setBackgroundResource(R.drawable.bg_red_category_enable_home)
                        tvDealer.setTextColor(resources.getColor(R.color.white))

                        openPage(DealerFragment().newInstance())
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })
        try {
            val filter = IntentFilter(Constants.BROADCAST.DASHBOARD_CHECKOUT)
            activity!!.registerReceiver(checkoutReciver, filter)
        } catch (e: Exception) {
        }
    }

    private fun observeData(){
        viewModel.apiResponse.observe(viewLifecycleOwner, Observer {
            if (it.status == ApiStatus.LOADING) {
                if(it.flagLoadingDialog){
                    loadingDialog.show(R.string.lbl_loading_harap_tunggu)
                }
            } else {
                if(it.flagLoadingDialog){
                    loadingDialog.dismiss()
                    when(it.status){
                        ApiStatus.SUCCESS -> {
                            snacked(rootView, it.message[0].toString())
                            vCheckout.hide()
                        }
                        else -> {
                            snacked(rootView, it)
                        }
                    }
                }
            }
        })

        viewModel.checkoutViewDashboard.observe(viewLifecycleOwner, Observer {
            checkoutDashboard = it

            tvDealerCheckout text checkoutDashboard?.codeDealer + " - " + checkoutDashboard?.dealerName

            vCheckout.show()
        })
    }

    private fun dialogCalendar() {

        var monthSelected = tvMonthNow.text.toString()

        val dialogCalendar = activity!!.initDialog(R.layout.dialog_bottom_calendar_home_wheel,true,Gravity.BOTTOM)

        val wheel = dialogCalendar.findViewById<WheelView>(R.id.wheel)
        val btnViewDashboard = dialogCalendar.findViewById<Button>(R.id.btnViewDashboard)!!
        val vTop = dialogCalendar.findViewById<View>(R.id.vTop)!!

        vTop.onClick {
            dialogCalendar.dismiss()
        }

        val months = ArrayList<String>()
        months.add(getString(R.string.month_januari) + CalendarUtils.thisYear())
        months.add(getString(R.string.month_februari) + CalendarUtils.thisYear())
        months.add(getString(R.string.month_maret) + CalendarUtils.thisYear())
        months.add(getString(R.string.month_april) + CalendarUtils.thisYear())
        months.add(getString(R.string.month_mei) + CalendarUtils.thisYear())
        months.add(getString(R.string.month_juni) + CalendarUtils.thisYear())
        months.add(getString(R.string.month_juli) +CalendarUtils.thisYear())
        months.add(getString(R.string.month_agustus) + CalendarUtils.thisYear())
        months.add(getString(R.string.month_september) + CalendarUtils.thisYear())
        months.add(getString(R.string.month_oktober) + CalendarUtils.thisYear())
        months.add(getString(R.string.month_november) + CalendarUtils.thisYear())
        months.add(getString(R.string.month_desember) + CalendarUtils.thisYear())

        wheel.setItems(months)
        wheel.setSeletion((CalendarUtils.setFormatDate(monthSelected,"MMMM yyyy","M").toInt() - 1))
        wheel.onWheelViewListener = object : WheelView.OnWheelViewListener() {
            override fun onSelected(selectedIndex: Int, item: String) {
                monthSelected = item
            }
        }

        btnViewDashboard.onClick {
            dialogCalendar.dismiss()
            activity!!.tvMonthNow text monthSelected

            sendBroadcast(monthSelected)
        }
    }

    private fun sendBroadcast(calendar: String) {
        val intent = Intent(Constants.BROADCAST.DASHBOARD)
        val bundle = Bundle()
        bundle.putString(Constants.BUNDLE.NAME, calendar)
        intent.putExtra(Constants.BUNDLE.CALENDAR, bundle)
        activity!!.sendBroadcast(intent)
    }

    private val checkoutReciver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            viewModel.getCheckoutViewDashboard()
        }
    }

    private fun initListener() {
        btnPartNumber.setOnClickListener(this)
        btnCampaignPromo.setOnClickListener(this)
        vDate.setOnClickListener(this)
        btnSalesman.setOnClickListener(this)
        btnDealer.setOnClickListener(this)
        btnOrderSugestion.setOnClickListener(this)
        ivNotification.setOnClickListener(this)
        btnCheckOut.setOnClickListener(this)
    }

    private fun dialogCheckout(){
        activity!!.bottomSheet(R.layout.dialog_bottom_visit_add_confirmation) {
            val btnNo = it.findViewById<Button>(R.id.btnNo)!!
            val btnCheckout = it.findViewById<Button>(R.id.btnCheckout)!!

            val tvDialogTitle = it.findViewById<TextView>(R.id.tvDialogTitle)!!
            val tvDialogMessage = it.findViewById<TextView>(R.id.tvDialogMessage)!!

            tvDialogTitle text getString(R.string.lbl_dialog_title_checkout)
            tvDialogMessage text getString(R.string.lbl_dialog_message_checkout)

            btnNo.onClick { this.dismiss() }

            btnCheckout.onClick {
                this.dismiss()
                viewModel.hitCheckoutDashboard(checkoutDashboard!!.codeVisit)
            }

        }
    }

    override fun onClick(v: View?) {
        when (v) {
            btnPartNumber -> {
                when(user!!.id_role){
                    Constants.ROLE.NONCHANNEL -> {
                        goTo<SearchPartActivity> {
                            val dealer = AllDealer(id = user!!.dealerId,name = user!!.dealerName,code = user!!.dealerCode)
                            putExtra(Constants.BUNDLE.JSON, dealer.getString())
                        }
                    }
                    Constants.ROLE.DEALER -> {
                        goTo<SearchPartActivity> {
                            val dealer = AllDealer(id = user!!.dealerId,name = user!!.dealerName,code = user!!.dealerCode)
                            putExtra(Constants.BUNDLE.JSON, dealer.getString())
                        }
                    }
                    else -> {
                        goTo<KodeDealerActivity> { }
                    }
                }
            }
            btnCampaignPromo -> {
                goTo<CampaignPromoActivity> { }
            }
            btnOrderSugestion -> {
                when(user!!.id_role){
                    Constants.ROLE.DEALER -> {
                        goTo<SuggestOrderActivity> {
                            val dealer = AllDealer(id = user!!.dealerId,name = user!!.dealerName,code = user!!.dealerCode)
                            putExtra(Constants.BUNDLE.JSON,dealer.getString())
                        }
                    }
                    else -> {
                        goTo<KodeDealerOrderSuggestionActivity> { }
                    }
                }

            }
            ivNotification -> {
                goTo<NotificationActivity> { }
            }
            vDate -> {
                dialogCalendar()
            }
            btnSalesman -> {
                btnSalesman.setBackgroundResource(R.drawable.bg_red_category_enable_home)
                btnDealer.setBackgroundResource(R.drawable.bg_grey_category_disable_home)

                tvSalesman.setTextColor(resources.getColor(R.color.white))
                tvDealer.setTextColor(resources.getColor(R.color.txt_gray))

                openPage(SalesmanFragment().newInstance())
            }
            btnDealer -> {
                btnDealer.setBackgroundResource(R.drawable.bg_red_category_enable_home)
                btnSalesman.setBackgroundResource(R.drawable.bg_grey_category_disable_home)

                tvSalesman.setTextColor(resources.getColor(R.color.txt_gray))
                tvDealer.setTextColor(resources.getColor(R.color.white))

                openPage(DealerFragment().newInstance())
            }
            btnCheckOut -> {
                dialogCheckout()
            }
        }
        super.onClick(v)
    }

    private fun openPage(fragment: Fragment) {
        activity!!.supportFragmentManager.beginTransaction().replace(R.id.flContent, fragment)
            .commit()
    }

    companion object {
        @JvmStatic
        fun newInstance(activity: HomeActivity) = HomeFragment().apply {
            this.mainActivity = activity
        }
    }
}