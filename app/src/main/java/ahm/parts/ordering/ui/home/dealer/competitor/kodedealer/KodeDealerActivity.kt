package ahm.parts.ordering.ui.home.dealer.competitor.kodedealer

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dashboard.partnumber.kodedealer.AllDealer
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.base.adapter.RecyclerAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_kode_dealer.*
import kotlinx.android.synthetic.main.item_content_kelompok_barang_search.view.*
import kotlinx.android.synthetic.main.item_header_kelompok_barang_search.view.*
import java.util.*
import kotlin.collections.ArrayList

class KodeDealerActivity : BaseActivity<KodeDealerViewModel>(), View.OnClickListener {

    lateinit var kodeDealerAdapter: RecyclerAdapter<String>

    var kodeDealerFavorits = ArrayList<AllDealer>()
    var kodeDealerAll = ArrayList<AllDealer>()

    var strSearch = ""

    var timer = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kode_dealer)

        initUI()
        initListener()

        observeData()
        viewModel.hitKodeDealer(strSearch)
    }

    private fun initUI() {

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
            kodeDealerFavorits.clear()
            kodeDealerAll.clear()

            kodeDealerFavorits.addAll(it.favorit)
            kodeDealerAll.addAll(it.list)

            kodeDealerAdapter.notifyDataSetChanged()
        })
    }

    private fun initAdapter() {

        val headerDealers = ArrayList<String>()
        headerDealers.add(resources.getString(R.string.lbl_pencarian_favorit))
        headerDealers.add(resources.getString(R.string.lbl_semua_kode_dealer))

        kodeDealerAdapter = rvKodeDealer.setAdapter(this, headerDealers, R.layout.item_header_kelompok_barang_search, {
                val item = headerDealers[it]

                tvName text item

                if (it == 0) {
                    rvContent.setAdapter(
                        this@KodeDealerActivity,
                        kodeDealerFavorits,
                        R.layout.item_content_kelompok_barang_search,
                        {
                            val item = kodeDealerFavorits[it]

                            tvNameSearch text item.code + " ~ " + item.name

                        },
                        {
                            val intent = Intent()
                            intent.putExtra(Constants.BUNDLE.JSON,this.getString())
                            setResult(Constants.RESULT.COMPETITOR_KODE_DEALER,intent)
                            finish()

                        })
                } else {
                    rvContent.setAdapter(
                        this@KodeDealerActivity,
                        kodeDealerAll,
                        R.layout.item_content_kelompok_barang_search,
                        {
                            val item = kodeDealerAll[it]

                            tvNameSearch text item.code + " ~ " + item.name
                        },
                        {
                            val intent = Intent()
                            intent.putExtra(Constants.BUNDLE.JSON,this.getString())
                            setResult(Constants.RESULT.COMPETITOR_KODE_DEALER,intent)
                            finish()
                        })
                }

            },
            {})

    }

    private fun initListener() {
        tvKembali.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            tvKembali -> {
                onBackPressed()
            }
        }
    }

}
