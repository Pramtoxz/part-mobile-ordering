package ahm.parts.ordering.ui.home.dealer.competitor.add.multiple

import ahm.parts.ordering.R
import ahm.parts.ordering.api.ApiStatus
import ahm.parts.ordering.data.Session
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dashboard.partnumber.kodedealer.AllDealer
import ahm.parts.ordering.data.model.home.dealer.competitor.CompetitorPhoto
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.helper.dialog.DialogHelper
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.home.HomeViewModel
import ahm.parts.ordering.ui.home.dealer.competitor.CompetitorViewModel
import ahm.parts.ordering.ui.home.dealer.competitor.adapter.CompetitorPhotoAdapter
import ahm.parts.ordering.ui.home.dealer.competitor.kodedealer.KodeDealerActivity
import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.Observer
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import kotlinx.android.synthetic.main.activity_competitor_add_multiple.*
import org.json.JSONArray
import org.json.JSONObject
import pl.aprilapps.easyphotopicker.*
import java.io.File
import javax.inject.Inject


class CompetitorAddMultipleActivity : BaseActivity<CompetitorViewModel>(), View.OnClickListener {

    lateinit var competitorPhotoAdapter: CompetitorPhotoAdapter

    var photos = ArrayList<CompetitorPhoto>()
    var kodeDealer: AllDealer? = null

