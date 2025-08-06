package ahm.parts.ordering.ui.home.home

import ahm.parts.ordering.R
import ahm.parts.ordering.api.ApiStatus
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dashboard.CheckoutDashboard
import ahm.parts.ordering.data.model.home.dashboard.partnumber.kodedealer.AllDealer
import ahm.parts.ordering.data.room.user.User
import ahm.parts.ordering.databinding.FragmentHomeBinding // LANGKAH 1: Impor kelas Binding
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
// HAPUS: import kotlinx.android.synthetic.main.fragment_home.* // LANGKAH 2: Impor sintetik sudah dihapus
import java.lang.Exception
import ahm.parts.ordering.ui.home.home.ordersugestion.dealer.KodeDealerOrderSuggestionActivity
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator
import com.whiteelephant.monthpicker.WheelView

class HomeFragment : BaseFragment<HomeViewModel>() {

    lateinit var mainActivity : HomeActivity
    var monthNow = CalendarUtils.getCurrentDate("MMMM yyyy")
    var checkoutDashboard : CheckoutDashboard? = null
    var user : User? = null

    // LANGKAH 3: Deklarasikan variabel binding untuk Fragment
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // LANGKAH 4: Gunakan binding untuk inflate layout
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initListener()
        observeData()
    }

    fun initUI() {
        mainActivity.roleFiturMenu()

        binding.tvMonthNow.text = monthNow

        openPage(SalesmanFragment().newInstance())

        viewModel.getUser().observe(viewLifecycleOwner, Observer {
            user = it
            try {
                when(user!!.id_role){
                    Constants.ROLE.DEALER -> {
                        binding.btnSalesman.hide()
                        binding.btnDealer.setBackgroundResource(R.drawable.bg_red_category_enable_home)
                        binding.tvDealer.setTextColor(resources.getColor(R.color.white))

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
                            snacked(binding.root, it.message[0].toString())
                            binding.vCheckout.hide()
                        }
                        else -> {
                            snacked(binding.root, it)
                        }
                    }
                }
            }
        })

        viewModel.checkoutViewDashboard.observe(viewLifecycleOwner, Observer {
            checkoutDashboard = it
            binding.tvDealerCheckout.text = checkoutDashboard?.codeDealer + " - " + checkoutDashboard?.dealerName
            binding.vCheckout.show()
        })
    }

    private fun dialogCalendar() {
        var monthSelected = binding.tvMonthNow.text.toString()
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
            binding.tvMonthNow.text = monthSelected
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
        binding.btnPartNumber.setOnClickListener { onClick(it) }
        binding.btnCampaignPromo.setOnClickListener { onClick(it) }
        binding.vDate.setOnClickListener { onClick(it) }
        binding.btnSalesman.setOnClickListener { onClick(it) }
        binding.btnDealer.setOnClickListener { onClick(it) }
        binding.btnOrderSugestion.setOnClickListener { onClick(it) }
        binding.ivNotification.setOnClickListener { onClick(it) }
        binding.btnCheckOut.setOnClickListener { onClick(it) }
    }

    private fun dialogCheckout(){
        activity!!.bottomSheet(R.layout.dialog_bottom_visit_add_confirmation) {
            val btnNo = it.findViewById<Button>(R.id.btnNo)!!
            val btnCheckout = it.findViewById<Button>(R.id.btnCheckout)!!
            val tvDialogTitle = it.findViewById<TextView>(R.id.tvDialogTitle)!!
            val tvDialogMessage = it.findViewById<TextView>(R.id.tvDialogMessage)!!

            tvDialogTitle.text = getString(R.string.lbl_dialog_title_checkout)
            tvDialogMessage.text = getString(R.string.lbl_dialog_message_checkout)

            btnNo.onClick { this.dismiss() }

            btnCheckout.onClick {
                this.dismiss()
                viewModel.hitCheckoutDashboard(checkoutDashboard!!.codeVisit)
            }
        }
    }

    fun onClick(v: View) {
        when (v) {
            binding.btnPartNumber -> {
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
            binding.btnCampaignPromo -> {
                goTo<CampaignPromoActivity> { }
            }
            binding.btnOrderSugestion -> {
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
            binding.ivNotification -> {
                goTo<NotificationActivity> { }
            }
            binding.vDate -> {
                dialogCalendar()
            }
            binding.btnSalesman -> {
                binding.btnSalesman.setBackgroundResource(R.drawable.bg_red_category_enable_home)
                binding.btnDealer.setBackgroundResource(R.drawable.bg_grey_category_disable_home)
                binding.tvSalesman.setTextColor(resources.getColor(R.color.white))
                binding.tvDealer.setTextColor(resources.getColor(R.color.txt_gray))
                openPage(SalesmanFragment().newInstance())
            }
            binding.btnDealer -> {
                binding.btnDealer.setBackgroundResource(R.drawable.bg_red_category_enable_home)
                binding.btnSalesman.setBackgroundResource(R.drawable.bg_grey_category_disable_home)
                binding.tvSalesman.setTextColor(resources.getColor(R.color.txt_gray))
                binding.tvDealer.setTextColor(resources.getColor(R.color.white))
                openPage(DealerFragment().newInstance())
            }
            binding.btnCheckOut -> {
                dialogCheckout()
            }
        }
    }

    private fun openPage(fragment: Fragment) {
        activity!!.supportFragmentManager.beginTransaction().replace(binding.flContent.id, fragment)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(activity: HomeActivity) = HomeFragment().apply {
            this.mainActivity = activity
        }
    }
}