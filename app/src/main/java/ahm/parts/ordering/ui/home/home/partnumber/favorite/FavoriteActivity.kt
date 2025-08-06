package ahm.parts.ordering.ui.home.home.partnumber.favorite

import ahm.parts.ordering.R
import ahm.parts.ordering.api.ApiStatus
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dashboard.partnumber.PartNumberFilter
import ahm.parts.ordering.data.model.home.dashboard.partnumber.kodedealer.AllDealer
import ahm.parts.ordering.data.room.user.User
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.base.adapter.RecyclerAdapter
import ahm.parts.ordering.ui.home.HomeViewModel
import ahm.parts.ordering.ui.home.home.partnumber.cart.CartActivity
import ahm.parts.ordering.ui.home.home.partnumber.detail.PartNumberDetailActivity
import ahm.parts.ordering.ui.home.home.partnumber.favorite.adapter.FavoriteAdapter
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_favorite_part_number.*
import kotlinx.android.synthetic.main.item_favorite_part_number.view.*
import org.json.JSONArray
import org.json.JSONObject

class FavoriteActivity : BaseActivity<FavoriteViewModel>(), View.OnClickListener {

    lateinit var favoriteAdapter: FavoriteAdapter
    var favorits = ArrayList<PartNumberFilter>()

    var user : User? = null
    lateinit var kodeDealer : AllDealer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_part_number)

        kodeDealer = extra(Constants.BUNDLE.PARTSEARCH.JSON_DEALER).getObject()

        initUI()
        initListener()

        observeData()
        viewModel.hitPartFavorite(kodeDealer.id)
    }

    private fun initUI() {
        setToolbar(getString(R.string.lbl_title_favorite), true)

        srRefresh.setOnRefreshListener {
            vChart.hide()
            viewModel.hitPartFavorite(kodeDealer.id)
        }

        viewModel.getUser().observe(this, Observer {
            user = it
            initAdapter()
        })

    }

    private fun observeData() {

        viewModel.apiResponse.observe(this, Observer {
            showStateApiView(it){
                vChart.hide()
                viewModel.hitPartFavorite(kodeDealer.id)
            }
            if (it.status == ApiStatus.LOADING) {

//                val l = it.flagView?.let { fview ->
//                    findViewById<RecyclerView>(fview).hide()
//                }!!

                when (it.flagView) {
                    R.id.rvFavorit -> {
                        srRefresh.isRefreshing = true
                        rvFavorit.hide()
                    }
                    R.id.btnChart -> {
                        loadingDialog.show(R.string.lbl_loading_harap_tunggu)
                    }
                }
            } else {
                if(it.flagLoadingDialog){
                    loadingDialog.dismiss()
                }

                if (it.status == ApiStatus.SUCCESS) {
                    when(it.flagView){
                        R.id.btnChart -> {
                            goTo<CartActivity>(requestCode = Constants.REQUEST.PART_NUMBER_SUGGEST_ORDER) {
                                putExtra(Constants.BUNDLE.PARTSEARCH.JSON_DEALER,kodeDealer.getString())
                            }
                            finish()
                        }
                    }
                }

                when (it.flagView) {
                    R.id.rvFavorit -> {
                        srRefresh.isRefreshing = false
                        rvFavorit.showErrorView(it){
                            viewModel.hitPartFavorite(kodeDealer.id)
                        }
                    }
                }
            }
        })

        viewModel.partFavorits.observe(this, Observer {

            favorits.clear()
            favorits.addAll(it)

            favorits.forEach {
                it.isChart = false
            }

            favoriteAdapter.notifyDataSetChanged()

        })

    }

    private fun initAdapter() {

        favoriteAdapter = FavoriteAdapter(favorits,this,user!!.id_role){
            goTo<PartNumberDetailActivity> {
                putExtra(Constants.BUNDLE.JSON,favorits[it].getString())
            }
        }

        rvFavorit.initItem(this,favoriteAdapter)

    }

    fun setTotalChart(){

        var intChartTotal = 0

        favorits.forEach {
            if(it.isChart){
                intChartTotal += 1
                tvItemChart text "$intChartTotal" + " items"
            }
        }

        for (i in 0 until favorits.size){
            val item = favorits[i]
            if(item.isChart){
                vChart.show()

                break
            }

            vChart.hide()
        }
    }

    fun createJsonPart() {
        val jsonArray = JSONArray()

        for (i in 0 until favorits.size) {
            val item = favorits[i]

            if(item.isChart){
                val jsonObject = JSONObject()
                jsonObject.put("part_id", item.id)
                jsonArray.put(jsonObject)
            }

        }
        val jsonSend = jsonArray.toString()
        viewModel.hitAddToCart(jsonSend,kodeDealer.id)
    }

    private fun initListener(){
        btnChart.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            btnChart -> {
                createJsonPart()
            }
        }
    }

}

