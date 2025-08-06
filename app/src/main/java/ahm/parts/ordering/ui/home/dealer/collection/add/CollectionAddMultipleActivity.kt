package ahm.parts.ordering.ui.home.dealer.collection.add

import ahm.parts.ordering.R
import ahm.parts.ordering.api.ApiStatus
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dealer.collection.Collection1
import ahm.parts.ordering.data.model.home.dealer.collection.CollectionPhoto
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.home.dealer.collection.CollectionNotification
import ahm.parts.ordering.ui.home.dealer.collection.CollectionViewModel
import ahm.parts.ordering.ui.home.dealer.collection.adapter.CollectionPhotoAdapter
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_collection.*
import kotlinx.android.synthetic.main.activity_collection_add_multiple.*
import kotlinx.android.synthetic.main.activity_collection_notification.*
import kotlinx.android.synthetic.main.item_collection_list.view.*
import org.json.JSONArray
import org.json.JSONObject
import pl.aprilapps.easyphotopicker.*
import java.io.File
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList


class CollectionAddMultipleActivity : BaseActivity<CollectionViewModel>(), View.OnClickListener {

    lateinit var collectionPhotoAdapter: CollectionPhotoAdapter
    lateinit var collection : Collection1
    var photos = ArrayList<CollectionPhoto>()
//    var kodeDealer: AllDealer? = null

    var totalPhotos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection_add_multiple)
        initUI()
        initListener()
        //observeData()
    }

    private fun initUI() {
        setToolbar(getString(R.string.lbl_title_collection_payment), true)
        collection = extra(Constants.BUNDLE.JSON).getObject()
        collection.apply {
            tvKodeDealerCollect text noTransaksi
            tvJumlahPcs text jumlahPcs
            tvDateBayar text CalendarUtils.setFormatDate(
                tglTransaksi,
                "yyyy-MM-dd",
                "MMMM dd, yyyy"
            ) + " - " + CalendarUtils.setFormatDate(
                tglTransaksi,
                "yyyy-MM-dd",
                "MMMM dd, yyyy"
            )
            tvJumlahBarang text jumlahItem
//            tvTotalPiutang text formatRupiah(totalBayar.toDouble()).toString()
            tvTotalPiutang.text = StringMasker().addRp(totalBayar.toDouble())
            tvTotalBayar.text = StringMasker().addRp(totalBayar.toDouble())
        }
        initAdapter()
    }


    private fun observeData(){
        viewModel.apiResponse.observe(this, Observer {
            showStateApiView(it){}

            if(it.status == ApiStatus.LOADING){
                when(it.flagView){
                    R.id.btnSubmitPayment -> {
                        loadingDialog.show(R.string.lbl_loading_harap_tunggu)
                    }
                }
            }else{
                loadingDialog.dismiss()
                if(it.status == ApiStatus.SUCCESS){
                    when(it.flagView){
                        R.id.btnSubmitPayment -> {
                            dialogSuccess(it.message[0].toString())
                            val kddealer = tvKodeDealerCollect.toString()
                            val totalb = collection.totalBayar
                            val data = this
                            goTo<CollectionNotification>{
                                putExtra(Constants.BUNDLE.JSON,kddealer)
                            }
                        }
                    }
                }
            }
        })

    }

    private fun initAdapter(){
        photos.add(CollectionPhoto(1,"",""))
        photos.add(CollectionPhoto(2,"",""))
        photos.add(CollectionPhoto(3,"",""))
        photos.add(CollectionPhoto(4,"",""))
        photos.add(CollectionPhoto(5,"",""))

        collectionPhotoAdapter = CollectionPhotoAdapter(photos,this){}

        rvPhoto.initItemHorizontal(this,collectionPhotoAdapter)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

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

                        collectionPhotoAdapter.notifyDataSetChanged()

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
                            EasyImage.lastlyTakenButCanceledPhoto(this@CollectionAddMultipleActivity)
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
                setResult(Constants.RESULT.COLLECTION_ADD,Intent())
                finish()
            }
        }
    }

    private fun initListener() {
        btnSubmitPayment.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v) {
//                    val data = this
//                    putExtra(Constants.BUNDLE.JSON, data.getString())

            btnSubmitPayment -> {
                val strnotran = textOf(tvKodeDealerCollect)
                val strbayar = textOf(tvTotalBayar)
                goTo<CollectionNotification>(requestCode = Constants.REQUEST.COLLECTION_KODE_DEALER){
                    putExtra(Constants.BUNDLE.NOTRANSAKSI,strnotran)
                    putExtra(Constants.BUNDLE.TOTALBAYAR,strbayar)
                }
//                val strIdTransaksi = textOf(tvKodeDealerCollect)
//                val strTanggalBayar = textOf(tvDateBayar)
//                val strTotalBayar = textOf(tvTotalBayar)
//
//                viewModel.hitCollectionAddMultiple(
//                    strIdTransaksi,
//                    strTanggalBayar,
//                    createJSON(photos),
//                    strTotalBayar
//                )

            }
        }
    }

    fun createJSON(list : ArrayList<CollectionPhoto>): String {
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
