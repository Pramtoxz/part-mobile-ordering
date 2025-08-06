package ahm.parts.ordering.ui.home.dealer.salesmanvisit.efektivitas

import ahm.parts.ordering.R
import ahm.parts.ordering.api.ApiStatus
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dashboard.partnumber.kodedealer.AllDealer
import ahm.parts.ordering.data.model.home.dealer.efektivitas.EfektivitasPlanActualParam
import ahm.parts.ordering.data.model.home.dealer.realisasivisit.KoordinatorSalesman
import ahm.parts.ordering.data.model.home.dealer.realisasivisit.Salesman
import ahm.parts.ordering.data.room.user.User
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.efektivitas.adapter.CoordinatorSelectedAdapter
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.efektivitas.adapter.KodeDealerSelectedAdapter
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.efektivitas.adapter.SalesmanSelectedAdapter
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.efektivitas.planactual.koordinator.PlanActualEfektivitasCoordinatorActivity
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.efektivitas.planactual.manager.PlanActualEfektivitasCoordinatorManagerActivity
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.efektivitas.planactual.salesman.PlanActualEfektivitasSalesmanActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_realisasi_visit_multiple_select.*
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class EfektivitasActivity : BaseActivity<EfektivitasViewModel>(), View.OnClickListener {

    private var dealerSelecteds = ArrayList<AllDealer>()
    private var koordinatorSalesmanSelecteds =  ArrayList<KoordinatorSalesman>()
    private var salesmanSelecteds = ArrayList<Salesman>()

    var isAllDealer = false
    var isAllSalesman = false
    var isAllKoordinatorSalesman = false

    lateinit var user: User

    private lateinit var salesmanSelectedAdapter: SalesmanSelectedAdapter
    private lateinit var koordinatorSelectedAdapter: CoordinatorSelectedAdapter
    private lateinit var kodeDealerSelectedAdapter: KodeDealerSelectedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_realisasi_visit_multiple_select)

        initUI()
        initListener()

        observeData()
    }

    private fun initUI() {
        setToolbar(getString(R.string.lbl_title_efektivitas), true)

        viewModel.getUser().observe(this, Observer {
            try {
                user = it!!
                when(it.id_role){
                    Constants.ROLE.SALESMAN -> {
                        formKoordinatorSalesman.hide()
                        formSalesman.hide()
                    }
                    Constants.ROLE.MANAGER -> {
                        formKoordinatorSalesman.show()
                        formSalesman.show()
                    }
                    else -> {
                        formKoordinatorSalesman.hide()
                        formSalesman.show()
                    }

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })

        initAdapter()
    }

    private fun observeData() {
        viewModel.apiResponse.observe(this, Observer {
            showStateApiView(it) {}
            if (it.status == ApiStatus.LOADING) {
                loadingDialog.show(R.string.dialog_lbl_loading)
            } else {
                loadingDialog.dismiss()
            }
        })
    }

    private fun initAdapter() {
        kodeDealerSelectedAdapter = KodeDealerSelectedAdapter(dealerSelecteds, this) {}

        rvDealer.initItemGrid(this, kodeDealerSelectedAdapter, 2)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.REQUEST.KODE_DEALER && resultCode == Constants.RESULT.KODE_DEALER) {

            dealerSelecteds.clear()
            dealerSelecteds.addAll(data!!.extra(Constants.BUNDLE.JSON).toList<AllDealer>() as ArrayList<AllDealer>)

            isAllDealer = data!!.extra(Constants.BUNDLE.ALLDEALER,false)

            etPilihDealer.hint = "Add Dealer"

            kodeDealerSelectedAdapter.notifyDataSetChanged()

        } else if (requestCode == Constants.REQUEST.KOORDINATOR_SALESMAN && resultCode == Constants.RESULT.KOORDINATOR_SALESMAN) {

            koordinatorSalesmanSelecteds.clear()
            koordinatorSalesmanSelecteds = data!!.extra(Constants.BUNDLE.KOORDINATOR_SALESMAN).toList<KoordinatorSalesman>() as ArrayList<KoordinatorSalesman>

            isAllKoordinatorSalesman = data.extra(Constants.BUNDLE.ALL_KOORDINATOR_SALESMAN,false)

            etKoordinatorSalesman.hint = getString(R.string.hint_add_salesman)

            koordinatorSelectedAdapter = CoordinatorSelectedAdapter(koordinatorSalesmanSelecteds, this) {}
            rvKoordinatorSalesman.initItemGrid(this, koordinatorSelectedAdapter, 2)

        } else if (requestCode == Constants.REQUEST.SALESMAN_SELECT && resultCode == Constants.RESULT.SALESMAN_SELECT) {

            salesmanSelecteds.clear()
            salesmanSelecteds = data!!.extra(Constants.BUNDLE.JSON).toList<Salesman>() as ArrayList<Salesman>

            isAllSalesman = data.extra(Constants.BUNDLE.ALLSALESMAN,false)

            etSalesman.hint = getString(R.string.hint_add_salesman)

            salesmanSelectedAdapter = SalesmanSelectedAdapter(salesmanSelecteds, this) {}
            rvSalesman.initItemGrid(this, salesmanSelectedAdapter, 2)
        }
    }

    private fun initListener() {
        btnViewPlanActual.setOnClickListener(this)
        etKoordinatorSalesman.setOnClickListener(this)
        etSalesman.setOnClickListener(this)
        etPilihDealer.setOnClickListener(this)
        etTanggalAwal.setOnClickListener(this)
        etTanggalAkhir.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!) {
            btnViewPlanActual -> {

                val strDateStart = textOf(etTanggalAwal)
                val strDateEnd = textOf(etTanggalAkhir)

                if (user.id_role == Constants.ROLE.MANAGER) {
                    when {
                        koordinatorSalesmanSelecteds.isEmpty() -> {
                            snacked(rootView, getString(R.string.alert_select_koordinator_salesman_first))
                            return
                        }
                        salesmanSelecteds.isEmpty() -> {
                            snacked(rootView, getString(R.string.alert_select_salesman_first))
                            return
                        }
                    }
                }else if(user.id_role != Constants.ROLE.SALESMAN){
                    when {
                        salesmanSelecteds.isEmpty() -> {
                            snacked(rootView, getString(R.string.alert_select_salesman_first))
                            return
                        }
                    }
                }

                when {
                    dealerSelecteds.isEmpty() -> {
                        snacked(rootView, "Select dealer first")
                    }
                    strDateStart.isEmpty() -> {
                        snacked(rootView, "Select date start first")
                    }
                    strDateEnd.isEmpty() -> {
                        snacked(rootView, "Select date end first")
                    }
                    else -> {
                        val formatStartDate = CalendarUtils.setFormatDate(
                            strDateStart,
                            "MMMM dd, yyyy",
                            SERVER_DATE_FORMAT
                        )
                        val formatEndDate = CalendarUtils.setFormatDate(
                            strDateEnd,
                            "MMMM dd, yyyy",
                            SERVER_DATE_FORMAT
                        )
                        if (user.id_role == Constants.ROLE.SALESMAN) {

                            val paramPlanActual = EfektivitasPlanActualParam(
                                dealer = createArrays(dealerSelecteds),
                                startTime = formatStartDate,
                                endTime = formatEndDate,
                                isAllDealer = isAllDealer)

                            goTo<PlanActualEfektivitasSalesmanActivity> {
                                putExtra(Constants.BUNDLE.PARAM,paramPlanActual.getString())
                            }

                        }else if(user.id_role == Constants.ROLE.MANAGER){
                            val paramPlanActual = EfektivitasPlanActualParam(
                                dealer = createArrays(dealerSelecteds),
                                startTime = formatStartDate,
                                endTime = formatEndDate,
                                isAllDealer = isAllDealer,
                                salesman = createArraysSalesman(salesmanSelecteds),
                                isAllSalesman = isAllSalesman,
                                koordinator = createArraysKoordinator(koordinatorSalesmanSelecteds),
                                isAllKoordinator = isAllKoordinatorSalesman
                            )

                            goTo<PlanActualEfektivitasCoordinatorManagerActivity> {
                                putExtra(Constants.BUNDLE.PARAM,paramPlanActual.getString())
                            }

                        }else{
                            val paramPlanActual = EfektivitasPlanActualParam(
                                dealer = createArrays(dealerSelecteds),
                                startTime = formatStartDate,
                                endTime = formatEndDate,
                                isAllDealer = isAllDealer,
                                salesman = createArraysSalesman(salesmanSelecteds),
                                isAllSalesman = isAllSalesman)

                            goTo<PlanActualEfektivitasCoordinatorActivity> {
                                putExtra(Constants.BUNDLE.PARAM,paramPlanActual.getString())
                            }
                        }
                    }
                }
            }
            etTanggalAwal -> {
                datePickerWithOutMin {
                    etTanggalAwal text CalendarUtils.setFormatDate(
                        it,
                        "dd MM yyyy",
                        "MMMM dd, yyyy"
                    )
                }
            }
            etTanggalAkhir -> {
                datePickerWithOutMin {
                    etTanggalAkhir text CalendarUtils.setFormatDate(
                        it,
                        "dd MM yyyy",
                        "MMMM dd, yyyy"
                    )
                }
            }
            etKoordinatorSalesman -> {
                goTo<KoordinatorSalesmanEfektivitasActivity>(requestCode = Constants.REQUEST.KOORDINATOR_SALESMAN) {
                    putExtra(Constants.BUNDLE.KOORDINATOR_SALESMAN,koordinatorSalesmanSelecteds.getString())
                }
            }
            etSalesman -> {
                if (user.id_role != Constants.ROLE.SALESMAN) {
                        goTo<SalesmanEfektivitasActivity>(requestCode = Constants.REQUEST.SALESMAN_SELECT) {
                            putExtra(Constants.BUNDLE.JSON, salesmanSelecteds.getString())
                        }
                }

            }
            etPilihDealer -> {
                if (user.id_role == Constants.ROLE.SALESMAN) {
                    goTo<KodeDealerEfektivitas>(requestCode = Constants.REQUEST.KODE_DEALER) {
                        putExtra(Constants.BUNDLE.JSON, salesmanSelecteds.getString())
                        putExtra(Constants.BUNDLE.KODEDEALER, dealerSelecteds.getString())
                    }
                } else {
                    if (salesmanSelecteds.isNotEmpty()) {
                        goTo<KodeDealerEfektivitas>(requestCode = Constants.REQUEST.KODE_DEALER) {
                            putExtra(Constants.BUNDLE.JSON, salesmanSelecteds.getString())
                            putExtra(Constants.BUNDLE.KODEDEALER, dealerSelecteds.getString())
                        }
                    } else {
                        snacked(rootView, "Select salesman first")
                    }
                }

            }
        }
    }

    private fun createArrays(list : ArrayList<AllDealer>): String {

        var arrays = "["

        for (i in 0 until list.size){
            val item = list[i]
            if(i != list.size -1){
                arrays = arrays + item.id + ","
            }else{
                arrays = arrays + item.id + "]"
            }
        }
        Log.e("arraysSend", "" + arrays)
        return arrays
    }

    private fun createArraysSalesman(list : ArrayList<Salesman>): String {

        var arrays = "["

        for (i in 0 until list.size){
            val item = list[i]
            if(i != list.size -1){
                arrays = arrays + item.id + ","
            }else{
                arrays = arrays + item.id + "]"
            }
        }
        Log.e("arraysSalesmanSend", "" + arrays)
        return arrays
    }

    private fun createArraysKoordinator(list : ArrayList<KoordinatorSalesman>): String {

        var arrays = "["

        for (i in 0 until list.size){
            val item = list[i]
            if(i != list.size -1){
                arrays = arrays + item.id + ","
            }else{
                arrays = arrays + item.id + "]"
            }
        }
        Log.e("arraysKoordinatorSend", "" + arrays)
        return arrays
    }
}
