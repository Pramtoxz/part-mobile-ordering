package ahm.parts.ordering.ui.home.dealer.salesmanvisit.realisasivisit.dealer

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dashboard.partnumber.kodedealer.AllDealer
import ahm.parts.ordering.data.model.home.dealer.realisasivisit.Salesman
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.home.HomeViewModel
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.realisasivisit.RealisasiVisitViewModel
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.realisasivisit.adapter.KodeDealerAdapter
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_kode_dealer_efektivitas.*
import java.util.*
import kotlin.collections.ArrayList

class KodeDealerActivity : BaseActivity<RealisasiVisitViewModel>(), ClickPrevention {

    var strSearch = ""
    var timer = Timer()
    var isSearch = false

    /**
     * variable kode dealer page sebelumnya jika sudah pernah menyeleksi di page ini
     */
    var kodeDealerSelects = ArrayList<AllDealer>()


    var kodeDealers = ArrayList<AllDealer>()
    lateinit var kodeDealerAdapter : KodeDealerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kode_dealer_efektivitas)

        //salesman = extra(Constants.BUNDLE.JSON).getObject()

        initUI()
        initListener()

        observeData()
        viewModel.hitKodeDealer(strSearch)
    }

    private fun initUI(){
        kodeDealerSelects = extra(Constants.BUNDLE.KODEDEALER).toList<AllDealer>() as ArrayList<AllDealer>

        swipeRefresh.setOnRefreshListener {
            viewModel.hitKodeDealer(strSearch)
        }

        etSearch.onChangeText {
            strSearch = it

            isSearch = it != ""

            timer = onSearch(timer) {
                viewModel.hitKodeDealer(strSearch)
            }
        }

        initAdapter()
    }

    private fun observeData(){
        viewModel.apiResponse.observe(this, Observer {
            showStateApiView(it){
                viewModel.hitKodeDealer(strSearch)
            }
        })

        viewModel.kodeDealer.observe(this, Observer {
            kodeDealers.clear()

            kodeDealers.add(AllDealer(name = getString(R.string.lbl_semua_dealer)))
            kodeDealers.addAll(it.list)

            /**
             * TODO filter data yang terselect di pemilihan dealer
             */
            if (kodeDealers.size == kodeDealerSelects.size) {
                for (d in kodeDealers.indices) {
                    val itemMember = kodeDealerSelects[d]
                    val itemCard = kodeDealers[d]
                    if (itemCard.id == itemMember.id) {
                        itemCard.selectedDealer = true
                    }
                }
            } else {
                for (d in kodeDealers.indices) {
                    val itemCard = kodeDealers[d]
                    for (z in kodeDealerSelects.indices) {
                        val itemMember = kodeDealerSelects[z]
                        if (itemCard.id == itemMember.id) {
                            itemCard.selectedDealer = true
                        }
                    }
                }
            }

            kodeDealerAdapter.notifyDataSetChanged()
        })
    }

    private fun initAdapter(){
        kodeDealerAdapter = KodeDealerAdapter(kodeDealers,this){}

        rvKodeDealer.initItem(this,kodeDealerAdapter)
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
                kodeDealers.forEach {
                    if(it.name != getString(R.string.lbl_semua_dealer)){
                        if(it.selectedDealer){
                            totalSelected += 1
                        }
                    }
                }

                if(totalSelected == (kodeDealers.size -1)){
                    kodeDealerAdapter.isAllDealer = !isSearch
                }

                if(totalSelected == 0){
                    snacked(rootView,"Pilih Dealer Terlebih Dahulu")
                }else{
                    var dealersSelected = ArrayList<AllDealer>()

                    kodeDealers.forEach {
                        if(it.name != getString(R.string.lbl_semua_dealer)){
                            if(it.selectedDealer){
                                dealersSelected.add(it)
                            }
                        }

                    }

                    setResult(Constants.RESULT.KODE_DEALER){
                        putExtra(Constants.BUNDLE.JSON,dealersSelected.getString())
                        putExtra(Constants.BUNDLE.ALLDEALER,kodeDealerAdapter.isAllDealer)
                    }
                }
            }
        }
    }

}
