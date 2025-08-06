package ahm.parts.ordering.ui.widget.activity

import ahm.parts.ordering.R
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NewActivityAdapter constructor(
    val items: ArrayList<String>,
    val activity: Activity,
    private val listener: (Int) -> Unit
) :
    RecyclerView.Adapter<NewActivityAdapter.MyViewHolder>() {

    inner class MyViewHolder(vi: View) : RecyclerView.ViewHolder(vi) {

        //TODO init View
        val tvName = vi.findViewById(R.id.tvName) as TextView
        val tvStatus = vi.findViewById(R.id.tvStatus) as TextView
        val tvInfo = vi.findViewById(R.id.tvInfo) as TextView


        fun bindItem(position: Int) {
            itemView.setOnClickListener {
                listener(position)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var itemView: View?


        itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_wallboard_activity, parent, false)

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

        if (position % 2 == 0){
            holder.tvStatus.setBackgroundResource(R.drawable.bg_gradient_capsule_status_green)
        }else{
            holder.tvStatus.setBackgroundResource(R.drawable.bg_gradient_capsule_red_status)
        }

    }

    override fun getItemCount(): Int {
        return if (items.size > 5) {
            5
        } else
            items.size
    }

}