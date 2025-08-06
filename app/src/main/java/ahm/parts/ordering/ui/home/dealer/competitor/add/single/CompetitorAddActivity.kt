package ahm.parts.ordering.ui.home.dealer.competitor.add.single

import ahm.parts.ordering.R
import ahm.parts.ordering.api.ApiStatus
import ahm.parts.ordering.data.Session
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dashboard.partnumber.kodedealer.AllDealer
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.helper.dialog.DialogHelper
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.home.HomeViewModel
import ahm.parts.ordering.ui.home.dealer.competitor.CompetitorViewModel
import ahm.parts.ordering.ui.home.dealer.competitor.kodedealer.KodeDealerActivity
import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.lifecycle.Observer
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import kotlinx.android.synthetic.main.activity_competitor_add.*
import pl.aprilapps.easyphotopicker.*
import java.io.File
import javax.inject.Inject


class CompetitorAddActivity : BaseActivity<CompetitorViewModel>(), View.OnClickListener {

//    @Inject
//    lateinit var session: Session

    lateinit var dialogHelper: DialogHelper

    var kodeDealer: AllDealer? = null

    var filePhoto = File("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_competitor_add)

        dialogHelper = DialogHelper(this, true)

        initUI()
        initListener()

        observeData()
    }

    private fun initUI() {
        setToolbar(getString(R.string.lbl_title_add_report_competitor), true)
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
                            setResult(Constants.RESULT.COMPETITOR_ADD,Intent())
                            finish()
                        }
                    }
                }
            }

        })

    }

    private fun initListener() {
        etNamaKompetitor.div(vDivNamaKompetitor, R.color.colorPrimary)
        etJudulAktivitasKompetitor.div(vDivJudulAktivitasKompetitor, R.color.colorPrimary)
        etProduk.div(vDivProduk, R.color.colorPrimary)
        etKeteranganPromo.div(vDivKeteranganPromo, R.color.colorPrimary)

        etKodeDealer.setOnClickListener(this)
        etBeginEffDate.setOnClickListener(this)
        etLastEffDate.setOnClickListener(this)
        btnUploadCampaignImage.setOnClickListener(this)

        btnAddCompetitor.setOnClickListener(this)
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
                    if (type == Constants.REQUEST.PICK_IMAGE_ADD_COMPETITOR) {
                        filePhoto = imageFile

                        compressImage(imageFile) {

                            tvUploadImageCampaign text getString(R.string.lbl_campaign_image_upload)
                            tvUploadImageCampaign.setTextColor(resources.getColor(R.color.blue_sea))

                            filePhoto = it
                        }
                    }
                }


                override fun onCanceled(source: EasyImage.ImageSource?, type: Int) {
                    if (source == EasyImage.ImageSource.CAMERA) {
                        val photoFile =
                            EasyImage.lastlyTakenButCanceledPhoto(this@CompetitorAddActivity)
                        photoFile?.delete()
                    }
                }
            })
    }

    override fun onClick(v: View?) {
        when (v!!) {
            etKodeDealer -> {
                goTo<KodeDealerActivity>(requestCode = Constants.REQUEST.COMPETITOR_KODE_DEALER) { }
            }
            btnUploadCampaignImage -> {
                runWithPermissions(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) {
                    val bottomSheet = dialogHelper.bottomSheet(R.layout.dialog_bottom_camera) {}

                    val btnCamera = bottomSheet.findViewById<Button>(R.id.btnCamera)!!
                    val btnGallery = bottomSheet.findViewById<Button>(R.id.btnGallery)!!
                    val btnPreview = bottomSheet.findViewById<Button>(R.id.btnPreview)!!

                    btnCamera.onClick {
                        bottomSheet.dismiss()
                        EasyImage.openCamera(this, Constants.REQUEST.PICK_IMAGE_ADD_COMPETITOR)
                    }

                    btnGallery.onClick {
                        bottomSheet.dismiss()
                        EasyImage.openGallery(this, Constants.REQUEST.PICK_IMAGE_ADD_COMPETITOR)
                    }

                    if (filePhoto.path == "") {
                        btnPreview.hide()
                    } else {
                        btnPreview.show()
                    }

                    btnPreview.onClick {
                        bottomSheet.dismiss()
                        dialogSheetPreview(Uri.fromFile(filePhoto).toString())
                    }
                }
            }
            etBeginEffDate -> {
                datePicker {
                    etBeginEffDate text CalendarUtils.setFormatDate(it,"dd MM yyyy","MMMM dd, yyyy")
                }
            }
            etLastEffDate -> {
                datePicker {
                    etLastEffDate text CalendarUtils.setFormatDate(it,"dd MM yyyy","MMMM dd, yyyy")
                }
            }
            btnAddCompetitor -> {
                when {
                    isEmptyRequired(
                        etKodeDealer,
                        etNamaKompetitor,
                        etJudulAktivitasKompetitor,
                        etProduk,
                        etBeginEffDate,
                        etLastEffDate,
                        etKeteranganPromo
                    ) -> return
                    filePhoto.path == "" -> showToast(resources.getString(R.string.lbl_form_required))
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

                        viewModel.hitCompetitorAdd(
                            strCodeDealer,
                            session.idRole!!,
                            strNameKompetitor,
                            strJudulAktivitas,
                            strProduct,
                            formatBeginDateEffDate,
                            formatLastDateEffDate,
                            filePhoto,
                            strKeteranganPromo
                        )
                    }
                }


            }
        }
    }

}
