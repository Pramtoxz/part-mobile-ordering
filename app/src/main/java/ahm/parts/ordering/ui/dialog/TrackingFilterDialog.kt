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
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.item_sorting_order.view.*

/**
 * Created by nuryazid on 4/20/18.
 */

class TrackingFilterDialog {

    private var dialog: BottomSheetDialog? = null
    private var dialogView: View? = null

    private var rvSortingPart: RecyclerView? = null

    private var tvDialogTitle: TextView? = null
    private var tvEmpty: TextView? = null
    private var tvReset: TextView? = null
    private var btnApply: Button? = null

    private var isCancelable = false

    var positiveListener: ClickListenerPos? = null
    var negativeListener: ClickListener? = null
    var nestedScrollView: NestedScrollView? = null

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

        dialog = context.bottomSheetWithOutShow(R.layout.dialog_bottom_filter_order){}

        rvSortingPart = dialog!!.findViewById(R.id.rvSortingPart)
        nestedScrollView = dialog!!.findViewById(R.id.nestedScrollView)
        tvReset = dialog!!.findViewById(R.id.tvReset)

        val rootView = dialog!!.findViewById<ViewGroup>(R.id.rootView)!!
        rootView.lyTrans()

        btnApply = dialog!!.findViewById(R.id.btnApply)

        sortings.add(TrackingSorting("Part Number", "", ""))
        sortings.add(TrackingSorting("Deskripsi", "", ""))
        sortings.add(TrackingSorting("Kelompok Barang", "", ""))
        sortings.add(TrackingSorting("Month Year", "", ""))
        sortings.add(TrackingSorting("Nomor Order", "", ""))
        sortings.add(TrackingSorting("Tanggal Order", "", ""))
        sortings.add(TrackingSorting("Quantity Order", "", ""))
        sortings.add(TrackingSorting("Amount Order", "", ""))
        sortings.add(TrackingSorting("Nomor Invoice", "", ""))
        sortings.add(TrackingSorting("Tanggal Invoice", "", ""))
        sortings.add(TrackingSorting("Quantity Invoice", "", ""))
        sortings.add(TrackingSorting("Quantity BO", "", ""))
        sortings.add(TrackingSorting("Quantity Shipping", "", ""))
        sortings.add(TrackingSorting("Surat Jalan", "", ""))
        sortings.add(TrackingSorting("Tanggal Surat Jalan", "", ""))
        sortings.add(TrackingSorting("Nama Ekspedisi", "", ""))


        sortingAdapter = SortingAdapter(sortings, context) {}

        rvSortingPart!!.initItem(context, sortingAdapter!!)

        dialog!!.setCancelable(true)

        btnApply?.setOnClickListener {
           Log.e("sortingsSelect",sortings.getString())
            positiveListener?.clickPos(this, sortings)
            dialog!!.dismiss()
        }

        tvReset?.onClick {
            sortings.forEach {
                it.selectedName = ""
                it.hideView = false
            }
            sortingAdapter!!.notifyDataSetChanged()
            positiveListener?.clickPos(this, sortings)
            dialog!!.dismiss()
        }

    }

    fun setTitle(@StringRes message: Int): TrackingFilterDialog {
        return setTitle(string(message))
    }

    fun setTitle(message: CharSequence): TrackingFilterDialog {
        tvDialogTitle?.text = message
        return this
    }

//    fun setImage(@DrawableRes drawable: Int): DefaultDialog {
//        ivDialog?.setImageResource(drawable)
//        return this
//    }

    fun setButton(@StringRes pValue: Int, @StringRes nValue: Int): TrackingFilterDialog {
        setButton(string(pValue), string(nValue))
        return this
    }

    fun setButton(@StringRes pValue: Int): TrackingFilterDialog {
        btnApply?.text = string(pValue)
        return this
    }

    fun setButton(pValue: CharSequence, nValue: CharSequence): TrackingFilterDialog {
        btnApply?.text = pValue
        return this
    }

    fun setCancelable(cancelable: Boolean): TrackingFilterDialog {
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
        nestedScrollView?.scrollTo(0,0)
        dialog?.show()
        return dialog as Dialog
    }

    interface ClickListener {
        fun click(dialog: TrackingFilterDialog)
    }

    interface ClickListenerPos {
        fun clickPos(dialog: TrackingFilterDialog, trackingSorts: ArrayList<TrackingSorting>)
    }

    interface SwipeRefreshListener {
        fun swipe(dialog: TrackingFilterDialog)
    }


    class SortingAdapter constructor(
        var items: ArrayList<TrackingSorting>,
        val activity: Context,
        private val listener: (Int) -> Unit
    ) :
        RecyclerView.Adapter<SortingAdapter.MyViewHolder>() {

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

            val listSort = ArrayList<TrackingFilter>()

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
                    /*listSort.add(TrackingFilter("Urutkan A - Z","date_order|asc"))
                    listSort.add(TrackingFilter("Urutkan Z - A","date_order|desc"))*/

                    listSort.add(TrackingFilter("Paling Baru - Lama","date_order|desc"))
                    listSort.add(TrackingFilter("Paling Lama - Baru","date_order|asc"))
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
                    holder.ivSorting.setImageResource(R.drawable.ic_arrow_chevron_right)
                    holder.rvSortChild.hide()
                    item.hideView = false
                }else{
                    holder.ivSorting.setImageResource(R.drawable.ic_arrow_down_red)
                    holder.rvSortChild.show()
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
