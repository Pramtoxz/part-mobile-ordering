package ahm.parts.ordering.ui.home.home.partnumber.cart.suggestorder

import ahm.parts.ordering.R
import ahm.parts.ordering.data.model.home.dashboard.partnumber.cart.suggestorder.SuggestOrder
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.adapter.MvpHolder
import android.Manifest
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SuggestOrderAdapter constructor(
    var items: ArrayList<SuggestOrder>,
    val activity: Activity,
    private val listener: (Int) -> Unit
) :
    RecyclerView.Adapter<SuggestOrderAdapter.MyViewHolder>() {

    inner class MyViewHolder(vi: View) : RecyclerView.ViewHolder(vi) {

        //TODO init View
        val tvNumber = vi.findViewById(R.id.tvNumber) as TextView
        val tvPartNumber = vi.findViewById(R.id.tvPartNumber) as TextView
        val tvPartDescription = vi.findViewById(R.id.tvPartDescription) as TextView
        val tvTotalAmount = vi.findViewById(R.id.tvTotalAmount) as TextView
        val tvTotalQty = vi.findViewById(R.id.tvTotalQty) as TextView
        val tvItemGroup = vi.findViewById(R.id.tvItemGroup) as TextView
        val tvQtyBackOrder = vi.findViewById(R.id.tvQtyBackOrder) as TextView
        val btnCheck = vi.findViewById(R.id.btnCheck) as ImageView


        fun bindItem(position: Int) {
            itemView.setOnClickListener {
                listener(position)
                items[position].isChecked = !items[position].isChecked
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View? = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order_sugestion, parent, false)

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

        holder.tvPartNumber text item.partNumber
        holder.tvPartDescription text item.partDescription
        holder.tvTotalAmount text StringMasker().addRp(item.amountTotal)

        holder.tvTotalQty text "${item.qtyTotal}" + " pcs"

        holder.tvItemGroup text item.itemGroup
        holder.tvQtyBackOrder text item.qtyBack+ " pcs"

        if(item.isChecked){
            holder.btnCheck.setImageResource(R.drawable.ic_checklist_enable)
        }else{
            holder.btnCheck.setImageResource(R.drawable.ic_checklist_disable)
        }
    }

    fun filterList(filteredList: ArrayList<SuggestOrder>) {
        items = filteredList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

}