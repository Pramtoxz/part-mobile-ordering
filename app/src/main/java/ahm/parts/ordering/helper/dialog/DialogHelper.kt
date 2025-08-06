package ahm.parts.ordering.helper.dialog

import ahm.parts.ordering.R
import ahm.parts.ordering.helper.*
import android.app.Activity
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.dialog_bottom_filter_stock.btnCancel
import kotlinx.android.synthetic.main.dialog_bottom_filter_stock.btnOk
import kotlinx.android.synthetic.main.dialog_bottom_kode_tipe_unit.*
import kotlinx.android.synthetic.main.dialog_bottom_selection_list.*

open class DialogHelper(val context: Activity, val isRequired : Boolean) {

    lateinit var adapter : DialogSelectionAdapter
    var selectionItem = -1
    lateinit var dialog: BottomSheetDialog

    fun dialogList(strTitle : String, items : ArrayList<*>,
                   positive: (Int) -> Unit, negative: () -> Unit) {

        val view = LayoutInflater.from(context).inflate(
            R.layout.dialog_bottom_selection_list,
            null
        )

        dialog = BottomSheetDialog(context, R.style.BottomSheetDialogTheme)
        dialog.setContentView(view)
        dialog.btnCancel?.setOnClickListener {
            dialog.dismiss()
        }

        dialog.tvTitle?.text = strTitle
        dialog.btnOk?.setOnClickListener {
            dialog.dismiss()
            positive(selectionItem)
        }

        if (isRequired){
            dialog.btnCancel?.hide()
        }else{
            dialog.btnCancel?.show()
        }

        dialog.btnCancel?.setOnClickListener {
            dialog.dismiss()
            selectionItem = -1
            adapter.selectedPosition = -1
            negative()
        }

        dialog.setOnShowListener {
            val bottomSheet = dialog.findViewById<FrameLayout>(R.id.design_bottom_sheet)
            BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED)
        }

        adapter = DialogSelectionAdapter(items, context){
            selectionItem = it
            adapter.selectedPosition = it
            adapter.notifyDataSetChanged()
        }


        adapter.notifyDataSetChanged()

        // init daftar tipe stock

        var filterMonths = ArrayList<String>()
        filterMonths.add("Januari 2019")
        filterMonths.add("Februari 2019")
        filterMonths.add("Maret 2019")
        filterMonths.add("April 2019")
        filterMonths.add("Mei 2019")
        filterMonths.add("Juni 2019")
        filterMonths.add("Juli 2019")
        filterMonths.add("Agustus 2019")
        filterMonths.add("September 2019")
        filterMonths.add("Oktober 2019")
        filterMonths.add("November 2019")
        filterMonths.add("Desember 2019")

        var calendarAdapter = dialog.rvList.setAdapter(context,filterMonths,R.layout.item_calendar_filter,{
            var item = filterMonths[it]

            var tvNameCalendar = this.findViewById<TextView>(R.id.tvNameCalendar)

            tvNameCalendar.text(item)

        },{

        })

        dialog.rvList.initItem(context, calendarAdapter)

        val vp = view.parent as View
        vp.setBackgroundColor(Color.TRANSPARENT)

        dialog.show()
    }
    
    fun dialogSearch(strTitle : String, items : ArrayList<*>, viewListener: (View) -> Unit, positive: (Int) -> Unit,
                     search: (String) -> Unit){
        var dialog: BottomSheetDialog? = null

        val view = LayoutInflater.from(context).inflate(
            R.layout.dialog_bottom_kode_tipe_unit,
            null
        )

        viewListener(view)

        dialog = BottomSheetDialog(context, R.style.BottomSheetDialogTheme)
        dialog.setContentView(view)
        dialog.tvTitleDialog.text = strTitle
        dialog.btnSave?.setOnClickListener {
            dialog.dismiss()
            positive(selectionItem)
        }

        dialog.etSearch onSubmitSearch {

            search(it)
            dialog.btnSave?.setBackgroundResource(R.drawable.bg_fill_btn_round_gray)
            dialog.btnSave?.setTextColor(ContextCompat.getColor(context, R.color.txt_black))
        }


        val vp = view.parent as View
        vp.setBackgroundColor(Color.TRANSPARENT)

        dialog?.show()
    }

    fun dialogCustom(resLayout : Int, getView : (View) -> Unit) : BottomSheetDialog{

        val view = LayoutInflater.from(context).inflate(
            resLayout,
            null
        )

        getView(view)

        dialog = BottomSheetDialog(context, R.style.BottomSheetDialogTheme)
        dialog.setContentView(view)
        dialog.btnCancel?.setOnClickListener {
            dialog.dismiss()
        }

        dialog.setOnShowListener {
            val bottomSheet = dialog.findViewById<FrameLayout>(R.id.design_bottom_sheet)
            BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED)
        }

        val vp = view.parent as View
        vp.setBackgroundColor(Color.TRANSPARENT)

        return dialog
    }

    fun bottomSheet(resLayout : Int, getView : (View) -> Unit) : BottomSheetDialog{

        val view = LayoutInflater.from(context).inflate(
            resLayout,
            null
        )

        getView(view)

        dialog = BottomSheetDialog(context, R.style.BottomSheetDialogTheme)
        dialog.setContentView(view)
        dialog.btnCancel?.setOnClickListener {
            dialog.dismiss()
        }

        dialog.setOnShowListener {
            val bottomSheet = dialog.findViewById<FrameLayout>(R.id.design_bottom_sheet)
            BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED)
        }

        val vp = view.parent as View
        vp.setBackgroundColor(Color.TRANSPARENT)

        dialog.show()

        return dialog
    }
}