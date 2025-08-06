package ahm.parts.ordering.ui.widget.activity

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.helper.initItem
import ahm.parts.ordering.ui.base.BaseFragment
import ahm.parts.ordering.ui.home.HomeViewModel
import ahm.parts.ordering.ui.widget.activity.prospect.ProspectActivity
import ahm.parts.ordering.ui.widget.activity.sales.ActivitySalesActivity
import ahm.parts.ordering.ui.widget.activity.spk.SPKActivity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_sales_activity.*

class SalesActivityFragment : BaseFragment<HomeViewModel>() {

    var successRates = ArrayList<String>()
    var overdueActivities = ArrayList<String>()
    var newActivities = ArrayList<String>()

    lateinit var successRateAdapter: SuccessRateAdapter
    lateinit var overdueActivityAdapter: OverdueActivityAdapter
    lateinit var newActivityAdapter: NewActivityAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sales_activity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivUser.setOnClickListener(this)

        observeData()

        initListener()

        populateData()

        initAdapterActivity()
    }

    fun initListener() {
        flSPK.setOnClickListener(this)
        ivUser.setOnClickListener(this)
        flSales.setOnClickListener(this)
        tvSeeNew.setOnClickListener(this)
        flProspek.setOnClickListener(this)
        btnMessage.setOnClickListener(this)
        tvSeeOverdue.setOnClickListener(this)
        llLeaderboard.setOnClickListener(this)
        btnSalesActivity_new.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.flProspek -> {
                val i = Intent(activity!!, ProspectActivity::class.java)
                startActivityForResult(i, Constants.INTENT.REQ_INTENT)
            }

            R.id.flSPK -> {
                val i = Intent(activity!!, SPKActivity::class.java)
                startActivityForResult(i, Constants.INTENT.REQ_INTENT)
            }

            R.id.flSales -> {
                val i = Intent(activity!!, ActivitySalesActivity::class.java)
                startActivityForResult(i, Constants.INTENT.REQ_INTENT)
            }

            R.id.btnSalesActivity_new -> {
            }

        }
        super.onClick(v)
    }

    /**
     * mengisi daftar success rate, aktivitas overdue dan aktivitas baru dengan data dummy
     */
    private fun populateData() {
        successRates.clear()
        successRates.add("1")
        successRates.add("2")
        successRates.add("3")

        overdueActivities.clear()
        overdueActivities.add("1")
        overdueActivities.add("2")
        overdueActivities.add("3")

        newActivities.clear()
        newActivities.add("1")
        newActivities.add("2")
        newActivities.add("3")
    }

    /**
     * menampilkan daftar success rate, aktivitas overdue dan aktivitas baru
     */
    fun initAdapterActivity() {
        successRateAdapter = SuccessRateAdapter(successRates, activity!!) {

        }
        rvSuccessRate.initItem(activity!!, successRateAdapter)

        overdueActivityAdapter = OverdueActivityAdapter(overdueActivities, activity!!) {

        }
        rvOverdue.initItem(activity!!, overdueActivityAdapter)

        newActivityAdapter = NewActivityAdapter(newActivities, activity!!) {

        }
        rvNewAktivitas.initItem(activity!!, newActivityAdapter)
    }

    private fun observeData() {
        viewModel.getUser().observe(viewLifecycleOwner, Observer { user ->
            tvName.text = user?.name
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = SalesActivityFragment()
    }

    override fun onResume() {
        super.onResume()
    }
}
