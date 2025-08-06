package ahm.parts.ordering.ui.home.home.ordersugestion.cekstockpartfilter

import ahm.parts.ordering.R
import ahm.parts.ordering.api.ApiStatus
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dashboard.partnumber.AllKelompokBarang
import ahm.parts.ordering.data.model.home.dashboard.partnumber.AllTypeMotor
import ahm.parts.ordering.data.model.home.dashboard.partnumber.PartNumberFilter
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.base.adapter.RecyclerAdapter
import ahm.parts.ordering.ui.home.home.partnumber.detail.PartNumberDetailActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_part_number_filter.*
import kotlinx.android.synthetic.main.item_favorite_part_number.view.*

class CheckStockFilterActivity : BaseActivity<CheckStockViewModel>(), View.OnClickListener {

    lateinit var partFilterAdapter: RecyclerAdapter<PartNumberFilter>

    var partFilters = ArrayList<PartNumberFilter>()

    var strSimilarity = ""
    var strPartNumber = ""
    var strPartDescription = ""
    var shorting = ""

    lateinit var itemGroup: AllKelompokBarang
    lateinit var motorType: AllTypeMotor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_part_number_filter)

        initUI()
        initListener()

        observeData()
    }

    private fun initUI() {
        setToolbar(getString(R.string.lbl_title_cek_stock_part), true)

        swipeRefresh.setOnRefreshListener {
            viewModel.hitStockFilter(
                strSimilarity,
                strPartNumber,
                strPartDescription,
                itemGroup.id.toString(),
                motorType.id.toString(),
                ""
            )
        }

        strSimilarity = extra(Constants.BUNDLE.PARTSEARCH.SIMILARITY)
        strPartNumber = extra(Constants.BUNDLE.PARTSEARCH.PART_NUMBER)
        strPartDescription = extra(Constants.BUNDLE.PARTSEARCH.PART_DESKRIPSI)

        itemGroup = extra(Constants.BUNDLE.PARTSEARCH.JSON_BARANG).getObject()
        motorType = extra(Constants.BUNDLE.PARTSEARCH.JSON_MOTOR).getObject()

        etSearch text motorType.name + ", " + itemGroup.name

        viewModel.hitStockFilter(
            strSimilarity,
            strPartNumber,
            strPartDescription,
            itemGroup.id.toString(),
            motorType.id.toString(),
            ""
        )

        initAdapter()
    }

    private fun observeData() {

        viewModel.apiResponse.observe(this, Observer {
            if (it.status == ApiStatus.LOADING) {
                when (it.flagView) {
                    R.id.rvPartNumberFilter -> {
                        swipeRefresh.isRefreshing = true
                    }
                }
            }
        })

        viewModel.stockFilters.observe(this, Observer {
            swipeRefresh.isRefreshing = false

            partFilters.clear()
            partFilters.addAll(it)

            partFilterAdapter.notifyDataSetChanged()
        })

    }

    private fun initAdapter() {
        partFilterAdapter =
            rvPartNumberFilter.setAdapter(this, partFilters, R.layout.item_favorite_part_number, {
                val item = partFilters[it]

                tvPartNumber text item.partNumber
                tvPartDescription text item.partDescription
                tvHet text StringMasker().addRp(item.het.toDouble())
                tvAvailabePcs text item.availablePart

                tvItemGroup text item.itemGroup

                if(item.isLove == 1){
                    ivLike.setImageResource(R.drawable.ic_like)
                }else{
                    ivLike.setImageResource(R.drawable.ic_like_area)
                }

                if(item.isPromo == 1){
                    ivDiscount.show()
                }else{
                    ivDiscount.hide()
                }

            }, {
                val data = this
                goTo<PartNumberDetailActivity> {
                    putExtra(Constants.BUNDLE.JSON,data.getString())
                }
                fade()
            })
    }

    private fun initListener(){
        lSearch.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            lSearch -> {
                onBackPressed()
            }
        }
    }

}
