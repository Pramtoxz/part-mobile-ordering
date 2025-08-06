package ahm.parts.ordering.ui.home.home.partnumber.detail

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dashboard.partnumber.PartNumberFilter
import ahm.parts.ordering.data.model.home.dashboard.partnumber.TypeMotorX
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.home.HomeViewModel
import android.os.Bundle
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_part_number_detail.*
import kotlinx.android.synthetic.main.item_type_motor_part_detail.view.*
import java.util.ArrayList

class PartNumberDetailActivity : BaseActivity<HomeViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_part_number_detail)

        initUI()

    }

    private fun initUI(){

        setToolbar(getString(R.string.lbl_title_part_details),true)

        extra(Constants.BUNDLE.JSON).getObject<PartNumberFilter>().apply {

            tvPartNumber text partNumber
            tvPartDescription text partDescription
            tvItemGroup text itemGroup
            tvHet text StringMasker().addRp(het.toDouble())
            tvAvailabePcs text availablePart

            if(isPromo == 1){
                ivDiscount.show()
            }else{
                ivDiscount.hide()
            }

            tvHotlineFlag text hotlineFlag
            tvHotlineMaxQty text hotlineMaxQty.toString()
            tvKitFlag text kitFlag
            tvHgpTl text hgpAccTl
            tvDus text dus.toString()
            tvFlagNumber text flagNumbering

            rvTypeMotor.setAdapter(this@PartNumberDetailActivity,typeMotor as ArrayList<TypeMotorX>,R.layout.item_type_motor_part_detail,{
                val item = typeMotor[it]

                tvTypeMotor text item.typeMotor

            },{})

        }

        viewModel.getUser().observe(this, Observer {
            try {
                when(it!!.id_role){
                    Constants.ROLE.NONCHANNEL -> {
                        tvAvailabePcs.hide()
                    }
                    Constants.ROLE.DEALER -> {
                        tvAvailabePcs.hide()
                    }
                }
            } catch (e: Exception) {
            }
        })
    }

}
