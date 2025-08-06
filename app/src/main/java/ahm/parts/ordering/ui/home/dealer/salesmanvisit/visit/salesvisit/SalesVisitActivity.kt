package ahm.parts.ordering.ui.home.dealer.salesmanvisit.visit.salesvisit

import ahm.parts.ordering.R
import ahm.parts.ordering.api.ApiStatus
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dealer.salemanvisit.RencanaVisit
import ahm.parts.ordering.data.model.home.dashboard.partnumber.kodedealer.AllDealer
import ahm.parts.ordering.data.model.home.dealer.salemanvisit.VisitAdd
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.home.HomeActivity
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.visit.VisitViewModel
import android.location.Location
import android.os.Bundle
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import com.binaryfork.spanny.Spanny
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.maps.model.LatLngBounds
import kotlinx.android.synthetic.main.activity_sales_visit_map.*
import java.util.ArrayList


class SalesVisitActivity : BaseActivity<VisitViewModel>(), OnMapReadyCallback,
    GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener,
    View.OnClickListener {

    lateinit var dealer: AllDealer
    lateinit var rencanaVisit: RencanaVisit
    lateinit var visitAdd: VisitAdd

    var userLocation = LatLng(0.0, 0.0)

    var markerList = ArrayList<Marker>()

    var codeVisit = ""

    var isCheckin = false

    lateinit var markerUser: Marker
    lateinit var markerDealer: Marker

    lateinit var mapFragment: SupportMapFragment
    private var locationRequest: LocationRequest? = null
    private var googleApiClient: GoogleApiClient? = null
    lateinit var fusedLocationClient: FusedLocationProviderClient
    private var googleMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sales_visit_map)

        dealer = extra(Constants.BUNDLE.DEALER.KODE_DEALER).getObject()
        rencanaVisit = extra(Constants.BUNDLE.DEALER.RENCANA_VISIT).getObject()
        visitAdd = extra(Constants.BUNDLE.DEALER.VISIT_ADD).getObject()

        initUI()
        initListener()

        observeData()
    }

    private fun initUI() {
        setToolbar(getString(R.string.lbl_title_sales_visit), true)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        tvDealer text dealer.code + " - " + dealer.name
        tvAddress text dealer.address

        mapFragment = supportFragmentManager
            .findFragmentById(R.id.fMap) as SupportMapFragment
        mapFragment.getMapAsync(this)


        visitAdd.apply {
            var color = 0

            viewBottom.show()
            if (this.status == 1) {
                btnCheckin text getString(R.string.btn_checkin)

                codeVisit = this.codeVisit

                isCheckin = true

                color = resources.getColor(R.color.green)
                vRetryContent.hide()
            } else {
                btnCheckin text getString(R.string.btn_coba_lagi)

                codeVisit = ""

                isCheckin = false

                color = resources.getColor(R.color.red_amount_order)
                vRetryContent.show()
            }

            val spannable = Spanny()
                .append("Checkin • jarak ")
                .append(this.distance + " meter ", ForegroundColorSpan(color))
                .append("dari bengkel")

            tvJarak.text = spannable
        }
    }

    private fun observeData() {
        viewModel.apiResponse.observe(this, Observer {
            if (it.status == ApiStatus.LOADING) {
                if (it.flagLoadingDialog) {
                    loadingDialog.show(R.string.lbl_loading_harap_tunggu)
                }
            } else {
                if (it.flagLoadingDialog) {
                    loadingDialog.dismiss()
                }

                if (it.status == ApiStatus.SUCCESS) {
                    when (it.flagView) {
                        R.id.btnCheckin -> {
                            goTo<HomeActivity> {  }
                            finishAffinity()
                           /* setResult(Constants.RESULT.VISIT_ADD) {}*/
                        }
                    }
                } else if (it.status == ApiStatus.ERROR) {
                    snacked(rootView, it)
                }

            }
        })

        viewModel.visitAdd.observe(this, Observer {

            var color = 0

            viewBottom.show()
            if (it.status == 1) {
                btnCheckin text getString(R.string.btn_checkin)

                codeVisit = it.codeVisit

                isCheckin = true

                color = resources.getColor(R.color.green)
                vRetryContent.hide()
            } else {
                btnCheckin text getString(R.string.btn_coba_lagi)

                codeVisit = ""

                isCheckin = false

                color = resources.getColor(R.color.red_amount_order)
                vRetryContent.show()
            }

            val spannable = Spanny()
                .append("Checkin • jarak ")
                .append(it.distance + " meter ", ForegroundColorSpan(color))
                .append("dari bengkel")

            tvJarak.text = spannable
        })
    }

    override fun onMapReady(gMap: GoogleMap) {
        googleMap = gMap!!

        buildGoogleApiClient()
        showUserLocation()
    }

    private fun showUserLocation() {

        fusedLocationClient.getLastLocation().addOnSuccessListener { location ->
            if (location != null) {
                userLocation = LatLng(location!!.getLatitude(), location!!.getLongitude())

                googleMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 17f))

                try {
                    if (markerUser != null) {
                        markerUser.remove()
                    }
                } catch (e: Exception) {
                }


                markerUser = googleMap!!.addMarker(
                    MarkerOptions()
                        .position(userLocation)
                        .anchor(0.5f, 0.5f)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pointer_sales))
