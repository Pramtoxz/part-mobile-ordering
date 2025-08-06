package ahm.parts.ordering.ui.home.dealer.collection

import ahm.parts.ordering.R
import ahm.parts.ordering.api.ApiStatus
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dashboard.partnumber.kodedealer.AllDealer
import ahm.parts.ordering.data.model.home.dealer.collection.CollectionParam
import ahm.parts.ordering.data.room.user.User
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.home.dealer.collection.kodedealer.KodeDealerActivity
import ahm.parts.ordering.ui.home.dealer.collection.list.CollectionActivityList
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_collection.*
import kotlinx.android.synthetic.main.activity_collection.rootView
import org.json.JSONArray
import org.json.JSONObject


class CollectionActivity : BaseActivity<CollectionViewModel>(), View.OnClickListener {
    var kodeDealer: AllDealer? = null
    lateinit var user : User


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection)
        initUI()
        initListener()
        observeData()
    }

    private fun initUI() {
        setToolbar(getString(R.string.lbl_title_collection), true)
        initAdapter()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.REQUEST.COLLECTION_KODE_DEALER && resultCode == Constants.RESULT.COLLECTION_KODE_DEALER) {
            kodeDealer = data!!.getStringExtra(Constants.BUNDLE.JSON)!!.getObject()
            etKodeDealerCol text kodeDealer!!.code
        }
    }

    private fun observeData() {
//        viewModel.apiResponse.observe(this, Observer {
//
//            if(it.status == ApiStatus.LOADING){
//                loadingDialog.show(R.string.dialog_lbl_please_wait)
//            }else{
//                if(it.flagLoadingDialog){
//                    loadingDialog.dismiss()
//                }
//
//                if(it.status == ApiStatus.SUCCESS){
//
//                    val kddealer = textOf(etKodeDealerCol)
//                    val strtglawal = textOf(etTglawal)
//                    val strtglakhir = textOf(etTglakhir)
//
//                    val formatBeginDateEffDate = CalendarUtils.setFormatDate(strtglawal,"MMMM dd, yyyy","yyyy-MM-dd")
//                    val formatLastDateEffDate = CalendarUtils.setFormatDate(strtglakhir,"MMMM dd, yyyy","yyyy-MM-dd")
//
//
//                    goTo<CollectionActivityList>(requestCode = Constants.REQUEST.COLLECTION_KODE_DEALER) {
//                        putExtra(Constants.BUNDLE.KODEDEALER,kddealer)
//                        putExtra(Constants.BUNDLE.START_TIME,formatBeginDateEffDate)
//                        putExtra(Constants.BUNDLE.END_TIME,formatLastDateEffDate)
//                    }
//                }
//
//            }
//        })
    }

    private fun initAdapter() {

    }

    private fun initListener() {
        etKodeDealerCol.setOnClickListener(this)
        btnSubmitCol.setOnClickListener(this)
        etTglakhir.setOnClickListener(this)
        etTglawal.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            btnSubmitCol -> {
                when{
                    etKodeDealerCol.isEmpty()->{
                        snacked(rootView, this!!.resources.getString(R.string.lbl_form_required))
                    }
                    etTglawal.isEmpty()->{
                        snacked(rootView,"Silahkan isi Periode Tanggal Awal Terlebih Dahulu !")
                    }
                    etTglakhir.isEmpty()->{
                        snacked(rootView,"Silahkan isi Periode Tanggal Akhir Terlebih Dahulu !")
                    }
                    else->{
                        val strkddealer = textOf(etKodeDealerCol)
                        val strtglawal = textOf(etTglawal)
                        val strtglakhir = textOf(etTglakhir)

                        val formatBeginDateEffDate = CalendarUtils.setFormatDate(strtglawal,"MMMM dd, yyyy","yyyy-MM-dd")
                        val formatLastDateEffDate = CalendarUtils.setFormatDate(strtglakhir,"MMMM dd, yyyy","yyyy-MM-dd")

                        val paramCollection = CollectionParam(
                            dealercollection = strkddealer,
                            startime = formatBeginDateEffDate,
                            endtime = formatLastDateEffDate
                        )

                        goTo<CollectionActivityList>{
                            putExtra(Constants.BUNDLE.PARAM,paramCollection.getString())
                        }
//                        Toast.makeText(this,"Check Data :"+strkddealer+formatBeginDateEffDate+formatLastDateEffDate,Toast.LENGTH_LONG).show()
                    }
                }
            }

            etKodeDealerCol ->{
                goTo<KodeDealerActivity>(requestCode = Constants.REQUEST.COLLECTION_KODE_DEALER) {
                }
            }
            etTglawal ->{
                datePickerWithOutMin {
                    etTglawal text CalendarUtils.setFormatDate(it,"dd MM yyyy","MMMM dd, yyyy")
                }
            }
            etTglakhir -> {
                datePickerWithOutMin {
                    etTglakhir text CalendarUtils.setFormatDate(it,"dd MM yyyy","MMMM dd, yyyy")
                }
            }

        }
    }
    fun createJSON(list : ArrayList<AllDealer>): String {
        val jsonArray = JSONArray()

        for (i in list.indices) {
            val item = list[i]

            val jsonObject = JSONObject()

            jsonObject.put("id", item.id.toInt())

            jsonArray.put(jsonObject)
        }
        val jsonSend = jsonArray.toString()
        Log.e("jsonSend", "" + jsonSend)
        return jsonSend
        //viewModel.hitSendMySocialActivity(jsonSend)
    }

}
