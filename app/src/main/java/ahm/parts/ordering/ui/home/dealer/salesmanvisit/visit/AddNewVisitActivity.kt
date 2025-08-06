package ahm.parts.ordering.ui.home.dealer.salesmanvisit.visit

import ahm.parts.ordering.R
import ahm.parts.ordering.api.ApiStatus
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dealer.salemanvisit.RencanaVisit
import ahm.parts.ordering.data.model.home.dashboard.partnumber.kodedealer.AllDealer
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.visit.kodedealer.KodeDealerActivity
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.visit.rencanavisit.RencanaVisitActivity
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.visit.salesvisit.SalesVisitActivity
import android.Manifest
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.model.LatLng
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import kotlinx.android.synthetic.main.activity_visit_add_new.*

class AddNewVisitActivity : BaseActivity<VisitViewModel>(), View.OnClickListener {

    var kodeDealer: AllDealer? = null
    var rencanaVisit: RencanaVisit? = null

    var userLocation: LatLng? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visit_add_new)

        initUI()
        initListener()

        observeData()
    }

    private fun initUI() {
        setToolbar(getString(R.string.lbl_title_add_new_visit), true)

        getLocationLiveData()
    }

    private fun observeData() {
        viewModel.apiResponse.observe(this, Observer {
            if (it.status == ApiStatus.LOADING) {
                loadingDialog.show(R.string.lbl_loading_harap_tunggu)
            } else {
                loadingDialog.dismiss()
                if (it.status != ApiStatus.SUCCESS) {
                    snacked(rootView, it)
                }
            }
        })

        viewModel.visitAdd.observe(this, Observer {

            loadingDialog.dismiss()
            goTo<SalesVisitActivity>(requestCode = Constants.REQUEST.VISIT_ADD) {
                putExtra(Constants.BUNDLE.DEALER.KODE_DEALER, kodeDealer.getString())
                putExtra(Constants.BUNDLE.DEALER.RENCANA_VISIT, rencanaVisit.getString())
                putExtra(Constants.BUNDLE.DEALER.VISIT_ADD, it.getString())
            }
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.REQUEST.KODE_DEALER && resultCode == Constants.RESULT.KODE_DEALER) {
            kodeDealer = data!!.extra(Constants.BUNDLE.JSON).getObject()

            etKodeDealer text kodeDealer!!.code
            etKodeDealerName text kodeDealer!!.name

        } else if (requestCode == Constants.REQUEST.RENCANA_VISIT && resultCode == Constants.RESULT.RENCANA_VISIT) {

            rencanaVisit = data!!.extra(Constants.BUNDLE.JSON).getObject()

            etRencanaVisit text CalendarUtils.setFormatDate(
                rencanaVisit!!.date,
                "yyyy-MM-dd",
                "d MMMM yyyy"
            )

        } else if (requestCode == Constants.REQUEST.VISIT_ADD && resultCode == Constants.RESULT.VISIT_ADD) {
            finish()
        }
    }

    private fun getLocationLiveData() {
        val locationLiveData = LocationLiveData.create(
            this,
            interval = (1000 * 60 * 10),
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY,
            expirationTime = 5000,
            fastestInterval = 100,
            maxWaitTime = 1000,
            numUpdates = 1,
            smallestDisplacement = 10f,
            onErrorCallback = object : LocationLiveData.OnErrorCallback {
                override fun onLocationSettingsException(e: ApiException) {
                    if (e is ResolvableApiException) {
                        try {
                            e.startResolutionForResult(this@AddNewVisitActivity, 1)
                        } catch (sendEx: IntentSender.SendIntentException) {
                        }
                    }
                }

                override fun onPermissionsMissing() {
                }
            }
        )
        locationLiveData.observe(this, Observer {
            if (it != null) {
                val latlongSekarang = LatLng(it.latitude, it.longitude)
                userLocation = latlongSekarang
                locationLiveData.removeObservers(this)
            }
        })
    }


    private fun initListener() {
        etKodeDealer.setOnClickListener(this)
        etRencanaVisit.setOnClickListener(this)
        btnNext.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            etKodeDealer -> {
                goTo<KodeDealerActivity>(requestCode = Constants.REQUEST.KODE_DEALER) { }
            }
            etRencanaVisit -> {
                if (kodeDealer == null) {
                    showToast(getString(R.string.lbl_select_dealer_first))
                } else {
                    goTo<RencanaVisitActivity>(requestCode = Constants.REQUEST.RENCANA_VISIT) {
                        putExtra(Constants.BUNDLE.JSON, kodeDealer.getString())
                    }
                }
            }
            btnNext -> {
                when{
                    kodeDealer == null || rencanaVisit == null -> {
                        showToast(resources.getString(R.string.lbl_form_required))
                    }
                    userLocation == null -> {
                        snacked(rootView,"Harap tunggu sedang mengambil lokasi anda")
                    }
                    else -> {
                        viewModel.hitVisitAdd(
                            "${userLocation?.latitude}",
                            "${userLocation?.longitude}",
                            kodeDealer!!,
                            rencanaVisit!!,
                            true
                        )
                    }
                }
            }
        }

    }
}


/*viewModel.hitVisitAdd(
    "${userLocation.latitude}",
    "${userLocation.longitude}",
    kodeDealer!!,
    rencanaVisit,
    true
)*/
