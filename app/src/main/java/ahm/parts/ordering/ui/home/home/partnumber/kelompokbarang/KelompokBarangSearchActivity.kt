package ahm.parts.ordering.ui.home.home.partnumber.kelompokbarang

import ahm.parts.ordering.R
import ahm.parts.ordering.api.ApiStatus
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dashboard.partnumber.AllKelompokBarang
import ahm.parts.ordering.helper.getString
import ahm.parts.ordering.helper.onChangeText
import ahm.parts.ordering.helper.onSearch
import ahm.parts.ordering.helper.setAdapter
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.base.adapter.RecyclerAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_kelompok_barang_search.*
import kotlinx.android.synthetic.main.item_content_kelompok_barang_search.view.*
import kotlinx.android.synthetic.main.item_header_kelompok_barang_search.view.*
import java.util.*
import kotlin.collections.ArrayList

class KelompokBarangSearchActivity : BaseActivity<KelompokBarangSearchViewModel>(),
    View.OnClickListener {

    lateinit var filterBarangAdapter: RecyclerAdapter<String>

    var allTypeBarangs = ArrayList<AllKelompokBarang>()
    var favoriteTypeBarangs = ArrayList<AllKelompokBarang>()

    var strSearch = ""

    var timer = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kelompok_barang_search)

        initUI()
        initListener()

        observeData()
        viewModel.hitKelompokBarangSearch(strSearch)
    }

    private fun initUI() {

        srRefresh.setOnRefreshListener {
            viewModel.hitKelompokBarangSearch(strSearch)
        }

        etSearch.onChangeText {
            strSearch = it

            timer = onSearch(timer) {
                viewModel.hitKelompokBarangSearch(strSearch)
            }
        }

        initAdapter()

    }

    private fun observeData() {
        /**
         * loading
         */
        viewModel.apiResponse.observe(this, Observer {
            if (it.status == ApiStatus.LOADING) {
                srRefresh.isRefreshing = true
            }
        })

        viewModel.kelompokBarangSearchs.observe(this, Observer {
            srRefresh.isRefreshing = false

            allTypeBarangs.clear()
            favoriteTypeBarangs.clear()

            allTypeBarangs.addAll(it.list)
            favoriteTypeBarangs.addAll(it.favorit)

            filterBarangAdapter.notifyDataSetChanged()
        })
    }

    private fun initAdapter() {

        var listData = ArrayList<String>()
        listData.add(getString(R.string.lbl_pencarian_favorit))
        listData.add(getString(R.string.lbl_semua_kelompok_barang))

        /**
         * membuat part adapter
         */
        filterBarangAdapter =
            rvFavorit.setAdapter(this, listData, R.layout.item_header_kelompok_barang_search, {
                val item = listData[it]

                tvName text item

                if (it == 0) {
                    rvContent.setAdapter(this@KelompokBarangSearchActivity,
                        favoriteTypeBarangs,
                        R.layout.item_content_kelompok_barang_search,
                        {
                            val item = favoriteTypeBarangs[it]

                            tvNameSearch text item.name + " - " + item.code
                        },
                        {
                            val intent = Intent()
                            intent.putExtra(Constants.BUNDLE.JSON, this.getString())
                            setResult(Constants.RESULT.PART_NUMBER_BARANG_SEARCH, intent)
                            finish()

                        })
                } else {
                    rvContent.setAdapter(this@KelompokBarangSearchActivity,
                        allTypeBarangs,
                        R.layout.item_content_kelompok_barang_search,
                        {
                            val item = allTypeBarangs[it]

                            tvNameSearch text item.name + " " + item.code
                        },
                        {
                            val intent = Intent()
                            intent.putExtra(Constants.BUNDLE.JSON, this.getString())
                            setResult(Constants.RESULT.PART_NUMBER_BARANG_SEARCH, intent)
                            finish()

                        })
                }
            }, {})
    }

    private fun initListener() {
        tvBatalkan.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            tvBatalkan -> {
                onBackPressed()
            }
        }
    }


}
