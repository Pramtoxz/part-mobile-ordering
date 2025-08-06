package ahm.parts.ordering.helper.dialog

import ahm.parts.ordering.R
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DialogSelectionAdapter constructor(
    val items: ArrayList<*>,
    val activity: Activity,
    private val listener: (Int) -> Unit
) :
    RecyclerView.Adapter<DialogSelectionAdapter.MyViewHolder>() {

    var selectedPosition = -1

    inner class MyViewHolder(vi: View) : RecyclerView.ViewHolder(vi) {

        //TODO init View
        val tvTitle = vi.findViewById(R.id.tvTitle) as TextView
        val ivCheck = vi.findViewById(R.id.ivCheck) as ImageView

        fun bindItem(position: Int) {
            itemView.setOnClickListener {
                listener(position)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var itemView: View?


        itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tipe_stock_filter, parent, false)

        var viewHolder: MyViewHolder? = null

        if (viewHolder == null)
            viewHolder = MyViewHolder(itemView)
        else
            viewHolder = parent.tag as MyViewHolder

        return viewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItem(position)

        val item = items[position]

        if (position == selectedPosition){
            holder.ivCheck.setImageResource(R.drawable.ic_checklist_btn_en)
        }else{
            holder.ivCheck.setImageResource(R.drawable.ic_checklist_btn_dis)
        }

        holder.tvTitle.text = item.toString()

    }

    override fun getItemCount(): Int {
        return items.size
    }

}