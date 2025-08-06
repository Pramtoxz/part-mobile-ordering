package ahm.parts.ordering.ui.home.dealer.kreditlimit.kodedealer.single

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dashboard.partnumber.kodedealer.AllDealer
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.base.adapter.RecyclerAdapter
import ahm.parts.ordering.ui.home.dealer.kreditlimit.adapter.KodeDealerAdapter
import ahm.parts.ordering.ui.home.dealer.kreditlimit.adapter.KodeDealerSingleSelectAdapter
import ahm.parts.ordering.ui.home.home.partnumber.kodedealer.KodeDealerViewModel
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_kode_dealer_kreditlimit.*
import kotlinx.android.synthetic.main.item_content_kelompok_barang_search.view.*
import kotlinx.android.synthetic.main.item_header_kelompok_barang_search.view.*
import java.util.*
import kotlin.collections.ArrayList

class KodeDealerActivity : BaseActivity<KodeDealerViewModel>(), View.OnClickListener {

    lateinit var kodeDealerAdapter: KodeDealerSingleSelectAdapter

    var kodeDealers = ArrayList<AllDealer>()
    var kodeDealerSelected = AllDealer()
    var kodeDealerSelectedKreditLimit : AllDealer? = null

    var strSearch = ""
    var timer = Timer()

    var dealerSelectionPos = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kode_dealer_kreditlimit)

        initUI()
        initListener()

        observeData()
        viewModel.hitKodeDealer(strSearch)
    }

    private fun initUI() {

        kodeDealerSelected = extra(Constants.BUNDLE.JSON).getObject()

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
            kodeDealers.addAll(it.list)

            for (i in 0 until kodeDealers.size){
                val item = kodeDealers[i]
                if(item.id == kodeDealerSelected.id){
                    dealerSelectionPos = i
                    kodeDealerSelectedKreditLimit = item
                }
            }

            initAdapter()

//            kodeDealerAdapter.notifyDataSetChanged()
        })
    }

    private fun initAdapter() {
        kodeDealerAdapter = KodeDealerSingleSelectAdapter(kodeDealers,this,dealerSelectionPos){}

        rvKodeDealer.initItem(this,kodeDealerAdapter)
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
                if(kodeDealerSelectedKreditLimit == null){
                    snacked(rootView,getString(R.string.lbl_required_dealer))
                }else{
                    setResult(Constants.RESULT.KODE_DEALER){
                        putExtra(Constants.BUNDLE.JSON,kodeDealerSelectedKreditLimit.getString())
                    }
                }
            }
        }
    }

}
