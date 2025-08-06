package ahm.parts.ordering.ui.home.home.partnumber.partnumbersearch

import ahm.parts.ordering.R
import ahm.parts.ordering.api.ApiStatus
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dashboard.partnumber.AllKelompokBarang
import ahm.parts.ordering.data.model.home.dashboard.partnumber.AllTypeMotor
import ahm.parts.ordering.data.model.home.dashboard.partnumber.PartNumberFilter
import ahm.parts.ordering.data.model.home.dashboard.partnumber.filter.PartNumberFilterSearch
import ahm.parts.ordering.data.model.home.dashboard.partnumber.kodedealer.AllDealer
import ahm.parts.ordering.data.room.user.User
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.helper.dialog.DialogHelper
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.base.adapter.RecyclerAdapter
import ahm.parts.ordering.ui.home.home.partnumber.adapter.PartNumberFilterAdapter
import ahm.parts.ordering.ui.home.home.partnumber.cart.CartActivity
import ahm.parts.ordering.ui.home.home.partnumber.detail.PartNumberDetailActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_part_number_filter.*
import kotlinx.android.synthetic.main.item_sorting_part.view.*
import org.json.JSONArray
import org.json.JSONObject

class PartNumberFilterActivity : BaseActivity<PartNumberFilterViewModel>(), View.OnClickListener {

    lateinit var dialogHelper : DialogHelper

    lateinit var partFilterAdapter: PartNumberFilterAdapter

    var partFilters = ArrayList<PartNumberFilter>()

    var strSortingSelected = ""
    var selectedSorting : PartNumberFilterSearch? = null

    var strSimilarity = ""
    var strPartNumber = ""
    var strPartDescription = ""

    var itemGroup = AllKelompokBarang()
    var motorType = AllTypeMotor()
    lateinit var kodeDealer: AllDealer

