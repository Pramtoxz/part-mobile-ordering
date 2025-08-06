package ahm.parts.ordering.ui.home.dealer.salesmanvisit.addnewdealer

import ahm.parts.ordering.R
import ahm.parts.ordering.api.ApiStatus
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseActivity
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import kotlinx.android.synthetic.main.activity_add_new_dealer.*
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import java.io.File
import java.io.IOException
import java.util.*

class AddNewDealerActivity : BaseActivity<AddNewDealerViewModel>(), View.OnClickListener
    , OnMapReadyCallback {


    private var googleMap: GoogleMap? = null

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var resultReceiver: AddressResultReceiver

    private var selectedLocation: LatLng? = null
    private var fileImage: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_dealer)

        initUI()
        initListener()

        observeData()
    }

    private fun initUI() {
        setToolbar(getString(R.string.lbl_title_add_new_dealer), true)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        initializeMap()

        ivBlock.setOnTouchListener { v: View?, event: MotionEvent ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    scrollView.requestDisallowInterceptTouchEvent(true)
                    return@setOnTouchListener false
                }
                MotionEvent.ACTION_UP -> {
                    scrollView.requestDisallowInterceptTouchEvent(false)
                    return@setOnTouchListener true
                }
                MotionEvent.ACTION_MOVE -> {
                    scrollView.requestDisallowInterceptTouchEvent(true)
                    return@setOnTouchListener false
                }
                else -> return@setOnTouchListener true
            }
        }
    }

    private fun observeData(){
        viewModel.apiResponse.observe(this, Observer {
            showStateApiView(it){}

            if(it.status == ApiStatus.LOADING){
                when(it.flagView){
                    R.id.btnAddDealer -> {
                        loadingDialog.show(R.string.lbl_loading_harap_tunggu)
                    }
                }
            }else{
                loadingDialog.dismiss()
                if(it.status == ApiStatus.SUCCESS){
                    when(it.flagView){
                        R.id.btnAddDealer -> {
                            dialogSuccess(it.message[0].toString())
                        }
                    }
                }
            }

        })
    }


    override fun onMapReady(gmap: GoogleMap?) {
        googleMap = gmap

        if (selectedLocation == null) {
            showUserLocation()
        } else {
            googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedLocation, 17f))
        }

        googleMap?.setOnCameraMoveListener {
            centerMarker.alpha = 0.25F

        }
        googleMap?.setOnCameraIdleListener {
            centerMarker.alpha = 1.0F

            selectedLocation = googleMap?.cameraPosition?.target

            etLatLong text "${selectedLocation?.longitude}" + ","+ "${selectedLocation?.latitude}"

            startIntentService()
        }
    }

    private fun startIntentService() {
        resultReceiver = AddressResultReceiver(Handler())

        val intent = Intent(this, AddressService::class.java).apply {
            putExtra(Constants.LOCATIONPICKER.RECEIVER, resultReceiver)
            putExtra(Constants.LOCATIONPICKER.LOCATION_DATA_EXTRA, selectedLocation)
        }
        startService(intent)
    }

    private fun showUserLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                val geocoder =  Geocoder(this, Locale.getDefault());

                try {
                    val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1);
                    val obj = addresses.get(0);
                    var add = obj.getAddressLine(0);
                    add = add + "\n" + obj.getCountryName();
                    add = add + "\n" + obj.getCountryCode();
                    add = add + "\n" + obj.getAdminArea();
                    add = add + "\n" + obj.getPostalCode();
                    add = add + "\n" + obj.getSubAdminArea();
                    add = add + "\n" + obj.getLocality();
                    add = add + "\n" + obj.getSubThoroughfare();

                    // Toast.makeText(this, "Address=>" + add,
                    // Toast.LENGTH_SHORT).show();

                    // TennisAppActivity.showDialog(add);
                } catch (e : IOException) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    /*showToast(e.message!!)*/
                }

                selectedLocation = LatLng(location.latitude, location.longitude)

                etLatLong text "${selectedLocation?.longitude}" + ","+ "${selectedLocation?.latitude}"

                googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedLocation, 17f))
            }
        }
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

    internal inner class AddressResultReceiver(handler: Handler) : ResultReceiver(handler) {

        override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {

            val addressOutput = resultData?.getString(Constants.LOCATIONPICKER.RESULT_DATA_KEY) ?: ""

            // Show a toast message if an customersAddress was found.
            if (resultCode == Constants.LOCATIONPICKER.SUCCESS_RESULT) {
                etAddress text addressOutput
            }

        }
    }

    private fun initializeMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.fMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
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

                    if(type == Constants.REQUEST.PHOTO_ADD_DEALER){

                        compressImage(imageFile) {
                            fileImage = it

                            ivPhotoAdd.setImageResource(R.drawable.ic_photo_white)

                            ivPhoto loadImage Uri.fromFile(fileImage).toString()

                        }
                    }
                }

                override fun onCanceled(source: EasyImage.ImageSource?, type: Int) {
                    if (source == EasyImage.ImageSource.CAMERA) {
                        val photoFile =
                            EasyImage.lastlyTakenButCanceledPhoto(this@AddNewDealerActivity)
                        photoFile?.delete()
                    }
                }
            })
    }

    private fun initListener() {
        btnAddDealer.setOnClickListener(this)
        ivPhoto.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!) {
            ivPhoto -> {
                runWithPermissions(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) {
                    val bottomSheet = bottomSheet(R.layout.dialog_bottom_camera) {}

                    val btnCamera = bottomSheet.findViewById<Button>(R.id.btnCamera)!!
                    val btnGallery = bottomSheet.findViewById<Button>(R.id.btnGallery)!!
                    val btnPreview = bottomSheet.findViewById<Button>(R.id.btnPreview)!!

                    btnCamera.onClick {
                        bottomSheet.dismiss()
                        EasyImage.openCamera(this, Constants.REQUEST.PHOTO_ADD_DEALER)
                    }

                    btnGallery.onClick {
                        bottomSheet.dismiss()
                        EasyImage.openGallery(this, Constants.REQUEST.PHOTO_ADD_DEALER)
                    }

                    if (fileImage == null) {
                        btnPreview.hide()
                    } else {
                        btnPreview.show()
                    }

                    btnPreview.onClick {
                        bottomSheet.dismiss()
                        dialogSheetPreview(Uri.fromFile(fileImage).toString())
                    }
                }
            }
            btnAddDealer -> {
                val latLong = "${selectedLocation?.latitude}" + "," + "${selectedLocation?.longitude}"
                when {
                    isEmptyRequired(etNameDealer, etLatLong, etAddress, etPhone, etDescription) -> {
                    }
                    fileImage == null -> {
                        snacked(
                            rootView,
                            getString(R.string.lbl_warning_isikan_photo_terlebih_dahulu)
                        )
                    }
                    else -> {
                        viewModel.addNewDealer(
                            textOf(etNameDealer),
                            latLong,
                            textOf(etAddress),
                            fileImage!!,
                            textOf(etPhone),
                            textOf(etDescription)
                        )
                    }
                }
            }
        }
    }

}
