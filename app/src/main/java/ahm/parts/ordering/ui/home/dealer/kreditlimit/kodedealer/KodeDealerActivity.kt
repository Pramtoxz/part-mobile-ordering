package ahm.parts.ordering.ui.home.dealer.kreditlimit.kodedealer

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dashboard.partnumber.kodedealer.AllDealer
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.base.adapter.RecyclerAdapter
import ahm.parts.ordering.ui.home.dealer.kreditlimit.adapter.KodeDealerAdapter
import ahm.parts.ordering.ui.home.home.partnumber.kodedealer.KodeDealerViewModel
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_kode_dealer_kreditlimit.*
import kotlinx.android.synthetic.main.item_content_kelompok_barang_search.view.*
import kotlinx.android.synthetic.main.item_header_kelompok_barang_search.view.*
import java.util.*
import kotlin.collections.ArrayList

class KodeDealerActivity : BaseActivity<KodeDealerViewModel>(), View.OnClickListener {

    lateinit var kodeDealerAdapter: KodeDealerAdapter

    var kodeDealers = ArrayList<AllDealer>()
    var kodeDealerSelects = ArrayList<AllDealer>()

    var strSearch = ""
    var timer = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kode_dealer_kreditlimit)

        initUI()
        initListener()

        observeData()
        viewModel.hitKodeDealer(strSearch)
    }

    private fun initUI() {

        kodeDealerSelects = extra(Constants.BUNDLE.JSON).toList<AllDealer>() as ArrayList<AllDealer>

        swipeRefresh.setOnRefreshListener {
            viewModel.hitKodeDealer(strSearch)
        }

        etSearch.onChangeText {
            strSearch = it

            timer = onSearch(timer) {
                viewModel.hitKodeDealer(strSearch)
            }
        }

        initAdapter()
    }

    private fun observeData() {
        viewModel.apiResponse.observe(this, androidx.lifecycle.Observer {
            showStateApiView(it){
                viewModel.hitKodeDealer(strSearch)
            }
        })

        viewModel.kodeDealer.observe(this, androidx.lifecycle.Observer {
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

    private fun initAdapter() {
        kodeDealerAdapter = KodeDealerAdapter(kodeDealers,this){}

        rvKodeDealer.initItem(this,kodeDealerAdapter)
    }

    private fun validateList(){

    }

    private fun initListener() {
        tvBatalkan.setOnClickListener(this)
        btnApplyDealer.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
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
                    }
                }
            }
        }
    }

}