    var totalPhotos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_competitor_add_multiple)

        initUI()
        initListener()

        observeData()
    }

    private fun initUI() {
        setToolbar(getString(R.string.lbl_title_add_report_competitor), true)

        initAdapter()
    }

    private fun observeData(){
        viewModel.apiResponse.observe(this, Observer {
            showStateApiView(it){}

            if(it.status == ApiStatus.LOADING){
                when(it.flagView){
                    R.id.btnAddCompetitor -> {
                        loadingDialog.show(R.string.lbl_loading_harap_tunggu)
                    }
                }
            }else{
                loadingDialog.dismiss()
                if(it.status == ApiStatus.SUCCESS){
                    when(it.flagView){
                        R.id.btnAddCompetitor -> {
                            dialogSuccess(it.message[0].toString())
                        }
                    }
                }
            }

        })

    }

    private fun initAdapter(){
        photos.add(CompetitorPhoto(1,"",""))
        photos.add(CompetitorPhoto(2,"",""))
        photos.add(CompetitorPhoto(3,"",""))
        photos.add(CompetitorPhoto(4,"",""))
        photos.add(CompetitorPhoto(5,"",""))

        competitorPhotoAdapter = CompetitorPhotoAdapter(photos,this){}

        rvPhoto.initItemHorizontal(this,competitorPhotoAdapter)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.REQUEST.COMPETITOR_KODE_DEALER && resultCode == Constants.RESULT.COMPETITOR_KODE_DEALER) {
            kodeDealer = data!!.getStringExtra(Constants.BUNDLE.JSON)!!.getObject()

            etKodeDealer text kodeDealer!!.code

        }
        EasyImage.handleActivityResult(
            requestCode,
            resultCode,
            data,
            this,
            object : DefaultCallback() {
                override fun onImagePickerError(
                    e: Exception?,
                    source: EasyImage.ImageSource?,
                    type: Int
                ) {
                    e!!.printStackTrace()
                }

                override fun onImagePicked(
                    imageFile: File,
                    source: EasyImage.ImageSource,
                    type: Int
                ) {

                    Log.e("typeImagePicked", "$type")
                    compressImage(imageFile) {

                        photos[(type - 1)].photoPath = Uri.fromFile(imageFile).toString()
                        photos[(type - 1)].base64Encode = encodeBase64(imageFile.absolutePath)!!

                        competitorPhotoAdapter.notifyDataSetChanged()

                        totalPhotos = 0
                        photos.forEach {
                            if (it.photoPath != "") {
                                totalPhotos += 1
                            }
                        }

                        tvSizePhoto text "$totalPhotos/5 Photos Uploaded"


                    }

                }


                override fun onCanceled(source: EasyImage.ImageSource?, type: Int) {
                    if (source == EasyImage.ImageSource.CAMERA) {
                        val photoFile =
                            EasyImage.lastlyTakenButCanceledPhoto(this@CompetitorAddMultipleActivity)
                        photoFile?.delete()
                    }
                }
            })
    }


    private fun dialogSuccess(apiMessage : String){
        bottomSheet(R.layout.dialog_bottom_alert_forgot){
            val tvMessage = it.findViewById<TextView>(R.id.tvMessage)
            val btnOk = it.findViewById<AppCompatButton>(R.id.btnOk)

            tvMessage text apiMessage

            btnOk.onClick {
                setResult(Constants.RESULT.COMPETITOR_ADD,Intent())
                finish()
            }
        }
    }

    private fun initListener() {
        etNamaKompetitor.div(vDivNamaKompetitor, R.color.colorPrimary)
        etJudulAktivitasKompetitor.div(vDivJudulAktivitasKompetitor, R.color.colorPrimary)
        etProduk.div(vDivProduk, R.color.colorPrimary)
        etKeteranganPromo.div(vDivKeteranganPromo, R.color.colorPrimary)

        etKodeDealer.setOnClickListener(this)
        etBeginEffDate.setOnClickListener(this)
        etLastEffDate.setOnClickListener(this)

        btnAddCompetitor.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v!!) {
            etKodeDealer -> {
                goTo<KodeDealerActivity>(requestCode = Constants.REQUEST.COMPETITOR_KODE_DEALER) { }
            }
            etBeginEffDate -> {
                datePickerWithOutMin {
                    etBeginEffDate text CalendarUtils.setFormatDate(it,"dd MM yyyy","MMMM dd, yyyy")
                }
            }
            etLastEffDate -> {
                datePickerWithOutMin {
                    etLastEffDate text CalendarUtils.setFormatDate(it,"dd MM yyyy","MMMM dd, yyyy")
                }
            }
            btnAddCompetitor -> {
                when {
                    isEmptyRequired(
                        etNamaKompetitor,
                        etJudulAktivitasKompetitor,
                        etProduk,
                        etBeginEffDate,
                        etLastEffDate,
                        etKeteranganPromo
                    ) -> return
                    totalPhotos == 0 -> showToast("Fill the photos empty")
                    kodeDealer == null -> showToast(resources.getString(R.string.lbl_form_required))
                    else -> {
                        val strCodeDealer = kodeDealer!!.code
                        val strNameKompetitor = textOf(etNamaKompetitor)
                        val strJudulAktivitas = textOf(etJudulAktivitasKompetitor)
                        val strProduct = textOf(etProduk)
                        val strBeginEffDate = textOf(etBeginEffDate)
                        val strLastEffDate = textOf(etLastEffDate)
                        val strKeteranganPromo = textOf(etKeteranganPromo)

                        val formatBeginDateEffDate = CalendarUtils.setFormatDate(strBeginEffDate,"MMMM dd, yyyy","yyyy-MM-dd")
                        val formatLastDateEffDate = CalendarUtils.setFormatDate(strLastEffDate,"MMMM dd, yyyy","yyyy-MM-dd")

                        viewModel.hitCompetitorAddMultiple(
                            strCodeDealer,
                            session.idRole!!,
                            strNameKompetitor,
                            strJudulAktivitas,
                            strProduct,
                            formatBeginDateEffDate,
                            formatLastDateEffDate,
                            createJSON(photos),
                            strKeteranganPromo
                        )
                    }
                }


            }
        }
    }

    fun createJSON(list : ArrayList<CompetitorPhoto>): String {
        val jsonArray = JSONArray()


        for (i in list.indices) {
            val item = list[i]

            if(item.photoPath != ""){
                val jsonObject = JSONObject()

                jsonObject.put("photo", item.base64Encode)

                jsonArray.put(jsonObject)
            }

        }
        val jsonSend = jsonArray.toString()
        Log.e("jsonSend", "" + jsonSend)
        return jsonSend
        //viewModel.hitSendMySocialActivity(jsonSend)
    }


}
