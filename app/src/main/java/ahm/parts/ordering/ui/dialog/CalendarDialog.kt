package ahm.parts.ordering.ui.dialog

import ahm.parts.ordering.R
import ahm.parts.ordering.data.model.home.order.TrackingFilter
import ahm.parts.ordering.data.model.home.order.TrackingSorting
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.adapter.RecyclerAdapter
import android.app.Dialog
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.item_sorting_order.view.*

/**
 * Created by nuryazid on 4/20/18.
 */

class CalendarDialog {

    private var dialog: Dialog? = null
    private var dialogView: View? = null

    private var rvSortingPart: RecyclerView? = null

    private var tvDialogTitle: TextView? = null
    private var tvEmpty: TextView? = null
    private var btnApply: Button? = null

    private var isCancelable = false

    var positiveListener: ClickListenerPos? = null
    var negativeListener: ClickListener? = null
    var swipeListener: SwipeRefreshListener? = null

    var monthSelected = ""

    var sortingAdapter: SortingAdapter? = null

    lateinit var context: Context

    var sortings = ArrayList<TrackingSorting>()

    internal val isShowing: Boolean
        get() = dialog != null && dialog?.isShowing == true


    constructor(context: Context) {
        init(context, androidx.appcompat.app.AlertDialog.Builder(context))
    }

    constructor(context: Context, theme: Int) {
        init(context, androidx.appcompat.app.AlertDialog.Builder(context, theme))
    }


    private fun init(context: Context, dialogBuilder: androidx.appcompat.app.AlertDialog.Builder) {

        dialog = context.initDialogWithOutShow(R.layout.dialog_bottom_calendar_home_wheel,true,Gravity.BOTTOM)

        monthSelected = CalendarUtils.getCurrentDate("MMMM yyyy")

        val wheel = dialog!!.findViewById<WheelView>(R.id.wheel)
        val btnViewDashboard = dialog!!.findViewById<Button>(R.id.btnViewDashboard)!!
        val vTop = dialog!!.findViewById<View>(R.id.vTop)!!

        btnViewDashboard text "Pilih"

        vTop.onClick {
            dialog!!.dismiss()
        }

        val months = ArrayList<String>()
        months.add(context.getString(R.string.month_januari) + CalendarUtils.thisYear())
        months.add(context.getString(R.string.month_februari) + CalendarUtils.thisYear())
        months.add(context.getString(R.string.month_maret) + CalendarUtils.thisYear())
        months.add(context.getString(R.string.month_april) + CalendarUtils.thisYear())
        months.add(context.getString(R.string.month_mei) + CalendarUtils.thisYear())
        months.add(context.getString(R.string.month_juni) + CalendarUtils.thisYear())
        months.add(context.getString(R.string.month_juli) +CalendarUtils.thisYear())
        months.add(context.getString(R.string.month_agustus) + CalendarUtils.thisYear())
        months.add(context.getString(R.string.month_september) + CalendarUtils.thisYear())
        months.add(context.getString(R.string.month_oktober) + CalendarUtils.thisYear())
        months.add(context.getString(R.string.month_november) + CalendarUtils.thisYear())
        months.add(context.getString(R.string.month_desember) + CalendarUtils.thisYear())

        wheel.setItems(months)
        wheel.setSeletion((CalendarUtils.setFormatDate(monthSelected,"MMMM yyyy","M").toInt() - 1))
        wheel.onWheelViewListener = object : WheelView.OnWheelViewListener() {
            override fun onSelected(selectedIndex: Int, item: String) {
                monthSelected = item
                Log.d("Tag", "[Dialog]selectedIndex: $selectedIndex, item: $item")
            }
        }

        btnViewDashboard?.setOnClickListener {
            dialog!!.dismiss()
            positiveListener?.clickPos(this, monthSelected)
        }

    }

    fun setTitle(@StringRes message: Int): CalendarDialog {
        return setTitle(string(message))
    }

    fun setTitle(message: CharSequence): CalendarDialog {
        tvDialogTitle?.text = message
        return this
    }

//    fun setImage(@DrawableRes drawable: Int): DefaultDialog {
//        ivDialog?.setImageResource(drawable)
//        return this
//    }