    var user : User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_part_number_filter)

        initUI()
        initListener()

        observeData()
        hitApi()
    }

    private fun initUI() {
        setToolbar(getString(R.string.lbl_title_part_number_search), true)

        dialogHelper = DialogHelper(this,true)

        strSimilarity = extra(Constants.BUNDLE.PARTSEARCH.SIMILARITY)
        strPartNumber = extra(Constants.BUNDLE.PARTSEARCH.PART_NUMBER)
        strPartDescription = extra(Constants.BUNDLE.PARTSEARCH.PART_DESKRIPSI)

        val strJsonMotor = extra(Constants.BUNDLE.PARTSEARCH.JSON_MOTOR)
        if(strJsonMotor != ""){
            motorType = strJsonMotor.getObject()
        }

        val strItemGroup = extra(Constants.BUNDLE.PARTSEARCH.JSON_BARANG)
        if(strItemGroup != ""){
            itemGroup = strItemGroup.getObject()
        }


        Log.e("kodeDealerString",extra(Constants.BUNDLE.PARTSEARCH.JSON_DEALER))
        kodeDealer = extra(Constants.BUNDLE.PARTSEARCH.JSON_DEALER).getObject()

        val filterSearch = strSimilarity.trim() + " " + strPartNumber.trim() + " " + strPartDescription.trim() + " " + motorType.name.trim() + " " + itemGroup.name.trim()
        etSearch text filterSearch.trim()

        swipeRefresh.setOnRefreshListener {
            lBottom.hide()
            hitApi()
        }

        viewModel.getUser().observe(this, Observer {
            user = it
            initAdapter()
        })

    }

    private fun observeData() {

        viewModel.apiResponse.observe(this, Observer {
            showStateApiView(it){
                lBottom.hide()
                hitApi()
            }
            if (it.status == ApiStatus.LOADING) {
                when(it.flagView){
                    R.id.btnChart -> {
                        loadingDialog.show(R.string.lbl_loading_harap_tunggu)
                    }
                }
            } else {
                loadingDialog.dismiss()
                if (it.status == ApiStatus.SUCCESS) {
                    when(it.flagView){
                        R.id.btnChart -> {
                            goTo<CartActivity>(requestCode = Constants.REQUEST.PART_NUMBER_SUGGEST_ORDER) {
                                putExtra(Constants.BUNDLE.JSON,partFilters.getString())
                                putExtra(Constants.BUNDLE.PARTSEARCH.JSON_DEALER,kodeDealer.getString())
                            }
                        }
                    }
                }
            }
        })

        viewModel.partNumberSearchs.observe(this, Observer {
            lBottom.hide()
            partFilters.clear()
            partFilters.addAll(it)

            partFilters.forEach {
                it.isChart = false
            }

            partFilterAdapter.notifyDataSetChanged()
        })

    }

    private fun hitApi(){
        viewModel.hitPartNumberSearch(
            strSimilarity,
            strPartNumber,
            strPartDescription,
            itemGroup.id,
            motorType.id,
            strSortingSelected,
            kodeDealer.id
        )
    }

    private fun initAdapter() {
        partFilterAdapter = PartNumberFilterAdapter(partFilters,this,user!!.id_role){
            goTo<PartNumberDetailActivity> {
                putExtra(Constants.BUNDLE.JSON,partFilters[it].getString())
            }
        }

        rvPartNumberFilter.initItem(this,partFilterAdapter)
    }

    fun filteredData(){

        var intChartTotal = 0

        partFilters.forEach {
            if(it.isChart){
                intChartTotal += 1
                tvItemChart text "$intChartTotal items"
            }
        }

        for (i in 0 until partFilters.size){
            val item = partFilters[i]
            if(item.isChart){
                lBottom.show()

                break
            }

            lBottom.hide()
        }

    }

    fun createJsonPart() {
        val jsonArray = JSONArray()

        for (i in 0 until partFilters.size) {
            val item = partFilters[i]

            if(item.isChart){
                val jsonObject = JSONObject()
                jsonObject.put("part_id", item.id)
                jsonArray.put(jsonObject)
            }

        }
        val jsonSend = jsonArray.toString()
        viewModel.hitAddToCart(jsonSend,kodeDealer.id)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e("requestCode",""+requestCode)
        Log.e("resultCode",""+resultCode)
        if(requestCode == Constants.REQUEST.PART_NUMBER_SUGGEST_ORDER && resultCode == Constants.RESULT.PART_NUMBER_SUGGEST_ORDER){
            val intent = Intent()
            setResult(Constants.RESULT.PART_NUMBER_SUGGEST_ORDER,intent)
            finish()
        }else if(requestCode == Constants.REQUEST.PART_NUMBER_SUGGEST_ORDER && resultCode == Constants.RESULT.PART_NUMBER_SUMMARY){
            val intent = Intent()
            setResult(Constants.RESULT.PART_NUMBER_SUMMARY,intent)
            finish()
        }
    }

    private fun initListener(){
        ivSorting.setOnClickListener(this)
        lSearch.setOnClickListener(this)
        btnChart.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            btnChart -> {
                createJsonPart()
            }
            ivSorting -> {
                val dialogSorting = dialogHelper.bottomSheet(R.layout.dialog_bottom_filter_part){}

                val rvSortingPart = dialogSorting.findViewById<RecyclerView>(R.id.rvSortingPart)!!
                val btnApply = dialogSorting.findViewById<Button>(R.id.btnApply)!!

                val sortings = ArrayList<PartNumberFilterSearch>()
                sortings.add(PartNumberFilterSearch(getString(R.string.sort_partnumber_a_z),"part_number|asc"))
                sortings.add(PartNumberFilterSearch(getString(R.string.sort_deskripsi_a_z),"description|asc"))
                sortings.add(PartNumberFilterSearch(getString(R.string.sort_available_part),"available_part|a"))
                sortings.add(PartNumberFilterSearch(getString(R.string.sort_not_available),"available_part|na"))
                sortings.add(PartNumberFilterSearch(getString(R.string.sort_promo),"promo|yes"))
                sortings.add(PartNumberFilterSearch(getString(R.string.sort_tidak_promo),"promo|no"))

                var sortingAdapter : RecyclerAdapter<PartNumberFilterSearch>? = null
                sortingAdapter = rvSortingPart.setAdapter(this,sortings,R.layout.item_sorting_part,{
                    val item = sortings[it]

                    tvSorting text item.name

                    if(selectedSorting!= null){
                        if(selectedSorting!!.name == item.name){
                            ivSorting.setImageResource(R.drawable.bg_fill_sorting_red)
                        }else{
                            ivSorting.setImageResource(R.drawable.bg_fill_sorting_grey)
                        }
                    }else{
                        ivSorting.setImageResource(R.drawable.bg_fill_sorting_grey)
                    }

                },{
                    selectedSorting = if(selectedSorting == this){
                        null
                    }else{
                        this
                    }

                    sortingAdapter!!.notifyDataSetChanged()

                })

                btnApply.onClick {
                    dialogSorting.dismiss()
                    strSortingSelected = if(selectedSorting != null){
                        selectedSorting!!.nameParam
                    }else{
                        ""
                    }
                    hitApi()
                }
            }
            lSearch -> {
                onBackPressed()
            }
        }
    }

}