package ahm.parts.ordering.ui.home.dealer.salesmanvisit.visit.rencanavisit

import ahm.parts.ordering.R
import ahm.parts.ordering.data.model.home.dealer.salemanvisit.RencanaVisit
import ahm.parts.ordering.helper.*
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RencanaVisitAdapter constructor(
    var items: ArrayList<RencanaVisit>,
    val activity: Activity,
    private val listener: (Int) -> Unit
) :
    RecyclerView.Adapter<RencanaVisitAdapter.MyViewHolder>() {

    inner class MyViewHolder(vi: View) : RecyclerView.ViewHolder(vi) {

        val tvDateVisit = vi.findViewById(R.id.tvDateVisit) as TextView

        fun bindItem(position: Int) {
            itemView.setOnClickListener {
                listener(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View? = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rencana_visit_date, parent, false)

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
        holder.tvDateVisit text CalendarUtils.setFormatDate(item.date,"yyyy-MM-dd","d MMMM yyyy")

    }

    override fun getItemCount(): Int {
        return items.size
    }

}