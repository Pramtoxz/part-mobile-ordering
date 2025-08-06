package ahm.parts.ordering.ui.home.dealer.salesmanvisit.realisasivisit.koordinatorsalesman

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dashboard.partnumber.kodedealer.AllDealer
import ahm.parts.ordering.data.model.home.dealer.realisasivisit.KoordinatorSalesman
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.home.HomeViewModel
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.realisasivisit.RealisasiVisitViewModel
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.realisasivisit.adapter.KodeDealerAdapter
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.realisasivisit.adapter.KoordinatorSalesmanAdapter
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_kode_dealer_efektivitas.*
import java.util.*
import kotlin.collections.ArrayList

class KoordinatorSalesmanActivity : BaseActivity<RealisasiVisitViewModel>(), ClickPrevention {

    var strSearch = ""
    var timer = Timer()
    var isSearch = false

    var koordinatorSelects = ArrayList<KoordinatorSalesman>()

    var koordinators = ArrayList<KoordinatorSalesman>()
    lateinit var koordinatorAdapter : KoordinatorSalesmanAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kode_dealer_efektivitas)

        initUI()
        initListener()

        observeData()
        viewModel.hitKoordinatorSalesman(strSearch)
    }

    private fun initUI(){
        etSearch.hint = getString(R.string.hint_cari_kode_nama_koordinator_salesman)
        tvHeader text getString(R.string.lbl_salesman_yang_terhubung_dengan_anda)

        koordinatorSelects = extra(Constants.BUNDLE.KOORDINATOR_SALESMAN).toList<KoordinatorSalesman>() as ArrayList<KoordinatorSalesman>

        swipeRefresh.setOnRefreshListener {
            viewModel.hitKoordinatorSalesman(strSearch)
        }

        etSearch.onChangeText {
            strSearch = it

            isSearch = it != ""

            timer = onSearch(timer) {
                viewModel.hitKoordinatorSalesman(strSearch)
            }
        }

        initAdapter()
    }

    private fun observeData(){
        viewModel.apiResponse.observe(this, Observer {
            showStateApiView(it){
                viewModel.hitKoordinatorSalesman(strSearch)
            }
        })

        viewModel.koordinatorSalesmans.observe(this, Observer {
            koordinators.clear()

            koordinators.add(KoordinatorSalesman(name = getString(R.string.lbl_semua_koordinator)))
            koordinators.addAll(it)

            /**
             * TODO filter data yang terselect di pemilihan dealer
             */
            if (koordinators.size == koordinatorSelects.size) {
                for (d in koordinators.indices) {
                    val itemMember = koordinatorSelects[d]
                    val itemCard = koordinators[d]
                    if (itemCard.id == itemMember.id) {
                        itemCard.selectedKoordinator = true
                    }
                }
            } else {
                for (d in koordinators.indices) {
                    val itemCard = koordinators[d]
                    for (z in koordinatorSelects.indices) {
                        val itemMember = koordinatorSelects[z]
                        if (itemCard.id == itemMember.id) {
                            itemCard.selectedKoordinator = true
                        }
                    }
                }
            }

            koordinatorAdapter.notifyDataSetChanged()
            /*koordinators.clear()

            koordinators.add(KoordinatorSalesman(name = getString(R.string.lbl_semua_salesman)))

            koordinators.addAll(it)

            koordinatorAdapter.notifyDataSetChanged()*/
        })
    }

    private fun initAdapter(){
        koordinatorAdapter = KoordinatorSalesmanAdapter(koordinators,this){}

        rvKodeDealer.initItem(this,koordinatorAdapter)
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
                koordinators.forEach {
                    if(it.name != getString(R.string.lbl_semua_koordinator)){
                        if(it.selectedKoordinator){
                            totalSelected += 1
                        }
                    }
                }

                if(totalSelected == (koordinators.size -1)){
                    koordinatorAdapter.isAllKoordinator = !isSearch
                }

                if(totalSelected == 0){
                    snacked(rootView,getString(R.string.alert_pilih_koordinator_terlebih_dahulu))
                }else{
                    val koordinatorSelected = ArrayList<KoordinatorSalesman>()

                    koordinators.forEach {
                        if(it.name != getString(R.string.lbl_semua_koordinator)){
                            if(it.selectedKoordinator){
                                koordinatorSelected.add(it)
                            }
                        }

                    }

                    setResult(Constants.RESULT.KOORDINATOR_SALESMAN){
                        putExtra(Constants.BUNDLE.KOORDINATOR_SALESMAN,koordinatorSelected.getString())
                        putExtra(Constants.BUNDLE.ALL_KOORDINATOR_SALESMAN,koordinatorAdapter.isAllKoordinator)
                    }
                }

            }
        }
    }

}
