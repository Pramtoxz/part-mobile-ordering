package ahm.parts.ordering.ui.home.dealer.salesmanvisit

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.helper.goTo
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.home.HomeViewModel
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.addnewdealer.AddNewDealerActivity
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.efektivitas.EfektivitasActivity
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.realisasivisit.RealisasiVisitActivity
import ahm.parts.ordering.ui.home.dealer.salesmanvisit.visit.AddNewVisitActivity
import android.Manifest
import android.os.Bundle
import android.view.View
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import kotlinx.android.synthetic.main.activity_salesman_visit.*

class SalesmanVisitActivity : BaseActivity<HomeViewModel>(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salesman_visit)

        initUI()
        initListener()
    }

    private fun initUI(){
        setToolbar(getString(R.string.lbl_title_salesman_visit),true)
    }

    private fun initListener(){
        btnVisit.setOnClickListener(this)
        btnRealisasiVisit.setOnClickListener(this)
        btnEfektivitas.setOnClickListener(this)
        btnAddNewDealer.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            btnVisit -> {
                runWithPermissions(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) {
                    if(isActiveGps()){
                        goTo<AddNewVisitActivity>(requestCode = Constants.REQUEST.VISIT_ADD) {  }
                    }
                }
            }
            btnRealisasiVisit -> {
                goTo<RealisasiVisitActivity> {  }
            }
            btnEfektivitas -> {
                goTo<EfektivitasActivity> {  }
            }
            btnAddNewDealer -> {
                runWithPermissions(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) {
                    if(isActiveGps()){
                        goTo<AddNewDealerActivity> {  }
                    }
                }
            }
        }
    }

}
