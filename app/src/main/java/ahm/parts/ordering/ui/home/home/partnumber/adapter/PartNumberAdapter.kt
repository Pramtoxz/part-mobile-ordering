package ahm.parts.ordering.ui.home.home.partnumber.adapter

import ahm.parts.ordering.R
import ahm.parts.ordering.data.constant.Constants
import ahm.parts.ordering.data.model.home.dashboard.partnumber.PartNumberFilter
import ahm.parts.ordering.helper.*
import ahm.parts.ordering.ui.home.home.partnumber.partnumbersearch.PartNumberFilterActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class PartNumberFilterAdapter constructor(
    var items: ArrayList<PartNumberFilter>,
    val activity: PartNumberFilterActivity,
    val role : String,
    private val listener: (Int) -> Unit
) :
    RecyclerView.Adapter<PartNumberFilterAdapter.MyViewHolder>() {

    inner class MyViewHolder(vi: View) : RecyclerView.ViewHolder(vi) {

        val tvPartNumber = vi.findViewById(R.id.tvPartNumber) as TextView
        val tvPartDescription = vi.findViewById(R.id.tvPartDescription) as TextView
        val tvItemGroup = vi.findViewById(R.id.tvItemGroup) as TextView
        val tvHet = vi.findViewById(R.id.tvHet) as TextView
        val tvAvailabePcs = vi.findViewById(R.id.tvAvailabePcs) as TextView

        val ivDiscount = vi.findViewById(R.id.ivDiscount) as ImageView
        val ivLike = vi.findViewById(R.id.ivLike) as ImageView
        val ivCheck = vi.findViewById(R.id.ivCheck) as ImageView

        val btnLike = vi.findViewById(R.id.btnLike) as CardView

        val lChart = vi.findViewById(R.id.lChart) as LinearLayout
        val lSelectedChart = vi.findViewById(R.id.lSelectedChart) as LinearLayout
        val vStockEmpty = vi.findViewById(R.id.vStockEmpty) as LinearLayout

        fun bindItem(position: Int) {
            itemView.setOnClickListener {
                listener(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View? = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_search_part_number, parent, false)

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

        holder.apply {
            tvPartNumber text item.partNumber
            tvPartDescription text item.partDescription
            tvHet text StringMasker().addRp(item.het.toDouble())
            tvAvailabePcs text item.availablePart

            tvItemGroup text item.itemGroup

            if (item.isLove == 1) {
                ivLike.setImageResource(R.drawable.ic_like)
            } else {
                ivLike.setImageResource(R.drawable.ic_like_area)
            }

            if (item.isPromo == 1) {
                ivDiscount.show()
            } else {
                ivDiscount.hide()
            }

            if (item.isChart) {
                lChart.hide()
                lSelectedChart.show()
            } else {
                lChart.show()
                lSelectedChart.hide()
            }

            lChart.onClick {
                item.isChart = true
                notifyDataSetChanged()

                activity.filteredData()

                activity.viewModel.hitAddCart("${item.id}",activity.kodeDealer.id)
            }

            lSelectedChart.onClick {
                item.isChart = false
                notifyDataSetChanged()

                activity.filteredData()

                activity.viewModel.hitDeleteCart(item.id.toString(),activity.kodeDealer.id)
            }

            btnLike.onClick {
                val isLove: Int

                if (item.isLove == 1) {
                    isLove = 0
                    item.isLove = 0
                    ivLike.setImageResource(R.drawable.ic_like)
                } else {
                    isLove = 1
                    item.isLove = 1
                    ivLike.setImageResource(R.drawable.ic_like)
                }
                notifyDataSetChanged()
                activity.viewModel.hitAddFavorite(item.id.toString(), isLove,activity.kodeDealer.id)
            }
        }

        when(role){
            Constants.ROLE.NONCHANNEL -> {
                holder.tvAvailabePcs.hide()
                holder.lChart.hide()
            }
            Constants.ROLE.DEALER -> {
                holder.tvAvailabePcs.hide()
            }
        }

    }

    fun filterList(filteredList: ArrayList<PartNumberFilter>) {
        items = filteredList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }
}