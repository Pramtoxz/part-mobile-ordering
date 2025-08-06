package ahm.parts.ordering.ui.home.home.partnumber.cart.skemapembelian

import ahm.parts.ordering.R
import ahm.parts.ordering.data.model.home.dashboard.partnumber.cart.skemapembelian.ListSkemaPembelian
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.base.adapter.MvpHolder
import android.Manifest
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SkemaPembelianAdapter constructor(
    val items: ArrayList<ListSkemaPembelian>,
    val activity: Activity,
    private val listener: (Int) -> Unit
) :
    RecyclerView.Adapter<SkemaPembelianAdapter.MyViewHolder>() {

    inner class MyViewHolder(vi: View) : RecyclerView.ViewHolder(vi) {

        //TODO init View
        val tvLabelQty = vi.findViewById(R.id.tvLabelQty) as TextView
        val tvQty = vi.findViewById(R.id.tvQty) as TextView
        val tvLabelAmount = vi.findViewById(R.id.tvLabelAmount) as TextView
        val tvAmount = vi.findViewById(R.id.tvAmount) as TextView
        val viewRootItem = vi.findViewById(R.id.viewRootItem) as View


        fun bindItem(position: Int) {
            itemView.setOnClickListener {
                listener(position)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View? = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_skema_pembelian, parent, false)
        //setHasStableIds(false)

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

        holder.tvLabelQty text  item.namePembelian
        holder.tvQty text item.qty + "pcs"
        holder.tvLabelAmount text item.namePrice
        holder.tvAmount text StringMasker().addRp(item.price)

//        if(item.qty.equals("0",true)){
//            activity.skemaPembelians.remove(item)
//            activity.skemaPembelianAdapter.notifyDataSetChanged()
////            holder.viewRootItem.visibility = View.GONE
//        }else{
//            holder.viewRootItem.visibility = View.VISIBLE
//        }


    }


    override fun getItemCount(): Int {
        return items.size
    }

}