package ahm.parts.ordering.ui.home.home.ordersugestion.partsearch

import ahm.parts.ordering.R
import ahm.parts.ordering.api.ApiStatus
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dashboard.partnumber.AllKelompokBarang
import ahm.parts.ordering.data.model.home.dashboard.partnumber.AllTypeMotor
import ahm.parts.ordering.data.model.home.dashboard.partnumber.kodedealer.AllDealer
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.home.HomeViewModel
import ahm.parts.ordering.ui.home.home.partnumber.favorite.FavoriteActivity
import ahm.parts.ordering.ui.home.home.partnumber.kelompokbarang.KelompokBarangSearchActivity
import ahm.parts.ordering.ui.home.home.partnumber.partnumbersearch.PartNumberFilterActivity
import ahm.parts.ordering.ui.home.home.partnumber.searchpart.SearchPartViewModel
import ahm.parts.ordering.ui.home.home.partnumber.tipemotor.TipeMotorSearchActivity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_part_number_search.*

class PartNumberSearchActivity : BaseActivity<SearchPartViewModel>(), View.OnClickListener {

    var dealerSelected : AllDealer? = null
    var itemGroupSelected = AllKelompokBarang()
    var motorSelected = AllTypeMotor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_part_number_search)

        dealerSelected = extra(Constants.BUNDLE.JSON).getObject()

        initUI()
        initListener()

        observeData()
    }

    private fun initUI(){
        setToolbar(getString(R.string.lbl_title_search_parts),true)
    }

    private fun observeData(){
        viewModel.apiResponse.observe(this, Observer {
            showStateApiView(it){}

            if(it.status == ApiStatus.LOADING){
                loadingDialog.show(R.string.dialog_lbl_please_wait)
            }else{
                if(it.flagLoadingDialog){
                    loadingDialog.dismiss()
                }

                if(it.status == ApiStatus.SUCCESS){

                    val strSimilarity = textOf(etSimilarity)
                    val strPartNumber = textOf(etPartNumber)
                    val strPartDeskripsi = textOf(etPartDeskripsi)


                    goTo<PartNumberFilterActivity>(requestCode = Constants.REQUEST.PART_NUMBER_SUGGEST_ORDER) {
                        putExtra(Constants.BUNDLE.PARTSEARCH.SIMILARITY,strSimilarity)
                        putExtra(Constants.BUNDLE.PARTSEARCH.PART_NUMBER,strPartNumber)
                        putExtra(Constants.BUNDLE.PARTSEARCH.PART_DESKRIPSI,strPartDeskripsi)
                        putExtra(Constants.BUNDLE.PARTSEARCH.JSON_BARANG,itemGroupSelected.getString())
                        putExtra(Constants.BUNDLE.PARTSEARCH.JSON_MOTOR,motorSelected.getString())
                        putExtra(Constants.BUNDLE.PARTSEARCH.JSON_DEALER,dealerSelected.getString())
                    }
                }

            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == Constants.REQUEST.PART_NUMBER_BARANG_SEARCH && resultCode == Constants.RESULT.PART_NUMBER_BARANG_SEARCH){

            itemGroupSelected = data!!.getStringExtra(Constants.BUNDLE.JSON)!!.getObject()

            spKelompokBarang text itemGroupSelected?.name + " - " + itemGroupSelected?.code

        }else if(requestCode == Constants.REQUEST.PART_NUMBER_MOTOR_SEARCH && resultCode == Constants.RESULT.PART_NUMBER_MOTOR_SEARCH){

            motorSelected = data!!.getStringExtra(Constants.BUNDLE.JSON)!!.getObject()

            spTypeMotor text motorSelected?.name

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_favorite, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite ->{
                goTo<FavoriteActivity> {
                    putExtra(Constants.BUNDLE.PARTSEARCH.JSON_DEALER,dealerSelected.getString())}
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initListener(){
        /**
         * set divider color
         */
        etSimilarity.div(vDivSimilarity, R.color.colorPrimary)
        etPartNumber.div(vDivPartNumber, R.color.colorPrimary)
        etPartDeskripsi.div(vDivPartDeskripsi, R.color.colorPrimary)
        spKelompokBarang.div(vDivKelompokBarang, R.color.colorPrimary)
        spTypeMotor.div(vDivTypeMotor, R.color.colorPrimary)


        btnSearch.setOnClickListener(this)
        spKelompokBarang.setOnClickListener(this)
        spTypeMotor.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            spKelompokBarang -> {
                goTo<KelompokBarangSearchActivity>(requestCode = Constants.REQUEST.PART_NUMBER_BARANG_SEARCH) {  }
            }
            spTypeMotor -> {
                goTo<TipeMotorSearchActivity>(requestCode = Constants.REQUEST.PART_NUMBER_MOTOR_SEARCH) {  }
            }
            btnSearch -> {

                val strSimilarity = textOf(etSimilarity)
                val strPartNumber = textOf(etPartNumber)
                val strPartDeskripsi = textOf(etPartDeskripsi)

                //if (isEmptyRequired(etSimilarity, etPartNumber,etPartDeskripsi)) return

//                if(barangSelected == null || motorSelected == null) {
//                    showToast(resources.getString(R.string.lbl_form_required))
//                    return
//                }

                viewModel.hitPartNumberSearch(strSimilarity,strPartNumber,strPartDeskripsi,
                    itemGroupSelected.id,
                    motorSelected.id,
                    "",
                    dealerSelected!!.id)
            }
        }
    }


}
