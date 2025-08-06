package ahm.parts.ordering.ui.home.dealer.salesmanvisit.efektivitas.salesman

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dashboard.partnumber.kodedealer.AllDealer
import ahm.parts.ordering.data.model.home.dealer.realisasivisit.KoordinatorSalesman
import ahm.parts.ordering.data.model.home.dealer.realisasivisit.Salesman
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.home.HomeViewModel
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.efektivitas.EfektivitasViewModel
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.realisasivisit.RealisasiVisitViewModel
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.realisasivisit.adapter.KodeDealerAdapter
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.realisasivisit.adapter.KoordinatorSalesmanAdapter
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.realisasivisit.adapter.SalesmanAdapter
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_kode_dealer_efektivitas.*
import java.util.*
import kotlin.collections.ArrayList

class SalesmanActivity : BaseActivity<EfektivitasViewModel>(), ClickPrevention {

    var strSearch = ""
    var timer = Timer()
    var isSearch = false

    var salesmans = ArrayList<Salesman>()
    var salesmanSelecteds = ArrayList<Salesman>()
    lateinit var salesmanAdapter : SalesmanAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kode_dealer_efektivitas)

        salesmanSelecteds = extra(Constants.BUNDLE.JSON).toList<Salesman>() as ArrayList<Salesman>

        initUI()
        initListener()

        observeData()
        viewModel.hitSalesman(strSearch)
    }

    private fun initUI(){
        etSearch.hint = getString(R.string.hint_cari_kode_nama_salesman)
        tvHeader text getString(R.string.lbl_salesman_yang_terhubung_dengan_anda)

        swipeRefresh.setOnRefreshListener {
            viewModel.hitSalesman(strSearch)
        }

        etSearch.onChangeText {
            strSearch = it

            isSearch = it != ""

            timer = onSearch(timer) {
                viewModel.hitSalesman(strSearch)
            }
        }

        initAdapter()
    }

    private fun observeData(){
        viewModel.apiResponse.observe(this, Observer {
            showStateApiView(it){
                viewModel.hitSalesman(strSearch)
            }
        })

        viewModel.salesmans.observe(this, Observer {

            salesmans.clear()

            salesmans.add(Salesman(name = getString(R.string.lbl_semua_salesman)))
            salesmans.addAll(it)

            /**
             * TODO filter data yang terselect di pemilihan dealer
             */
            if (salesmans.size == salesmanSelecteds.size) {
                for (d in salesmans.indices) {
                    val itemMember = salesmanSelecteds[d]
                    val itemCard = salesmans[d]
                    if (itemCard.id == itemMember.id) {
                        itemCard.selectedSales = true
                    }
                }
            } else {
                for (d in salesmans.indices) {
                    val itemCard = salesmans[d]
                    for (z in salesmanSelecteds.indices) {
                        val itemMember = salesmanSelecteds[z]
                        if (itemCard.id == itemMember.id) {
                            itemCard.selectedSales = true
                        }
                    }
                }
            }

            salesmanAdapter.notifyDataSetChanged()
        })
    }

    private fun initAdapter(){
        salesmanAdapter = SalesmanAdapter(salesmans,this){}

        rvKodeDealer.initItem(this,salesmanAdapter)
    }

    private fun initListener(){
        btnApplyDealer.setOnClickListener(this)
        tvBatalkan.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!){
            tvBatalkan -> {
                onBackPressed()
            }
            btnApplyDealer -> {
                var totalSelected = 0
                salesmans.forEach {
                    if(it.name != getString(R.string.lbl_semua_salesman)){
                        if(it.selectedSales){
                            totalSelected += 1
                        }
                    }
                }

                if(totalSelected == (salesmans.size -1)){
                    salesmanAdapter.isAllSalesman = !isSearch
                }

                if(totalSelected == 0){
                    snacked(rootView,"Pilih salesman Terlebih Dahulu")
                }else{
                    var salesmanSelecteds = ArrayList<Salesman>()

                    salesmans.forEach {
                        if(it.name != getString(R.string.lbl_semua_salesman)){
                            if(it.selectedSales){
                                salesmanSelecteds.add(it)
                            }
                        }

                    }

                    setResult(Constants.RESULT.SALESMAN_SELECT){
                        putExtra(Constants.BUNDLE.JSON,salesmanSelecteds.getString())
                        putExtra(Constants.BUNDLE.ALLSALESMAN,salesmanAdapter.isAllSalesman)
                    }
                }
                /*if(salesmanAdapter.salesmanSelected != null){
                    setResult(Constants.RESULT.SALESMAN_SELECT){
                        putExtra(Constants.BUNDLE.JSON,salesmanAdapter.salesmanSelected!!.getString())
                    }
                }else{
                    snacked(rootView,resources.getString(R.string.lbl_form_select_required))
                }*/
            }
        }
    }

}
