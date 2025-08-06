package ahm.parts.ordering.ui.home.order.tracking.adapter

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.order.Invoice
import ahm.parts.ordering.data.model.home.order.TrackingItem
import ahm.parts.ordering.data.model.home.order.TrackingOrder
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.home.order.tracking.detailpengiriman.TrackingOrderPengirimanDetailActivity
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TrackingOrderAdapter constructor(
    var items: ArrayList<TrackingOrder>,
    val activity: Activity,
    private val listener: (Int) -> Unit
) :
    RecyclerView.Adapter<TrackingOrderAdapter.MyViewHolder>() {

    inner class MyViewHolder(vi: View) : RecyclerView.ViewHolder(vi) {

        //TODO init View
        val tvPartNumber = vi.findViewById(R.id.tvPartNumber) as TextView
        val tvDate = vi.findViewById(R.id.tvDate) as TextView
        val tvOrderQty = vi.findViewById(R.id.tvOrderQty) as TextView
        val tvAmountOrder = vi.findViewById(R.id.tvAmountOrder) as TextView


        fun bindItem(position: Int) {
            itemView.setOnClickListener {
                listener(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View? = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ongoing_order, parent, false)

        var viewHolder: MyViewHolder? = null

        if (viewHolder == null)
            viewHolder = MyViewHolder(itemView!!)
        else
            viewHolder = parent.tag as MyViewHolder


        return viewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItem(position)

        val item = items[position]

        holder.tvPartNumber text item.code

        holder.tvDate text CalendarUtils.setFormatDate(item.dateOrder,SERVER_DATE_TIME_FORMAT,"d MMM yyyy")

        holder.tvOrderQty text item.totalItem + " item • "+ item.totalPcs + "pcs"
        holder.tvAmountOrder text StringMasker().addRp(item.totalPrice.toDouble())

    }

    fun filterList(filteredList: ArrayList<TrackingOrder>) {
        items = filteredList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

}

class TrackingOrderListItemAdapter constructor(
    var items: ArrayList<TrackingItem>,
    val activity: Activity,
    private val listener: (Int) -> Unit
) :
    RecyclerView.Adapter<TrackingOrderListItemAdapter.MyViewHolder>() {

    inner class MyViewHolder(vi: View) : RecyclerView.ViewHolder(vi) {

        //TODO init View
        val tvPartNumber = vi.findViewById(R.id.tvPartNumber) as TextView
        val tvPartDescription = vi.findViewById(R.id.tvPartDescription) as TextView
        val tvItemGroup = vi.findViewById(R.id.tvItemGroup) as TextView
        val tvTotalAmount = vi.findViewById(R.id.tvTotalAmount) as TextView
        val tvStatusPengiriman = vi.findViewById(R.id.tvStatusPengiriman) as TextView
        val tvTotalQty = vi.findViewById(R.id.tvTotalQty) as TextView


        fun bindItem(position: Int) {
            itemView.setOnClickListener {
                listener(position)
                activity.goTo<TrackingOrderPengirimanDetailActivity> {
                    putExtra(Constants.BUNDLE.JSON,items[position].getString())
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View? = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tracking_order_list_item, parent, false)

        var viewHolder: MyViewHolder? = null

        if (viewHolder == null)
            viewHolder = MyViewHolder(itemView!!)
        else
            viewHolder = parent.tag as MyViewHolder


        return viewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItem(position)

        val item = items[position]

        holder.tvPartNumber text item.partNumber
        holder.tvPartDescription text item.partDescription
        holder.tvItemGroup text item.itemGroup
        holder.tvTotalQty text item.totalPcs + " pcs"
        holder.tvTotalAmount text StringMasker().addRp(item.totalPrice.toDouble())
        holder.tvStatusPengiriman text item.delivered + " / " + item.totalPcs + " DELIVERED"

    }

    fun filterList(filteredList: ArrayList<TrackingItem>) {
        items = filteredList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

}


class TrackingOrderListPengirimanAdapter constructor(
    var items: ArrayList<Invoice>,
    val activity: Activity,
    private val listener: (Int) -> Unit
) :
    RecyclerView.Adapter<TrackingOrderListPengirimanAdapter.MyViewHolder>() {

    inner class MyViewHolder(vi: View) : RecyclerView.ViewHolder(vi) {

        //TODO init View
        val tvNumber = vi.findViewById(R.id.tvNumber) as TextView
        val tvInvoiceNo = vi.findViewById(R.id.tvInvoiceNo) as TextView
        val tvDate = vi.findViewById(R.id.tvDate) as TextView
        val tvTotalPrice = vi.findViewById(R.id.tvTotalPrice) as TextView
        val tvShippingQty = vi.findViewById(R.id.tvShippingQty) as TextView
        val tvNoSurat = vi.findViewById(R.id.tvNoSurat) as TextView
        val tvDateSurat = vi.findViewById(R.id.tvDateSurat) as TextView
        val tvEkspedisi = vi.findViewById(R.id.tvEkspedisi) as TextView


        fun bindItem(position: Int) {
            itemView.setOnClickListener {
                listener(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View? = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tracking_order_invoice_pengiriman, parent, false)

        var viewHolder: MyViewHolder? = null

        if (viewHolder == null)
            viewHolder = MyViewHolder(itemView!!)
        else
            viewHolder = parent.tag as MyViewHolder


        return viewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItem(position)

        val item = items[position]

        holder.tvNumber text "${(position + 1)}"

        holder.tvInvoiceNo text item.invoiceNo
        holder.tvEkspedisi text item.expedition
        holder.tvDate text CalendarUtils.setFormatDate(item.date,SERVER_DATE_TIME_FORMAT,VIEW_DATE_FORMAT)
        holder.tvDateSurat text CalendarUtils.setFormatDate(item.date,SERVER_DATE_TIME_FORMAT,VIEW_DATE_SHORT_FORMAT)
        holder.tvShippingQty text item.totalPcs + " pcs"
        holder.tvTotalPrice text StringMasker().addRp(item.totalPrice)
        holder.tvNoSurat text item.deliveryNumber

    }

    fun filterList(filteredList: ArrayList<Invoice>) {
        items = filteredList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

}