    fun setButton(@StringRes pValue: Int, @StringRes nValue: Int): CalendarDialog {
        setButton(string(pValue), string(nValue))
        return this
    }

    fun setButton(@StringRes pValue: Int): CalendarDialog {
        btnApply?.text = string(pValue)
        return this
    }

    fun setButton(pValue: CharSequence, nValue: CharSequence): CalendarDialog {
        btnApply?.text = pValue
        return this
    }

    fun setCancelable(cancelable: Boolean): CalendarDialog {
        isCancelable = cancelable
        dialog?.setCancelable(cancelable)
        return this
    }

    protected fun string(@StringRes res: Int): String {
        return dialog?.context?.getString(res) ?: "-"
    }

    protected fun <ViewClass : View> findView(id: Int): ViewClass {
        return dialog?.findViewById<View>(id) as ViewClass
    }

    fun dismiss() {
        if (dialog?.isShowing == true) {
            dialog?.dismiss()
        }
    }

    fun show(): Dialog {
        dialog?.show()
        return dialog as Dialog
    }

    interface ClickListener {
        fun click(dialog: CalendarDialog)
    }

    interface ClickListenerPos {
        fun clickPos(dialog: CalendarDialog, monthSelect: String)
    }

    interface SwipeRefreshListener {
        fun swipe(dialog: CalendarDialog)
    }