//                        .title("User Position")
                )

                val dealerLocation = LatLng(dealer.latitude.toDouble(), dealer.longitude.toDouble())

                markerDealer = googleMap!!.addMarker(
                    MarkerOptions()
                        .position(dealerLocation)
                        .anchor(0.5f, 0.5f)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pointer_dealer))
//                        .title("User Position")
                )

                markerList.add(markerUser)
                markerList.add(markerDealer)

              /*  viewModel.hitVisitAdd(
                    "${userLocation.latitude}",
                    "${userLocation.longitude}",
                    dealer,
                    rencanaVisit,
                    true
                )*/

//                try {
                val builder = LatLngBounds.Builder()

                builder.include(markerUser.position)
                markerList.forEach {
                    builder.include(it.position)
                }

                val bounds = builder.build()

                googleMap!!.setOnMapLoadedCallback {
                    googleMap!!.animateCamera(
                        CameraUpdateFactory.newLatLngBounds(
                            bounds,
                            30
                        )
                    )
                }
//                } catch (e: Exception) {
//                }

//                viewModel.hitMap(
//                    location!!.getLatitude().toString(),
//                    location!!.getLongitude().toString()
//                )

//                val cameraUpdate = CameraUpdateFactory.newLatLngZoom(userLocation, 19f)
//                googleMap!!.moveCamera(cameraUpdate)
            }
        }
    }

    @Synchronized
    protected fun buildGoogleApiClient() {
        googleApiClient = GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build()

        googleApiClient!!.connect()
    }

    override fun onConnected(bundle: Bundle?) {
        locationRequest = LocationRequest()
        locationRequest!!.interval = 5000
        locationRequest!!.fastestInterval = 3000
        locationRequest!!.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY

        LocationServices.FusedLocationApi.requestLocationUpdates(
            googleApiClient,
            locationRequest,
            this
        )
    }

    override fun onLocationChanged(location: Location?) {
        if (location != null) {
            userLocation = LatLng(location!!.getLatitude(), location!!.getLongitude())

//            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 17f))

            try {
                if (markerUser != null) {
                    markerUser.remove()
                }
            } catch (e: Exception) {
            }

            markerUser = googleMap!!.addMarker(
                MarkerOptions()
                    .position(userLocation)
                    .anchor(0.5f, 0.5f)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pointer_sales))
//                    .title("User Position")
            )

            /*viewModel.hitVisitAdd(
                "${userLocation.latitude}",
                "${userLocation.longitude}",
                dealer,
                rencanaVisit,
                false
            )*/

        }

        val builder = LatLngBounds.Builder()

        builder.include(markerUser.position)
        markerList.forEach {
            builder.include(it.position)
        }

        val bounds = builder.build()

        googleMap!!.setOnMapLoadedCallback {
            googleMap!!.animateCamera(
                CameraUpdateFactory.newLatLngBounds(
                    bounds,
                    30
                )
            )
        }
    }

    override fun onConnectionSuspended(i: Int) {

    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {

    }

    private fun initListener() {
        btnCheckin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!) {
            btnCheckin -> {
                if (!codeVisit.equals("", ignoreCase = true)) {
                    bottomSheet(R.layout.dialog_bottom_visit_add_confirmation) {
                        val tvDialogTitle = it.findViewById<TextView>(R.id.tvDialogTitle)!!
                        val tvDialogMessage = it.findViewById<TextView>(R.id.tvDialogMessage)!!

                        val btnNo = it.findViewById<Button>(R.id.btnNo)!!
                        val btnCheckout = it.findViewById<Button>(R.id.btnCheckout)!!

                        tvDialogTitle.hide()

                        tvDialogMessage text "Apakah Anda ingin melakukan checkin\n salesman visit?"
                        btnCheckout text "Ya, Checkin"


                        btnNo.onClick { this.dismiss() }

                        btnCheckout.onClick {
                            this.dismiss()
                            viewModel.hitCheckin(codeVisit)
                        }

                    }
                } else {
                    viewModel.hitVisitAdd(
                        "${userLocation.latitude}",
                        "${userLocation.longitude}",
                        dealer,
                        rencanaVisit,
                        false
                    )
                }
            }
        }
    }

}
