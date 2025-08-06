package ahm.parts.ordering.ui.home.home.ordersugestion

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dashboard.partnumber.AllKelompokBarang
import ahm.parts.ordering.data.model.home.dashboard.partnumber.AllTypeMotor
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.home.HomeViewModel
import ahm.parts.ordering.ui.home.home.ordersugestion.cekstockpartfilter.CheckStockFilterActivity
import ahm.parts.ordering.ui.home.home.partnumber.kelompokbarang.KelompokBarangSearchActivity
import ahm.parts.ordering.ui.home.home.partnumber.tipemotor.TipeMotorSearchActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_part_number_search.*

class CheckStockSearchActivity : BaseActivity<HomeViewModel>(), View.OnClickListener {

    var barangSelected : AllKelompokBarang? = null
    var motorSelected : AllTypeMotor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_part_number_search)

        initUI()
        initListener()

    }

    private fun initUI(){
        setToolbar(getString(R.string.lbl_title_search_parts),true)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == Constants.REQUEST.PART_NUMBER_BARANG_SEARCH && resultCode == Constants.RESULT.PART_NUMBER_BARANG_SEARCH){

            barangSelected = data!!.getStringExtra(Constants.BUNDLE.JSON)!!.getObject()

            spKelompokBarang text barangSelected?.name + " - " + barangSelected?.code

        }else if(requestCode == Constants.REQUEST.PART_NUMBER_MOTOR_SEARCH && resultCode == Constants.RESULT.PART_NUMBER_MOTOR_SEARCH){

            motorSelected = data!!.getStringExtra(Constants.BUNDLE.JSON)!!.getObject()

            spTypeMotor text motorSelected?.name

        }
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

                if (isEmptyRequired(etSimilarity, etPartNumber,etPartDeskripsi)) return

                if(barangSelected == null || motorSelected == null) {
                    showToast(resources.getString(R.string.lbl_form_required))
                    return
                }

                goTo<CheckStockFilterActivity> {
                    putExtra(Constants.BUNDLE.PARTSEARCH.SIMILARITY,strSimilarity)
                    putExtra(Constants.BUNDLE.PARTSEARCH.PART_NUMBER,strPartNumber)
                    putExtra(Constants.BUNDLE.PARTSEARCH.PART_DESKRIPSI,strPartDeskripsi)
                    putExtra(Constants.BUNDLE.PARTSEARCH.JSON_BARANG,barangSelected.getString())
                    putExtra(Constants.BUNDLE.PARTSEARCH.JSON_MOTOR,motorSelected.getString())
                }
            }
        }
    }


}
