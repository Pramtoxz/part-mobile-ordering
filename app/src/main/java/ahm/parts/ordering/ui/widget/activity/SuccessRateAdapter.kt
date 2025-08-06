package ahm.parts.ordering.ui.widget.activity

import ahm.parts.ordering.R
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SuccessRateAdapter constructor(
    val items: ArrayList<String>,
    val activity: Activity,
    private val listener: (Int) -> Unit
) :
    RecyclerView.Adapter<SuccessRateAdapter.MyViewHolder>() {

    inner class MyViewHolder(vi: View) : RecyclerView.ViewHolder(vi) {

        //TODO init View


        fun bindItem(position: Int) {
            itemView.setOnClickListener {
                listener(position)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var itemView: View?


        itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_success_rate, parent, false)

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

    }

    override fun getItemCount(): Int {
        return if (items.size > 5) {
            5
        } else
            items.size
    }

}