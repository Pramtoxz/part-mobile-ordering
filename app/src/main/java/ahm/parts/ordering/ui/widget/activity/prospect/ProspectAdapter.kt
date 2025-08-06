package ahm.parts.ordering.ui.widget.activity.prospect

import ahm.parts.ordering.R
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ProspectAdapter constructor(
    val items: ArrayList<String>,
    val activity: Activity,
    private val listener: (Int) -> Unit
) :
    RecyclerView.Adapter<ProspectAdapter.MyViewHolder>() {

    inner class MyViewHolder(vi: View) : RecyclerView.ViewHolder(vi) {

        //TODO init View
        val ivUnitThumbnail = vi.findViewById(R.id.ivUnitThumbnail) as ImageView

        fun bindItem(position: Int) {
            itemView.setOnClickListener {
                listener(position)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var itemView: View?


        itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_prospek, parent, false)

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

        Glide.with(activity)
            .load(R.drawable.es_no_data)
            .apply(RequestOptions.circleCropTransform())
            .into(holder.ivUnitThumbnail)

    }

    override fun getItemCount(): Int {
        return if (items.size > 5) {
            5
        } else
            items.size
    }

}