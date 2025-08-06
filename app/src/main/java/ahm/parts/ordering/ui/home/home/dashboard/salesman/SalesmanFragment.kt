package ahm.parts.ordering.ui.home.home.dashboard.salesman

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dashboard.SalesManRank
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseFragment
import ahm.parts.ordering.ui.base.adapter.RecyclerAdapter
import ahm.parts.ordering.ui.home.HomeViewModel
import ahm.parts.ordering.ui.previewimage.PreviewImageActivity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_salesman.*
import kotlinx.android.synthetic.main.item_home_leaderboard.view.*
import kotlinx.android.synthetic.main.content_loading_list.*
import org.jetbrains.anko.backgroundDrawable
import java.lang.Exception

class SalesmanFragment : BaseFragment<HomeViewModel>(), View.OnClickListener {

    var monthNow = CalendarUtils.getCurrentDate("MMMM yyyy")

    lateinit var dashboardAdapter: RecyclerAdapter<SalesManRank>

    var dashboards = ArrayList<SalesManRank>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_salesman, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initListener()

        observeData()
        viewModel.hitDashboardSalesman(monthNow)
    }

    private fun initUI() {

        lLoadingView.hide()

        initAdapter()

        try {
            val filter = IntentFilter(Constants.BROADCAST.DASHBOARD)
            activity!!.registerReceiver(calendarReciver, filter)
        } catch (e: Exception) {
        }
    }

    private fun observeData() {

        viewModel.apiResponse.observe(viewLifecycleOwner, Observer {
            showStateApiView(it,400) {
                viewModel.hitDashboardSalesman(monthNow)
            }
        })

        /**
         * mendapatkan data dashboard sales dari api
         */
        viewModel.dashboardSalesmans.observe(viewLifecycleOwner, Observer {
            /**
             * set text dari data dari api
             */
            tvTotalTarget text StringMasker().addRp(it.target)
            tvTotalPencapaian text StringMasker().addRp(it.pencapaian)
            tvTotalOmzet text StringMasker().addRp(it.totalOmzet)
            tvTotalProduktivitas text it.produktivitas
            tvTotalPencapaianCampaign text it.pencapaianCampaign
            tvTotalRealisasiVisit text it.realisasiVisit
            tvTotalEfektifitas text it.efectifitasVisit

            it.myRank!!.apply {
                tvRank text peringkat
                tvRankPosition text rank
                tvName text name

                when (type) {
                    Constants.DASHBOARD.TYPE.NAIK -> {
                        lMyRank.backgroundDrawable =
                            resources.getDrawable(R.drawable.bg_green_home_leaderboard)
                        ivArrowMyRank.setImageResource(R.drawable.ic_double_arrow_up)
                        ivArrowMyRank.show()
                    }
                    Constants.DASHBOARD.TYPE.BERTAHAN -> {
                        lMyRank.backgroundDrawable =
                            resources.getDrawable(R.drawable.bg_green_home_leaderboard)
                        ivArrowMyRank.invisible()
                    }
                    Constants.DASHBOARD.TYPE.TURUN -> {
                        lMyRank.backgroundDrawable =
                            resources.getDrawable(R.drawable.bg_red_home_leaderboard)
                        ivArrowMyRank.setImageResource(R.drawable.ic_double_arrow_down_red)
                        ivArrowMyRank.show()
                    }
                }

                ivProfileUser loadImageCircle photo

                ivProfileUser.onClick {
                    val intent = Intent(activity!!, PreviewImageActivity::class.java)

                    intent.putExtra(Constants.BUNDLE.PHOTO, photo)
                    val pair = Pair.create<View, String>(ivProfileUser, "imagePreview")
                    val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity!!, pair)
                    startActivity(intent, options.toBundle())
                }

            }

            dashboards.clear()
            dashboards.addAll(it.salesManRank!!)
            dashboardAdapter.notifyDataSetChanged()

            sendBroadcast()

        })
    }


    private fun initAdapter() {
        /**
         * membuat list adapter
         */
        dashboardAdapter = rvLeaderboard.setAdapter(activity!!, dashboards, R.layout.item_home_leaderboard, {
                val item = dashboards[it]

                tvPositionLeaderboard text (it + 1).toString()

                tvNameDashboard text item.name

                tvRank text item.peringkat

                ivProfile loadImageCircle item.photo

                ivProfile.onClick {
                    val intent = Intent(activity!!, PreviewImageActivity::class.java)

                    intent.putExtra(Constants.BUNDLE.PHOTO, item.photo)
                    val pair = Pair.create<View, String>(ivProfile, "imagePreview")
                    val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity!!, pair)
                    startActivity(intent, options.toBundle())
                }

                when (item.type) {
                    Constants.DASHBOARD.TYPE.NAIK -> {
                        ivArrowLeaderboard.setImageResource(R.drawable.ic_double_arrow_up_green)
                        ivArrowMyRank.show()
                    }
                    Constants.DASHBOARD.TYPE.BERTAHAN -> {
                        ivArrowLeaderboard.invisible()
                    }
                    Constants.DASHBOARD.TYPE.TURUN -> {
                        ivArrowLeaderboard.setImageResource(R.drawable.ic_double_arrow_down_red)
                        ivArrowLeaderboard.show()
                    }
                }

            }, {}
        )
    }

    private val calendarReciver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val bundle = intent.extras!!.getBundle(Constants.BUNDLE.CALENDAR)
            if (bundle != null) {

                val strCalendar = bundle.getString(Constants.BUNDLE.NAME)!!

                monthNow = strCalendar
                viewModel.hitDashboardSalesman(monthNow)
            }

        }
    }

    private fun sendBroadcast() {
        val intent = Intent(Constants.BROADCAST.DASHBOARD_CHECKOUT)
        activity!!.sendBroadcast(intent)
    }

    private fun initListener() {
        rootViewSalesman.lyTrans()
    }

    override fun onClick(view: View?) {
        when(view){

        }
    }

    fun newInstance(): SalesmanFragment {
        val fragment =
            SalesmanFragment()

        val args = Bundle()
        fragment.arguments = args

        return fragment
    }


}
