package ahm.parts.ordering.ui.widget.activity.prospect.detail

import ahm.parts.ordering.R
import ahm.parts.ordering.data.model.home.FollowUpDetailProspect
import ahm.parts.ordering.data.model.home.StepDetailProspect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_follow_up.view.*
import kotlinx.android.synthetic.main.item_vertical_step.view.*


class StepAdapter(private val list:ArrayList<StepDetailProspect>) : RecyclerView.Adapter<StepAdapter.Holder>(){

    var stepActive = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_vertical_step,parent,false))
    }

    override fun getItemCount(): Int = list?.size

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.view.tvCurrentFunnelDetailProspect.text = list?.get(position)?.step

        if (position == stepActive){
            holder.view.step_circle_indicator_yellow.setImageResource(R.drawable.bg_fill_circle_indicator_yellow)
            holder.view.tvToFunnelDetailProspect.visibility = View.VISIBLE
        }else{
            holder.view.step_circle_indicator_yellow.setImageResource(R.drawable.bg_fill_circle_indicator_grey)
            holder.view.tvToFunnelDetailProspect.visibility = View.GONE
        }

        if (position == list.size-1){
            holder.view.lineStep.visibility = View.GONE
        }else{
            holder.view.lineStep.visibility = View.VISIBLE
        }

    }

    class Holder(val view: View) : RecyclerView.ViewHolder(view)

}


class FollowUpAdapter(private val list : ArrayList<FollowUpDetailProspect>) : RecyclerView.Adapter<FollowUpAdapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_follow_up,parent,false))
    }

    override fun getItemCount(): Int = list?.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.view.title_follow_up.text = list?.get(position)?.followUp

    }

    class Holder(val view: View) : RecyclerView.ViewHolder(view)

}