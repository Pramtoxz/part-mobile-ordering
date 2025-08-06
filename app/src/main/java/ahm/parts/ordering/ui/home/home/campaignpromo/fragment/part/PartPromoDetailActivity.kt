package ahm.parts.ordering.ui.home.home.campaignpromo.fragment.part

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.campaignpromo.PartPromo
import ahm.parts.ordering.data.model.home.campaignpromo.TypeMotor
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.helper.dialog.DialogHelper
import ahm.parts.ordering.ui.base.BaseActivity
import ahm.parts.ordering.ui.home.HomeViewModel
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_part_promo_detail.*
import kotlinx.android.synthetic.main.item_type_motor_part_detail.view.*
import java.util.ArrayList

class PartPromoDetailActivity : BaseActivity<HomeViewModel>(), View.OnClickListener {

    lateinit var partPromo: PartPromo

    lateinit var dialogHelper: DialogHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_part_promo_detail)

        initUI()
        initListener()

    }

    private fun initUI(){
        setToolbar(getString(R.string.lbl_title_part_details),true)

        dialogHelper = DialogHelper(this,true)

        partPromo = extra(Constants.BUNDLE.JSON).getObject<PartPromo>().apply {
            tvNamePart text "$code • $name"
            tvPartNumber text "$partNumber • $itemGroup"
            tvHet text StringMasker().addRp(het.toDouble())
            tvDiscount text "$discount%"
            tvBeginDate text CalendarUtils.setFormatDate(beginEffdate,"yyyy-MM-dd","MMMM dd, yyyy")
            tvEndDate text CalendarUtils.setFormatDate(endEffdate,"yyyy-MM-dd","MMMM dd, yyyy")
            tvNotePart text "*$note"

            tvHotlineFlag text hotlineFlag
            tvHotlineMaxQty text hotlineMaxQty.toString()
            tvKitFlag text kitFlag
            tvHgpTl text hgpAccTl
            tvDus text dus.toString()
            tvFlagNumber text flagNumbering

            rvTypeMotor.setAdapter(this@PartPromoDetailActivity,typeMotor as ArrayList<TypeMotor>,R.layout.item_type_motor_part_detail,{
                val item = typeMotor[it]

                tvTypeMotor text item.typeMotor

            },{

            })
        }

    }

    private fun initListener(){
        btnDetailPromo.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            btnDetailPromo -> {
                val dialog = dialogHelper.bottomSheet(R.layout.dialog_bottom_part_promo){}

                val tvPromoDesc = dialog.findViewById<TextView>(R.id.tvPromoDesc)!!
                val btnClosePromo = dialog.findViewById<Button>(R.id.btnClosePromo)!!

                tvPromoDesc text partPromo.description

                btnClosePromo.onClick {
                    dialog.dismiss()
                }
            }
        }
    }


}
