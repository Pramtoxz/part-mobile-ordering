package ahm.parts.ordering.ui.home.home.ordersugestion.tipemotor

import ahm.parts.ordering.R
import ahm.parts.ordering.helper.setAdapter
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.home.HomeViewModel
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_tipe_motor_search.*
import kotlinx.android.synthetic.main.item_content_kelompok_barang_search.view.*
import kotlinx.android.synthetic.main.item_header_kelompok_barang_search.view.*

class TipeMotorSearchActivity : BaseActivity<HomeViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tipe_motor_search)

        initUI()
        initListener()

    }

    private fun initUI() {

        initAdapter()

    }

    private fun initAdapter() {

        var listData = ArrayList<String>()
        listData.add("Pencarian Favorit")
        listData.add("Semua Kelompok Barang")

        rvFavorit.setAdapter(this, listData, R.layout.item_header_kelompok_barang_search, {
            val item = listData[it]

            tvName.text(item)

            var listFavorit = ArrayList<String>()
            listFavorit.add("TR - TIRE")
            listFavorit.add("BR - BRAKE")
            listFavorit.add("BT - BATTERY")

            rvContent.setAdapter(
                this@TipeMotorSearchActivity,
                listFavorit,
                R.layout.item_content_kelompok_barang_search,
                {
                    val item = listFavorit[it]

                    tvNameSearch.text(item)
                },
                {

                })

        }, {

        })
    }

    private fun initListener() {

    }

}