    class SortingAdapter constructor(
        var items: ArrayList<TrackingSorting>,
        val activity: Context,
        private val listener: (Int) -> Unit
    ) :
        RecyclerView.Adapter<SortingAdapter.MyViewHolder>() {

        var selectedDistributorPos = -1
        var selectedDistributorItem: String? = null

        inner class MyViewHolder(vi: View) : RecyclerView.ViewHolder(vi) {

            val tvSorting = vi.findViewById<TextView>(R.id.tvSorting)
            val ivSorting = vi.findViewById<ImageView>(R.id.ivSorting)
            val rvSortChild = vi.findViewById<RecyclerView>(R.id.rvSortChild)

            fun bindItem(position: Int) {
                itemView.setOnClickListener {
                    listener(position)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val itemView: View? = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_sorting_dropdown, parent, false)

            var viewHolder: MyViewHolder? = null

            if (viewHolder == null)
                viewHolder = MyViewHolder(itemView!!)
            else
                viewHolder = parent.tag as MyViewHolder


            return viewHolder
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.bindItem(position)

            val posParent = position

            val item = items[position]

            holder.apply {
                tvSorting text item.name
            }

            var listSort = ArrayList<TrackingFilter>()

            when(position){
                0 -> {
                    /**
                     * Part Number
                     */
                    listSort.add(TrackingFilter("Urutkan A - Z","part_number|asc"))
                    listSort.add(TrackingFilter("Urutkan Z - A","part_number|desc"))
                }
                1 -> {
                    /**
                     * Deskripsi
                     */
                    listSort.add(TrackingFilter("Urutkan A - Z","description|asc"))
                    listSort.add(TrackingFilter("Urutkan Z - A","description|desc"))
                }
                2 -> {
                    /**
                     * Kelompok Barang
                     */
                    listSort.add(TrackingFilter("Urutkan A - Z","type_part|asc"))
                    listSort.add(TrackingFilter("Urutkan Z - A","type_part|desc"))
                }
                3 -> {
                    /**
                     * Month Year
                     */
                    listSort.add(TrackingFilter("Paling Baru - Lama","month|desc"))
                    listSort.add(TrackingFilter("Paling Lama - Baru","month|asc"))
                }
                4 -> {
                    /**
                     * Nomor Order
                     */
                    listSort.add(TrackingFilter("Urutkan A - Z","no_order|asc"))
                    listSort.add(TrackingFilter("Urutkan Z - A","no_order|desc"))
                }
                5 -> {
                    /**
                     * Tanggal Order
                     */
                    listSort.add(TrackingFilter("Urutkan A - Z","date_order|asc"))
                    listSort.add(TrackingFilter("Urutkan Z - A","date_order|desc"))
                }
                6 -> {
                    /**
                     * Quantity Order
                     */
                    listSort.add(TrackingFilter("Jumlah Besar - Kecil","qty|desc"))
                    listSort.add(TrackingFilter("Jumlah Kecil - Besar","qty|asc"))
                }
                7 -> {
                    /**
                     * Amount Order
                     */
                    listSort.add(TrackingFilter("Jumlah Besar - Kecil","amount|desc"))
                    listSort.add(TrackingFilter("Jumlah Kecil - Besar","amount|asc"))
                }
                8 -> {
                    /**
                     * Nomor Invoice
                     */
                    listSort.add(TrackingFilter("Urutkan A - Z","no_invoice|asc"))
                    listSort.add(TrackingFilter("Urutkan Z - A","no_invoice|desc"))
                }
                9 -> {
                    /**
                     * Tanggal Invoice
                     */
                    listSort.add(TrackingFilter("Paling Baru - Lama","date_invoice|desc"))
                    listSort.add(TrackingFilter("Paling Lama - Baru","date_invoice|asc"))
                }
                10 -> {
                    /**
                     * Quantity Invoice
                     */
                    listSort.add(TrackingFilter("Jumlah Besar - Kecil","qty_invoice|desc"))
                    listSort.add(TrackingFilter("Jumlah Kecil - Besar","qty_invoice|asc"))
                }
                11 -> {
                    /**
                     * Quantity BO
                     */
                    listSort.add(TrackingFilter("Jumlah Besar - Kecil","qty_bo|desc"))
                    listSort.add(TrackingFilter("Jumlah Kecil - Besar","qty_bo|asc"))
                }
                12 -> {
                    /**
                     * Quantity Shipping
                     */
                    listSort.add(TrackingFilter("Jumlah Besar - Kecil","qty_shipping|desc"))
                    listSort.add(TrackingFilter("Jumlah Kecil - Besar","qty_shipping|asc"))
                }
                13 -> {
                    /**
                     * Surat Jalan
                     */
                    listSort.add(TrackingFilter("Urutkan A - Z","delivery_order|asc"))
                    listSort.add(TrackingFilter("Urutkan Z - A","delivery_order|desc"))
                }
                14 -> {
                    /**
                     * Tanggal Surat Jalan
                     */
                    listSort.add(TrackingFilter("Paling Baru - Lama","date_delivery_order|desc"))
                    listSort.add(TrackingFilter("Paling Lama - Baru","date_delivery_order|asc"))
                }
                15 -> {
                    /**
                     * Nama Ekspedisi
                     */
                    listSort.add(TrackingFilter("Urutkan A - Z","no_expedition|asc"))
                    listSort.add(TrackingFilter("Urutkan Z - A","no_expedition|desc"))
                }
            }

            var selectedSorting = ""

            var sortingAdapter : RecyclerAdapter<TrackingFilter>? = null
            sortingAdapter = holder.rvSortChild.setAdapter(activity,listSort,R.layout.item_sorting_order,{
                val item = listSort[it]

                if(it == -1){
                    vDivider.hide()
                }

                tvNameFilter text item.name

                if (selectedSorting == item.nameParam) {
                    tvNameFilter.setTextColor(activity.resources.getColor(R.color.txt_black_category))
                    ivSorting.setImageResource(R.drawable.bg_fill_sorting_red)
                } else {
                    tvNameFilter.setTextColor(activity.resources.getColor(R.color.txt_gray))
                    ivSorting.setImageResource(R.drawable.bg_fill_sorting_grey)
                }


            },{
                if(selectedSorting == this.nameParam){
                    selectedSorting = ""
                    holder.tvSorting.setTextColor(activity.resources.getColor(R.color.txt_black_category))
                }else{
                    selectedSorting = this.nameParam
                    holder.tvSorting.setTextColor(activity.resources.getColor(R.color.red_amount_order))
                }

                items[posParent].selectedName = selectedSorting

                sortingAdapter!!.notifyDataSetChanged()
            })

            holder.itemView.onClick {
                if(item.hideView){
                    holder.ivSorting.setImageResource(R.drawable.ic_arrow_down_red)
                    holder.rvSortChild.show()
                    item.hideView = false
                }else{
                    holder.ivSorting.setImageResource(R.drawable.ic_arrow_chevron_right)
                    holder.rvSortChild.hide()
                    item.hideView = true
                }
            }

        }

        fun filterList(filteredList: ArrayList<TrackingSorting>) {
            items = filteredList
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int {
            return items.size
        }
    }

}
